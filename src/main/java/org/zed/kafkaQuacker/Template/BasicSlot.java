package org.zed.kafkaQuacker.Template;

import java.util.function.Function;

public class BasicSlot implements Function<Object, String> {

    protected String rawTemplateSegment;

    public BasicSlot(String templateSegment) {
        this.rawTemplateSegment = templateSegment;
    }

    @Override
    public String apply(Object o) {
        return rawTemplateSegment;
    }
}
