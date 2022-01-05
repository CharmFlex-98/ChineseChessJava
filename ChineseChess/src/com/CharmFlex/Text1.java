package com.CharmFlex;

public class Text1 extends Text{

    public Text1(int x) {
        super(x);
    }

    @Override
    Server go() {
        return new Server();
    }
}
