package org.zed.kafkaQuacker.Template;

public class StaticSlot extends BasicSlot {
    public StaticSlot(String templateSegment) {
        super(templateSegment);
    }

    @Override
    public String apply(Object o) {
        return rawTemplateSegment;
    }
}
