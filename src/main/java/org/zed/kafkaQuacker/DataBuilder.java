package org.zed.kafkaQuacker;

import com.fasterxml.jackson.jr.ob.JSON;
import com.fasterxml.jackson.jr.ob.impl.DeferredMap;
import org.zed.kafkaQuacker.Template.DynamicSlot;
import org.zed.kafkaQuacker.Template.BasicSlot;
import org.zed.kafkaQuacker.Template.StaticSlot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataBuilder {
    private static DataBuilder instance;
    private String templatePath;
    private Vector<BasicSlot> compiledKeyTemplate = new Vector<>();
    private Vector<BasicSlot> compiledValueTemplate = new Vector<>();

    private DataBuilder() {
    }

    public synchronized static DataBuilder getInstance() {
        if (instance == null) {
            instance = new DataBuilder();
        }
        return instance;
    }

    public void init(String templatePath) throws Exception {
        this.templatePath = templatePath;

        File templateFile = new File(this.templatePath);
        Object templateContent = JSON.std.anyFrom(templateFile);

        compiledKeyTemplate = compileTemplate(((DeferredMap) templateContent).get("KAFKA_MSG_KEY").toString());
        compiledValueTemplate = compileTemplate(JSON.std.with(JSON.Feature.PRETTY_PRINT_OUTPUT).asString(((DeferredMap) templateContent).get("KAFKA_MSG_VALUE")));
    }

    private Vector<BasicSlot> compileTemplate(String rawTemplate) throws Exception {
        Vector<BasicSlot> slots = new Vector<>();
        Pattern pattern = Pattern.compile("(\"q:.*\")");
        Matcher matcher = pattern.matcher(rawTemplate);
        int templateStartIndex = 0;
        while (matcher.find()) {
            String staticSegment = rawTemplate.substring(templateStartIndex, matcher.start());
            if (staticSegment.length() > 0) {
                slots.add(new StaticSlot(staticSegment));
            }

            String dynamicSegment = rawTemplate.substring(matcher.start(), matcher.end());
            slots.add(new DynamicSlot(dynamicSegment));

            templateStartIndex = matcher.end();
        }
        String tailStaticSegment = rawTemplate.substring(templateStartIndex);
        if (tailStaticSegment.length() > 0) {
            slots.add(new StaticSlot(tailStaticSegment));
        }
        return slots;
    }

    public QuackerMessage getMessage() {
        return new QuackerMessage(
                generateKafkaMessageKey(),
                generateKafkaMessageValue()
        );
    }

    private String generateKafkaMessageKey() {
        return replaceContents(this.compiledKeyTemplate);
    }

    private byte[] generateKafkaMessageValue() {
        String resultContent = replaceContents(this.compiledValueTemplate);
        return resultContent != null ? resultContent.getBytes() : new byte[0];
    }

    private String replaceContents(Vector<BasicSlot> parsedTemplateSlots) {
        ArrayList<String> result = new ArrayList<>();
        parsedTemplateSlots.forEach(new Consumer<BasicSlot>() {
            @Override
            public void accept(BasicSlot valueTemplateSlot) {
                result.add(valueTemplateSlot.apply(null));
            }
        });
        return result.stream().reduce("", new BinaryOperator<String>() {
            @Override
            public String apply(String s, String s2) {
                return s + s2;
            }
        });
    }
}

    //    // DataBuilderConfig - Configuration of MQTT server
//    type DataBuilderConfig
//
//    struct {
//        Path string // Path - Data template file path
//    }
//
//    // DataBuilder - The databuilder class.
//    type DataBuilder
//
//    struct {
//        config DataBuilderConfig
//        template string
//        slots map[ int]Slot
//    }
//
//    // NewDataBuilder - Create a new DataBuilder object
//    func NewDataBuilder(config DataBuilderConfig) DataBuilder {
//        builder:=DataBuilder {
//            config:
//            config,
//        }
//        err:=builder.parse()
//        if err != nil {
//            panic(err)
//        }
//        return builder
//    }
//
//    // Close - Close the databuilder mission
//    func(b *DataBuilder) Close(){
//        }
//
//// Parse - Parse the template JSON to initialize the builder
//        func(b*DataBuilder)parse()error{
//        rawTemplate,err:=ioutil.ReadFile(b.config.Path)
//        if err!=nil{
//        return err
//        }
//
//        matcher,err:=regexp.Compile("(\"q:.*\")")
//        if err!=nil{
//        return err
//        }
//
//        slotCount:=0
//        innerMatcher,err:=regexp.Compile("\"q:(.*):(.*)\"")
//        if err!=nil{
//        return err
//        }
//        slots:=make(map[int]Slot)
//
//        parsedTemplate:=matcher.ReplaceAllFunc(rawTemplate,func(bytes[]byte)[]byte{
//        slotCount=slotCount+1
//        slots[slotCount]=Slot{
//        count:slotCount,
//        seed:int(rand.Float32()*100),
//        provider:b.parseProvider(slotCount,innerMatcher,bytes),
//        }
//
//        return[]byte("${"+strconv.Itoa(slotCount)+"}")
//        })
//
//        // fmt.Printf("parsed %s\n", string(parsedTemplate))
//
//        b.template=string(parsedTemplate)
//        b.slots=slots
//
//        return nil
//        }
//
// parseProvider - Parse the slot to get value provider function.
//    String parseProvider(slotCount int, innerMatcher*regexp.Regexp, bytes[]byte) {
//        result:=innerMatcher.FindAllSubmatch(bytes, 10)
//        valueType:=string(result[0][1])
//        parameters:=result[0][2]
//
//        return func() string {
//            if valueType == "float" {
//             \
//            }
//            if valueType == "int" {
//            }
//            if valueType == "string" {
//            }
//            return "unknown"
//        }
//    }

//// Make - Make a payload
//        func(b*DataBuilder)Make()(string,error){
//        matcher,err:=regexp.Compile(`\${\d*}`)
//        if err!=nil{
//        return"",err
//        }
//
//        payload:=matcher.ReplaceAllStringFunc(b.template,func(slotCountString string)string{
//        slotCount,err:=strconv.Atoi(strings.Trim(slotCountString,"${}"))
//        if err!=nil{
//        panic(err)
//        }
//        return b.slots[slotCount].provider()
//        })
//
//        return payload,nil
//        }
//
//        type Slot struct{
//        count int
//        seed int
//        provider Provider
//        }
//
//        type Provider func()string
