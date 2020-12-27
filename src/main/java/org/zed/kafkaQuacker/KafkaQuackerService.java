package org.zed.kafkaQuacker;

/**
 * Discards any incoming data.
 */
public class KafkaQuackerService {

    public KafkaQuackerService() {
    }

    public void run() throws Exception {
        addShutdownHook();
        try {
            initComponents();
        } finally {
            this.shutdownGracefully();
        }
    }

    private void initComponents() {
        System.out.println("Connecting to Kafka...");
        MessageProducer mp = MessageProducer.getInstance();
        mp.init();
        System.out.println("Kafka connected.");
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    System.out.println("Shutting down...");
                    shutdownGracefully();
                } catch (Throwable t) {
                    t.printStackTrace();
                } finally {
                    System.out.println("Bye");
                }
            }
        });
    }

    private void shutdownGracefully() {

        MessageProducer mp = MessageProducer.getInstance();
        mp.shutdownGracefully();
    }

    public static void main(String[] args) throws Exception {
        ServiceConfig sc = new ServiceConfig();
        new KafkaQuackerService().run();
    }
}