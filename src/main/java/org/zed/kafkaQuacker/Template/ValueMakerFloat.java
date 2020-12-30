package org.zed.kafkaQuacker.Template;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValueMakerFloat extends ValueMaker {
    private static final Pattern FLOAT_PATTERN = Pattern.compile("(.*),(.*)");
    private float minValue;
    private float maxValue;

    public ValueMakerFloat(String rawParameter) throws Exception {
        super(rawParameter);
        Matcher matcher = FLOAT_PATTERN.matcher(rawParameter);
        if (!matcher.matches()) {
            throw new Exception("ValueMakerFloat needs correct parameters `min:max`");
        }
        try {
            minValue = Float.parseFloat(matcher.group(1));
            maxValue = Float.parseFloat(matcher.group(2));
        } catch (NumberFormatException e) {
            throw new Exception("ValueMakerFloat needs correct parameters `min:max`. "
                    + rawParameter + " can not be parsed into float value.");
        }
    }

    @Override
    public String make() {
        return String.format("%f", Math.random() * (maxValue - minValue) + minValue);
    }
}