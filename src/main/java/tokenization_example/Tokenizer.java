package tokenization_example;

import java.util.Optional;

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
                                        
    public Optional<Token> tryReadIdentifierToken() {
        if (position < input.length() &&
            Character.isLetter(input.charAt(position))) {
            String chars = "" + input.charAt(position);
            position++;
            while (position < input.length() &&
                   Character.isLetterOrDigit(input.charAt(position))) {
                chars += input.charAt(position);
                position++;
            }
            return Optional.of(new IdentifierToken(chars));
        } else {
            return Optional.empty();
        }
    }
}
