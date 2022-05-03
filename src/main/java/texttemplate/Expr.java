package texttemplate;

class Expr {
    public enum Kind {
        Text, Var, Comment,
    }

    public Kind kind;
    public String value;

    public Expr(Kind kind, String value) {
        this.kind = kind;
        this.value = value;
    }

    public String toString() {
        return String.format("%s(\"%s\")", this.kind, this.value);
    }
}
