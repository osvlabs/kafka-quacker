package org.zed.kafkaQuacker.Template;

import org.zed.kafkaQuacker.DataBuilder;

public class ValueTemplateDynamicSlot extends ValueTemplateSlot {
    public ValueTemplateDynamicSlot(String templateSegment) {
        super(templateSegment);
    }

    @Override
    public String apply(Object o) {
        return rawTemplateSegment+":dynamic";
    }
}
