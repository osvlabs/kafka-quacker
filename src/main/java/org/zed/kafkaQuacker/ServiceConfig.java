package org.zed.kafkaQuacker;

public class ServiceConfig {
    /**
     * kafka1.server.com:9093,kafka2.server.com:9093
     * http://kafka.apache.org/documentation/#bootstrap.servers
     */
    public static String QUACKER_BOOTSTRAP_SERVER = System.getenv("QUACKER_BOOTSTRAP_SERVER");

    /**
     * The Kafka security protocol. `PLAINTEXT` or `SSL`. Not support SASL_PLAINTEXT or SASL_SSL yet
     * Default: PLAINTEXT
     */
    public static String QUACKER_SECURITY_PROTOCOL = System.getenv("QUACKER_SECURITY_PROTOCOL");
    /**
     * /var/private/kafka.keystore
     * http://kafka.apache.org/documentation/#ssl.keystore.location
     */
    public static String QUACKER_KEYSTORE = System.getenv("QUACKER_KEYSTORE");
    /**
     * The password of the keystore
     * http://kafka.apache.org/documentation/#ssl.keystore.password
     */
    public static String QUACKER_KEYSTORE_PASSWORD = System.getenv("QUACKER_KEYSTORE_PASSWORD");
    /**
     * The password of the key pair
     * http://kafka.apache.org/documentation/#ssl.key.password
     */
    public static String QUACKER_KEY_PASSWORD = System.getenv("QUACKER_KEY_PASSWORD");
    /**
     * /var/private/kafka.truststore
     * http://kafka.apache.org/documentation/#ssl.truststore.location
     */
    public static String QUACKER_TRUSTSTORE = System.getenv("QUACKER_TRUSTSTORE");
    /**
     * The password of the truststore.
     * http://kafka.apache.org/documentation/#ssl.truststore.password
     */
    public static String QUACKER_TRUSTSTORE_PASSWORD = System.getenv("QUACKER_TRUSTSTORE_PASSWORD");

    /**
     * The Kafka topic we are going to emit data to.
     * Default: your/topic/of/data
     */
    public static String QUACKER_TOPIC = System.getenv("QUACKER_TOPIC");

    /**
     * The Kafka producer client ID
     * Default: kafka-quacker
     */
    public static String QUACKER_CLIENTID = System.getenv("QUACKER_CLIENTID");

    /**
     * Data emit frequency. (seconds)
     * Default: 1
     */
    public static int QUACKER_INTERVAL = 1000;

    /**
     * The data template file
     */
    public static String QUACKER_DATAFILE = System.getenv("QUACKER_DATAFILE");

    /**
     * Dry run will only generate data but not emit to Kafka server.
     */
    public static boolean QUACKER_DRYRUN = true;


    static {
        String RawQuackerInterval = System.getenv("QUACKER_INTERVAL");
        if (RawQuackerInterval != null && RawQuackerInterval.length() > 0) {
            ServiceConfig.QUACKER_INTERVAL = Integer.parseInt(RawQuackerInterval) * 1000;
        }
        String RawQuackerDryrun = System.getenv("QUACKER_DRYRUN");
        if (RawQuackerDryrun != null && RawQuackerDryrun.length() > 0) {
            RawQuackerDryrun = RawQuackerDryrun.toLowerCase();
            ServiceConfig.QUACKER_DRYRUN = RawQuackerDryrun.equals("1") || RawQuackerDryrun.equals("true");
        }

        System.out.println("QUACKER_BOOTSTRAP_SERVER = " + ServiceConfig.QUACKER_BOOTSTRAP_SERVER);
        System.out.println("QUACKER_SECURITY_PROTOCOL = " + ServiceConfig.QUACKER_SECURITY_PROTOCOL);
        System.out.println("QUACKER_KEYSTORE = " + ServiceConfig.QUACKER_KEYSTORE);
        System.out.println("QUACKER_KEYSTORE_PASSWORD =  <secret>");
        System.out.println("QUACKER_KEY_PASSWORD =  <secret>");
        System.out.println("QUACKER_TRUSTSTORE = " + ServiceConfig.QUACKER_TRUSTSTORE);
        System.out.println("QUACKER_TRUSTSTORE_PASSWORD =  <secret>");
        System.out.println("QUACKER_TOPIC = " + ServiceConfig.QUACKER_TOPIC);
        System.out.println("QUACKER_CLIENTID = " + ServiceConfig.QUACKER_CLIENTID);
        System.out.println("QUACKER_INTERVAL = " + ServiceConfig.QUACKER_INTERVAL);
        System.out.println("QUACKER_DATAFILE = " + ServiceConfig.QUACKER_DATAFILE);
        System.out.println("QUACKER_DRYRUN = " + ServiceConfig.QUACKER_DRYRUN);
    }
}
