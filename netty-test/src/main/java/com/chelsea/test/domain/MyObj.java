package com.chelsea.test.domain;

import java.io.Serializable;

public class MyObj implements Serializable {

    private static final long serialVersionUID = 1L;

    private String value;

    public MyObj() {}

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
