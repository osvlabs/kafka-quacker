package org.zed.kafkaQuacker.Template;

import java.util.function.Function;

public class ValueTemplateSlot implements Function<Object, String> {

    protected String rawTemplateSegment;

    public ValueTemplateSlot(String templateSegment) {
        this.rawTemplateSegment = templateSegment;
    }

    @Override
    public String apply(Object o) {
        return rawTemplateSegment;
    }
}
