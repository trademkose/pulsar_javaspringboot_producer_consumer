package io.github.trademkose.pulsar.bus.msg;

public class MyMsg {
    private String data;
    public MyMsg(String data) {
        this.data = data;
    }

    public MyMsg() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
