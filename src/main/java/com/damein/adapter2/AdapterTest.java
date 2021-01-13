package com.damein.adapter2;

public class AdapterTest {

    public static void main(String[] args) {
        Source source = new Source();
        Targetable target = new Adapter(source);
        target.method1();
        target.method2();
    }
}
