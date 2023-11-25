package ru.karpov.HeadHunter.model;

public class KeyValue {
    private String key;
    private Integer value;

    public KeyValue(final String key, final Integer value) {
        this.key = key;
        this.value = value;
    }

    public KeyValue() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(final Integer value) {
        this.value = value;
    }
}
