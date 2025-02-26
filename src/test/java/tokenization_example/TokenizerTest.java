package tokenization_example;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

public class TokenizerTest {
    public void assertDoesNotTokenize(final String input) {
        assertThrows(TokenizerException.class,
                     () -> Tokenizer.tokenize(input));
    }
    
    public void assertTokenizes(final String input,
                                final Token... tokens) throws TokenizerException {
        assertArrayEquals(tokens,
                          Tokenizer.tokenize(input).toArray());
    }

    @Test
    public void testEmptyTokenizes() throws TokenizerException {
        assertTokenizes("");
    }

    @Test
    public void testWhitespaceTokenizes() throws TokenizerException {
        assertTokenizes("   ");
    }

    @Test
    public void testInvalidChar() {
        assertDoesNotTokenize("$");
    }

    @Test
    public void testSingleCharIdentifierTokenizes() throws TokenizerException {
        assertTokenizes("a", new IdentifierToken("a"));
    }

    @Test
    public void testMultiCharIdentifierTokenizes() throws TokenizerException {
        assertTokenizes("foo", new IdentifierToken("foo"));
    }

    @Test
    public void testMultiCharIdentifierWithDigitsTokenizes() throws TokenizerException {
        assertTokenizes("foo123", new IdentifierToken("foo123"));
    }

    @Test
    public void testIntegerTokenizes() throws TokenizerException {
        assertTokenizes("456", new IntegerToken(456));
    }

    @Test
    public void testLParenTokenizes() throws TokenizerException {
        assertTokenizes("(", new LParenToken());
    }

    @Test
    public void testRParenTokenizes() throws TokenizerException {
        assertTokenizes(")", new RParenToken());
    }

    @Test
    public void testPlusTokenizes() throws TokenizerException {
        assertTokenizes("+", new PlusToken());
    }

    @Test
    public void testMinusTokenizes() throws TokenizerException {
        assertTokenizes("-", new MinusToken());
    }

    @Test
    public void testStarTokenizes() throws TokenizerException {
        assertTokenizes("*", new StarToken());
    }

    @Test
    public void testDivTokenizes() throws TokenizerException {
        assertTokenizes("/", new DivToken());
    }

    @Test
    public void testEqualsTokenizes() throws TokenizerException {
        assertTokenizes("=", new EqualsToken());
    }

    @Test
    public void testSemicolonTokenizes() throws TokenizerException {
        assertTokenizes(";", new SemicolonToken());
    }

    @Test
    public void testPrintTokenizes() throws TokenizerException {
        assertTokenizes("print", new PrintToken());
    }
    
    @Test
    public void testMultipleTokensTokenize() throws TokenizerException {
        assertTokenizes("()+-*/=;print foo 123",
                        new LParenToken(),
                        new RParenToken(),
                        new PlusToken(),
                        new MinusToken(),
                        new StarToken(),
                        new DivToken(),
                        new EqualsToken(),
                        new SemicolonToken(),
                        new PrintToken(),
                        new IdentifierToken("foo"),
                        new IntegerToken(123));
    }
} // TokenizerTest
