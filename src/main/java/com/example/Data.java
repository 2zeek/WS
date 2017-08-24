package com.example;

/**
 * Created by Nikolay V. Petrov on 24.08.2017.
 */
public class Data {
    private Integer id;
    private String data;

    public Data(Integer id, String data) {
        this.id = id;
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format(
                "Data[id=%d, data='%s']",
                id, data);
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return this.data;
    }
}