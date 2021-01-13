package com.damein.decorator;

public class Decorator {

    private Sourceable source;

    public Decorator(Sourceable source) {
        super();
        this.source = source;
    }

    public void method() {
        System.out.println("before decorator!");
        source.method();
        System.out.println("after decorator!");
    }
}
