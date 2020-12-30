package org.zed.kafkaQuacker.Template;

import java.util.regex.Pattern;

public class ValueMakerString extends ValueMaker {
    private static final Pattern STRING_PATTERN = Pattern.compile("(.*?)(,|$)");

    public ValueMakerString(String rawParameter) {
        super(rawParameter);
    }

    @Override
    public String make() {
        return null;
    }
}

//                stringMatcher, _:=regexp.Compile("(.*?)(,|$)")
//                result:=stringMatcher.FindAllSubmatch(parameters, -1)
//                stringsCount:=float64(len(result))
//                stringIndex:=int(math.Floor(rand.Float64() * stringsCount))
//                randomStr:=string(result[stringIndex][1])
//                return fmt.Sprintf("\"%v\"", randomStr)