package io.github.trademkose.pulsar.bus.msg;

public class MyMsg2 {
    private String data;
    public MyMsg2(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
