package tokenization_example;

public interface TokenVisitor<A> {
    public A visitPrintToken();
    public A visitIdentifierToken(String name);
    public A visitSemicolonToken();
}
