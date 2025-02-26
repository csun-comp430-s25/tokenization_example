public class RParen implements Token {
    public RParen() {}
    public String toString() {
        return "(";
    }
    public boolean equals(final Object obj) {
        return obj instanceof RParen;
    }
    public int hashCode() {
        return 1;
    }
}

