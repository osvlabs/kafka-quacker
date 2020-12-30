package org.zed.kafkaQuacker.Template;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValueMakerTimestamp extends ValueMaker {
    private SimpleDateFormat ft;

    public ValueMakerTimestamp(String timeStampFormat) throws Exception {
        super(timeStampFormat);
        if (rawParameter == null || rawParameter.length() <= 0) {
            rawParameter = "yyyy-MM-dd hh:mm:ss";
        }
        ft = new SimpleDateFormat(rawParameter);
    }

    @Override
    public String make() {
        Date date = new Date();
        return ft.format(date);
    }
}