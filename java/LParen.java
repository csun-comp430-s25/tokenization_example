public class LParen implements Token {
    public LParen() {}
    public String toString() {
        return "(";
    }
    public boolean equals(final Object obj) {
        return obj instanceof LParen;
    }
    public int hashCode() {
        return 0;
    }
}
