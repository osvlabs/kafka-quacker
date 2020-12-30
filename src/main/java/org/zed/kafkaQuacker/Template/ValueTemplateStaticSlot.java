package org.zed.kafkaQuacker.Template;

public class ValueTemplateStaticSlot extends ValueTemplateSlot {
    public ValueTemplateStaticSlot(String templateSegment) {
        super(templateSegment);
    }

    @Override
    public String apply(Object o) {
        return rawTemplateSegment;
    }
}
