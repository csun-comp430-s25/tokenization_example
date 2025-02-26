package tokenization_example;

import java.util.Optional;
import java.util.ArrayList;

public class Tokenizer {
    public static final Symbol[] SYMBOLS = {
        new Symbol("(", new LParenToken()),
        new Symbol(")", new RParenToken()),
        new Symbol("+", new PlusToken()),
        new Symbol("-", new MinusToken()),
        new Symbol("*", new StarToken()),
        new Symbol("/", new DivToken()),
        new Symbol("=", new EqualsToken()),
        new Symbol(";", new SemicolonToken())
    };

    public final String input;
    private int position;

    public Tokenizer(final String input) {
        this.input = input;
        position = 0;
    } // Tokenizer

    private void skipWhitespace() {
        while (position < input.length() &&
               Character.isWhitespace(input.charAt(position))) {
            position++;
        }
    } // skipWhitespace

    private Optional<Token> tryReadIntegerToken() {
        String digits = "";
        while (position < input.length() &&
               Character.isDigit(input.charAt(position))) {
            digits += input.charAt(position);
            position++;
        }
        if (digits.length() > 0) {
            return Optional.of(new IntegerToken(Integer.parseInt(digits)));
        } else {
            return Optional.empty();
        }
    } // tryReadIntegerToken

    private Optional<Token> tryReadIdentifierOrReservedWord() {
        if (position < input.length() &&
            Character.isLetter(input.charAt(position))) {
            String chars = "" + input.charAt(position);
            position++;
            while (position < input.length() &&
                   Character.isLetterOrDigit(input.charAt(position))) {
                chars += input.charAt(position);
                position++;
            }

            if (chars.equals("print")) {
                return Optional.of(new PrintToken());
            } else {
                return Optional.of(new IdentifierToken(chars));
            }
        } else {
            return Optional.empty();
        }
    } // tryReadIdentifierOrReservedWord

    private Optional<Token> tryReadSymbol() {
        for (final Symbol symbol : SYMBOLS) {
            if (input.startsWith(symbol.asString(), position)) {
                position += symbol.asString().length();
                return Optional.of(symbol.asToken());
            }
        }
        return Optional.empty();
    } // tryReadSymbol

    // assumes it's starting on a non-whitespace character in range
    private Token tryReadToken() throws TokenizerException {
        Optional<Token> retval = null;
        if ((retval = tryReadIntegerToken()).isPresent() ||
            (retval = tryReadIdentifierOrReservedWord()).isPresent() ||
            (retval = tryReadSymbol()).isPresent()) {
            return retval.get();
        } else {
            throw new TokenizerException("Cannot tokenize character: " + input.charAt(position));
        }
    } // tryReadToken

    private ArrayList<Token> readTokens() throws TokenizerException {
        final ArrayList<Token> tokens = new ArrayList<Token>();
        skipWhitespace();
        while (position < input.length()) {
            tokens.add(tryReadToken());
            skipWhitespace();
        }
        return tokens;
    } // readTokens

    public static ArrayList<Token> tokenize(final String input) throws TokenizerException {
        return new Tokenizer(input).readTokens();
    } // tokenize
}
