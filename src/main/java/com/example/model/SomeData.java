package com.example.model;

/**
 * Created by Nikolay V. Petrov on 24.08.2017.
 */
public class SomeData {

    public static final String TABLE_NAME = "some_data";
    public static final String ID_COLUMN = "id";
    public static final String ID_SEQ = "data_id_seq";
    public static final String DATA_COLUMN = "data";

    private Long id;
    private String data;

    public SomeData() {
    }

    public SomeData(String data) {
        this.data = data;
    }

    public SomeData(Long id, String data) {
        this.id = id;
        this.data = data;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SomeData that = (SomeData) obj;
        if (!data.equals(that.data)) return false;

        return true;
    }

    @Override
    public String toString() {
        return String.format(
                "Data[id=%d, data='%s']",
                id, data);
    }
}