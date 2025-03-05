package tokenization_example;

import java.util.Optional;
import java.util.ArrayList;

public class Tokenizer {
    public final String input;
    private int position;

    public Tokenizer(final String input) {
        this.input = input;
        position = 0;
    }

    public int getPosition() {
        return position;
    }
    
    public void skipWhitespace() {
        while (position < input.length() &&
               Character.isWhitespace(input.charAt(position))) {
            position++;
        }
    }

    // private void readAnyToken() {
    //     try {
    //         return tryReadIntegerToken();
    //     } catch (TokenizerException e) {
    //         try {
    //             return tryReadIdentifierToken();
    //         } catch (TokenizerException e) {
    //             try {
    //                 return tryReadEqualsToken();
    //             } catch (TokenizerException e) {
    //                 ...

    // returns null if it couldn't read a token
    // private Token tryReadIntegerToken() {

    public Optional<Token> tryReadIntegerToken() {
        String digits = "";
        while (position < input.length() &&
               Character.isDigit(input.charAt(position))) {
            digits += input.charAt(position);
            position++;
        }

        if (digits.length() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(new IntegerToken(Integer.parseInt(digits)));
        }
    } // tryReadIntegerToken
                                        
    public Optional<Token> tryReadIdentifierOrReservedWordToken() {
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
    } // tryReadIdentifierOrReservedWordToken

    public Optional<Token> tryReadSymbol() {
        if (input.startsWith("(", position)) {
            position++;
            return Optional.of(new LParenToken());
        } else if (input.startsWith(")", position)) {
            position++;
            return Optional.of(new RParenToken());
        } else {
            return Optional.empty();
        }
    } // tryReadSymbol

    // assumes we aren't on whitespace and we are in range
    public Token readToken() throws TokenizerException {
        Optional<Token> token;
        if ((token = tryReadIntegerToken()).isPresent() ||
            (token = tryReadSymbol()).isPresent() ||
            (token = tryReadIdentifierOrReservedWordToken()).isPresent()) {
            return token.get();
        } else {
            throw new TokenizerException("Invalid char: " + input.charAt(position));
        }
    } // readToken
        
    public ArrayList<Token> tokenize() throws TokenizerException {
        final ArrayList<Token> tokens = new ArrayList<Token>();
        skipWhitespace();
        while (position < input.length()) {
            tokens.add(readToken());
            skipWhitespace();
        }
        return tokens;
    } // tokenize
}
