package com.damein.adapter2;

public class Adapter implements Targetable {

    private Source source;

    public Adapter(Source source) {
        super();
        this.source = source;
    }

    public void method1() {
        source.method1();
    }

    public void method2() {
        System.out.println("this is the targetable method!");
    }
}
