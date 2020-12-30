package org.zed.kafkaQuacker.Template;

public abstract class ValueMaker {
    protected String rawParameter;

    public ValueMaker(String rawParameter) {
        this.rawParameter = rawParameter;
    }

    abstract public String make();
}
