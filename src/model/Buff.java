package model;

public class Buff {
    private String name;
    private Kind kind;

    public String getName() {
        return name;
    }

    public Kind getKind() {
        return kind;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }
}

enum Kind {
    POSITIVE, NEGATIVE
}
