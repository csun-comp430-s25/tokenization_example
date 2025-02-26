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
    public void testInvalidChar()  {
        assertDoesNotTokenize("$");
    }
} // TokenizerTest
