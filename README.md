# kafka-quacker

Send mock data to your Kafka topic.

## Usage docker
```
docker run 
  -e QUACKER_HOST=kafka.host.com 
  -e QUACKER_PORT=1883 
  -e QUACKER_USERNAME=kafka-username 
  -e QUACKER_PASSWORD=kafka-password 
  -e QUACKER_TOPIC=my-topic/telemetry 
  -v /home/zgldh/my-project/data.json:/data.json 
  zgldh/kafka-quacker:1.0
```

## Usage docker-compose

Edit the docker-compose.yml  
```
docker-compose up 
```


## Variables

name| descrpition | sample
----|-------------|---------
QUACKER_HOST| The host to your Kafka bootstrap server. | `kafka.host.com`
QUACKER_PORT| The Kafka bootstrap port. |`9091`
QUACKER_SECURITY_PROTOCOL| `PLAINTEXT` or `SSL`. Not support SASL_PLAINTEXT or SASL_SSL yet| `PLAINTEXT`
QUACKER_TRUSTSTORE| The truststore file path.| `/secrets/kafka.truststore`
QUACKER_TRUSTSTORE_PASSWORD| The truststore file password.| `you-dont-know`   
QUACKER_KEYSTORE| The keystore file path.| `/secrets/kafka.keystore`
QUACKER_KEYSTORE_PASSWORD| The keystore file password.|   `you-dont-know`
QUACKER_KEY_PASSWORD| The key password.|   `you-dont-know`
QUACKER_TOPIC| Which topic do you want the mock data send to? |`your/topic/to/send`
QUACKER_CLIENTID| The client ID |`kafka-quacker`
QUACKER_INTERVAL| Time interval between two data sending. |`1`
QUACKER_DATAFILE| The mock data template. |`./data.json`
QUACKER_DRYRUN| Dont push to server, just output payload. |""

## Custom Data
Please edit the file `data.json` to any text you want. It supports following placeholders:
- `q:float:{min},{max}` to generate a float number between [min, max).
- `q:int:{min},{max}` to generate an integer number between [min, max).
- `q:string:{str1},{str2},{str3},...,{strN}` to get one string from n strings randomly.

Currently, no more placeholders supported.

