package com.sg.relief.domain.service;

public enum NotificationParameter {
    SOUND("default"),
    COLOR("#ffbeed");
    private String value;

    NotificationParameter (String value){
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
