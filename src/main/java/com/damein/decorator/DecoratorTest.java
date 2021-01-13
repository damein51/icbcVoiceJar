package com.damein.decorator;

public class DecoratorTest {

    public static void main(String[] args) {
        Sourceable source = new Source();
        Decorator obj = new Decorator(source);
        obj.method();

    }
}
