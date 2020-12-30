package org.zed.kafkaQuacker.Template;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValueMakerInt extends ValueMaker {
    private static final Pattern INT_PATTERN = Pattern.compile("(.*),(.*)");
    private int minValue;
    private int maxValue;

    public ValueMakerInt(String rawParameter) throws Exception {
        super(rawParameter);
        Matcher matcher = INT_PATTERN.matcher(rawParameter);
        if (!matcher.matches()) {
            throw new Exception("ValueMakerInt needs correct parameters `min:max`");
        }
        try {
            minValue = Integer.parseInt(matcher.group(1));
            maxValue = Integer.parseInt(matcher.group(2));
        } catch (NumberFormatException e) {
            throw new Exception("ValueMakerInt needs correct parameters `min:max`. "
                    + rawParameter + " can not be parsed into Integer value.");
        }
    }

    @Override
    public String make() {
        return String.format("%d", Math.round(Math.random() * (maxValue - minValue) + minValue));
    }
}