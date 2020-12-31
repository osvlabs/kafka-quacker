# kafka-quacker Kafka 模拟数据发送器

Send mock JSON/String data to your Kafka topic.

## Usage docker
```
docker run 
  -e QUACKER_BOOTSTRAP_SERVER=kafka.host.com:9091
  -e QUACKER_TOPIC=my-topic/telemetry 
  -v /home/zgldh/my-project/data.json:/data.json 
  zgldh/kafka-quacker:1.1.0
```

## Usage docker-compose

Edit the `.env` then:  
```
docker-compose up 
```


## Variables

name| descrpition | sample
----|-------------|---------
QUACKER_BOOTSTRAP_SERVER| The server to your Kafka bootstrap server. | `kafka.host.com:9091`
QUACKER_SECURITY_PROTOCOL| `PLAINTEXT` or `SSL`. Not support SASL_PLAINTEXT or SASL_SSL yet| `PLAINTEXT`
QUACKER_TRUSTSTORE| The truststore file path.| `/secrets/kafka.truststore`
QUACKER_TRUSTSTORE_PASSWORD| The truststore file password.| `you-dont-know`   
QUACKER_KEYSTORE| The keystore file path.| `/secrets/kafka.keystore`
QUACKER_KEYSTORE_PASSWORD| The keystore file password.|   `you-dont-know`
QUACKER_KEY_PASSWORD| The key password.|   `you-dont-know`
QUACKER_TOPIC| Which topic do you want the mock data send to? |`your/topic/to/send`
QUACKER_CLIENTID| The client ID |`kafka-quacker`
QUACKER_INTERVAL| Time interval between two data sending. (ms) |`1000`
QUACKER_DATAFILE| The mock data template. |`./data.json`
QUACKER_DRYRUN| Dont push to server, just output payload. |""

## Custom Data
Please edit the file `data.json` to any text you want. It supports following placeholders:
- `q:float:{min},{max}` to generate a float number between [min, max).
- `q:int:{min},{max}` to generate an integer number between [min, max).
- `q:string:{str1},{str2},{str3},...,{strN}` to get one string from n strings randomly.
- `q:timestamp` to get a current timestamp. You could custom the date format: `q:timestamp:yyyy-MM-dd hh:mm:ss`. Please refer to https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html     


Currently, no more placeholders supported.

