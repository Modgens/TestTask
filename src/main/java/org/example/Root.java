package org.example;

public class Root {
    private long id;
    private double root;

    public Root(long id, double root) {
        this.id = id;
        this.root = root;
    }

    public Root() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getRoot() {
        return root;
    }

    public void setRoot(double root) {
        this.root = root;
    }
}
