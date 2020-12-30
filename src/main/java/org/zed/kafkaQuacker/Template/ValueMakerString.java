package org.zed.kafkaQuacker.Template;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValueMakerString extends ValueMaker {
    private static final Pattern STRING_PATTERN = Pattern.compile("(.*?)(,|$)");
    private ArrayList<String> items = new ArrayList<>();
    private int itemsLength = 0;

    public ValueMakerString(String rawParameter) throws Exception {
        super(rawParameter);
        Matcher matcher = STRING_PATTERN.matcher(rawParameter);
        if (!matcher.matches()) {
            throw new Exception("ValueMakerInt needs correct parameters `min:max`");
        }
        try {
            matcher.reset();
            while (matcher.find()) {
                String item = matcher.group(1);
                if (item.length() > 0) {
                    items.add(item);
                }
            }
        } catch (NumberFormatException e) {
            throw new Exception("ValueMakerInt needs correct parameters `min:max`. "
                    + rawParameter + " can not be parsed into Integer value.");
        }
        itemsLength = items.size();
    }

    @Override
    public String make() {
        return items.get((int) Math.floor(Math.random() * itemsLength));
    }
}