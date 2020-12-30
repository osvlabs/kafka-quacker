package org.zed.kafkaQuacker.Template;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DynamicSlot extends BasicSlot {

    private static final Pattern TYPE_PATTERN = Pattern.compile("\"q:(.*):(.*)\"");

    private String valueType;
    private String slotRawParameter;
    private ValueMaker valueMaker;

    public DynamicSlot(String templateSegment) throws Exception {
        super(templateSegment);
        Matcher matcher = TYPE_PATTERN.matcher(templateSegment);
        if(!matcher.matches()){
            throw new Exception("Dynamic Slot has wrong template: "+templateSegment);
        }
        valueType = matcher.group(1);
        slotRawParameter  =matcher.group(2);
        switch (valueType){
            case "float":
                valueMaker = new ValueMakerFloat(slotRawParameter);
                break;
            case "int":
                valueMaker = new ValueMakerInt(slotRawParameter);
                break;
            case "string":
                valueMaker = new ValueMakerString(slotRawParameter);
                break;
            default:
                throw new Exception("Dynamic Slot has unknown type: "+valueType);
        }
    }

    @Override
    public String apply(Object o) {
        return valueMaker.make();
    }
}
