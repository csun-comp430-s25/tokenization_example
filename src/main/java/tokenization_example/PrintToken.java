package tokenization_example;

public record PrintToken() implements Token {
    public <A> A visit(TokenVisitor<A> v) {
        return v.visitPrintToken();
    }        
}
