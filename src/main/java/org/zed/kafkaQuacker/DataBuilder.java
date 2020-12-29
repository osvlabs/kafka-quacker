package org.zed.kafkaQuacker;

import com.fasterxml.jackson.jr.ob.JSON;
import com.fasterxml.jackson.jr.ob.impl.DeferredMap;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataBuilder {
    private static DataBuilder instance;
    private String templatePath;
    private String kafkaMsgKey;
    private String kafkaMsgValueTemplate;
    private HashMap<Integer, Function<Object, String>> slots = new HashMap();

    private DataBuilder() {
    }

    public synchronized static DataBuilder getInstance() {
        if (instance == null) {
            instance = new DataBuilder();
        }
        return instance;
    }

    public void init(String templatePath) throws IOException {
        this.templatePath = templatePath;

        File templateFile = new File(this.templatePath);
        Object templateContent = JSON.std.anyFrom(templateFile);

        kafkaMsgKey = ((DeferredMap) templateContent).get("KAFKA_MSG_KEY").toString();
        kafkaMsgValueTemplate = compileValueTemplate(JSON.std.with(JSON.Feature.PRETTY_PRINT_OUTPUT).asString(((DeferredMap) templateContent).get("KAFKA_MSG_VALUE")));
    }

    private String compileValueTemplate(String rawValueTemplate) {
        Pattern pattern = Pattern.compile("(\"q:.*\")");
        Matcher matcher = pattern.matcher(rawValueTemplate);
        while(matcher.find()){
            // TODO
        }
        return null;
    }

    public QuackerMessage getMessage() {
        return new QuackerMessage(
                generateKafkaMessageKey(),
                generateKafkaMessageValue()
        );
    }

    private String generateKafkaMessageKey() {
        return replaceContents(this.kafkaMsgKey);
    }

    private byte[] generateKafkaMessageValue() {
        String resultContent = replaceContents(this.kafkaMsgValueTemplate);
        return resultContent != null ? resultContent.getBytes() : new byte[0];
    }

    private String replaceContents(String raw) {
        return null;
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
//// parseProvider - Parse the slot to get value provider function.
//        func(b*DataBuilder)parseProvider(slotCount int,innerMatcher*regexp.Regexp,bytes[]byte)Provider{
//        result:=innerMatcher.FindAllSubmatch(bytes,10)
//        valueType:=string(result[0][1])
//        parameters:=result[0][2]
//
//        return func()string{
//        if valueType=="float"{
//        floatMatcher,_:=regexp.Compile("(.*),(.*)")
//        result:=floatMatcher.FindAllSubmatch(parameters,10)
//        minValue,err:=strconv.ParseFloat(string(result[0][1]),64)
//        if err!=nil{
//        panic(err)
//        }
//        maxValue,err:=strconv.ParseFloat(string(result[0][2]),64)
//        if err!=nil{
//        panic(err)
//        }
//        return strconv.FormatFloat(rand.Float64()*(maxValue-minValue)+minValue,'f',10,64)
//        }
//        if valueType=="int"{
//        intMatcher,_:=regexp.Compile("(.*),(.*)")
//        result:=intMatcher.FindAllSubmatch(parameters,10)
//        minValue,err:=strconv.ParseFloat(string(result[0][1]),64)
//        if err!=nil{
//        panic(err)
//        }
//        maxValue,err:=strconv.ParseFloat(string(result[0][2]),64)
//        if err!=nil{
//        panic(err)
//        }
//        return strconv.FormatInt(int64(rand.Float64()*(maxValue-minValue)+minValue),10)
//        }
//        if valueType=="string"{
//        stringMatcher,_:=regexp.Compile("(.*?)(,|$)")
//        result:=stringMatcher.FindAllSubmatch(parameters,-1)
//        stringsCount:=float64(len(result))
//        stringIndex:=int(math.Floor(rand.Float64()*stringsCount))
//        randomStr:=string(result[stringIndex][1])
//        return fmt.Sprintf("\"%v\"",randomStr)
//        }
//        return"unknown"
//        }
//        }
//
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
