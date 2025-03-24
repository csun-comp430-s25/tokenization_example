public class Parser {
    public final Token[] tokens;
    public Parser(final Token[] tokens) {
        this.tokens = tokens;
    }

    public Token readToken(final int pos) throws ParseException {
        if (pos < 0 || pos >= tokens.length) {
            throw new ParseException("Ran out of tokens");
        } else {
            return tokens[pos];
        }
    } // readToken

    // multExp ::= primaryExp ((`*` | `/`) primaryExp)*
    public ParseResult<Exp> multExp(final int startPos) throws ParseException { ... }

    // addExp ::= multExp ((`+` | `-`) multExp)*
    public ParseResult<Exp> addExp(final int startPos) throws ParseException {
        final ParseResult<Exp> m = multExp(startPos);
        Exp result = m.result;
        boolean shouldRun = true;
        int pos = m.nextPos;
        while (shouldRun) {
            try {
                final Token t = getToken(pos);
                final Op op;
                if (t instanceof PlusToken) {
                    op = new PlusOp();
                } else if (t instanceof MinusToken) {
                    op = new MinusOp();
                } else {
                    throw new ParseException("Expected + or -");
                }
                final ParseResult<Exp> m2 = multExp(pos + 1);
                result = new BinOpExp(result, op, m2.result);
                pos = m2.nextPos;
            } catch (ParseException e) {
                shouldRun = false;
            }
        }
        return new ParseResult<Exp>(result, pos);
    }
    
    public ParseResult<Exp> exp(final int startPos) throws ParseException {
        return addExp(startPos);
    }

    public void assertTokenIs(final int pos, final Token expected) throws ParseException {
        final Token received = getToken(pos);
        if (!expected.equals(received)) {
            throw new ParseException("Expected: " + expected.toString() +
                                     "; received: " + received.toString());
        }
    } // assertTokenIs
        
    // id ~ EqualsToken ~ exp ~ SemicolonToken ^^ {
    //   case name ~ _ ~ e ~ _ => AssignStmt(name, e)
    // } |
    // PrintToken ~> exp <~ SemicolonToken ^^ PrintStmt.apply

    //
    // stmt ::= IDENTIFIER `=` exp `;` |
    //          `print` exp `;` |
    //          `return` [exp] `;`
    //    
    public ParseResult<Stmt> stmt(final int startPos) throws ParseException {
        final Token token = readToken(startPos);
        if (token instanceof IdentifierToken id) {
            String name = id.name;
            assertTokenIs(startPos + 1, new EqualsToken());
            ParseResult<Exp> expression = exp(startPos + 2);
            assertTokenIs(expression.nextPos, new SemicolonToken());
            AssignStmt assign = new AssignStmt(name, expression.result);
            return new ParseResult<Stmt>(assign, expression.nextPos + 1);
        } else if (token instanceof PrintToken) {
            ParseResult<Exp> expression = exp(startPos + 1);
            assertTokenIs(expression.nextPos, new SemicolonToken());
            PrintStmt print = new PrintStmt(expression.result);
            return new ParseResult<Stmt>(print, expression.nextPos + 1);
        } else if (token instanceof ReturnToken) {
            ParseResult<Optional<Exp>> opExpression;
            try {
                ParseResult<Exp> expression = exp(startPos + 1);
                opExpression = new ParseResult<Optional<Exp>>(Optional.of(expression.result),
                                                              expression.nextPos);
            } catch (ParseException e) {
                opExpression = new ParseResult<Optional<Exp>>(Optional.empty(),
                                                              startPos + 1);
            }
            assertTokenIs(opExpression.nextPos, new SemicolonToken());
            return new ParseResult<Stmt>(new ReturnStmt(opExpression.result),
                                         opExpression.nextPos + 1);
        } else {
            throw new ParseException("Expected statement; got: " + token);
        }
    } // stmt

    // program ::= stmt*
    public ParseResult<Program> program(final int startPos) {
        final List<Stmt> stmts = new ArrayList<Stmt>();
        int pos = startPos;
        boolean shouldRun = true;
        while (shouldRun) {
            try {
                final ParseResult<Stmt> stmtRes = stmt(pos);
                stmts.add(stmtRes.result);
                pos = stmtRes.nextPos;
            } catch (ParseException e) {
                shouldRun = false;
            }
        }
        return new ParseResult<Program>(new Program(stmts), pos);
    } // program

    public Program parseWholeProgram() throws ParseException {
        final ParseResult<Program> p = program(0);
        if (p.nextPos == tokens.length) {
            return p.result;
        } else {
            throw new ParseException("Invalid token at position: " + p.nextPos);
        }
    } // parseWholeProgram
}
