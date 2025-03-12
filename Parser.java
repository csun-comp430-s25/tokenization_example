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

    public ParseResult<Exp> exp(final int startPos) throws ParseException { ... }

    public void assertTokenIs(final int pos, final Token expected) throws ParseException {
        final Token received = getToken(pos);
        if (!expected.equals(received)) {
            throw new ParseException("Expected: " + expected.toString() +
                                     "; received: " + received.toString());
        }
    } // assertTokenIs
        
    // stmt ::= IDENTIFIER `=` exp `;` |
    //          `print` exp `;`
    //
    // id ~ EqualsToken ~ exp ~ SemicolonToken ^^ {
    //   case name ~ _ ~ e ~ _ => AssignStmt(name, e)
    // } |
    // PrintToken ~> exp <~ SemicolonToken ^^ PrintStmt.apply
    
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
        } else {
            throw new ParseException("Expected statement; got: " + token);
        }
    } // stmt
        
