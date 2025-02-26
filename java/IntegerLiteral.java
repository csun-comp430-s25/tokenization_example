// public record IntegerLiteral(int value)
//  implements Token

public class IntegerLiteral implements Token {
    public final int value;

    public IntegerLiteral(final int value) {
        this.value = value;
    }

    public String toString() {
        return "IntegerLiteral(" + value + ")";
    }

    public boolean equals(final Object obj) {
        if (obj instanceof IntegerLiteral) {
            return value == ((IntegerLiteral)obj).value;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return value;
    }
}

