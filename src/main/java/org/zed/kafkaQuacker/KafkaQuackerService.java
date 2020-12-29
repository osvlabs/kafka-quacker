package org.zed.kafkaQuacker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Discards any incoming data.
 */
public class KafkaQuackerService {

    private final AtomicBoolean running = new AtomicBoolean();

    public KafkaQuackerService() {
    }

    public void run() {
        addShutdownHook();
        try {
            startProducer();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.shutdownGracefully();
        }
    }

    private void startProducer() throws IOException {
        System.out.println("Quacker starting...");
        MessageProducer mp = null;
        DataBuilder db = DataBuilder.getInstance();
        db.init(ServiceConfig.QUACKER_DATAFILE);

        String publishLabel = "Publish";
        if (ServiceConfig.QUACKER_DRYRUN) {
            System.out.println("Dry run");
            publishLabel = "Dry run";
        } else {
            mp = MessageProducer.getInstance();
            mp.init();
        }

        System.out.println(String.format("Kafka bootstrap server %s", ServiceConfig.QUACKER_BOOTSTRAP_SERVER));
        System.out.println("Security Protocol " + ServiceConfig.QUACKER_SECURITY_PROTOCOL);
        System.out.println("Client ID " + ServiceConfig.QUACKER_CLIENTID);
        System.out.println("Publisher Started to: " + ServiceConfig.QUACKER_TOPIC);

        running.set(true);
        while (running.get()) {
            System.out.println(String.format("%s ---- %s ----\n", new Date().toString(), publishLabel));

            QuackerMessage message = db.getMessage();

            if (!ServiceConfig.QUACKER_DRYRUN) {
                mp.send(message.getKey(), message.getPayload());
            }
            System.out.println(message);
            try {
                Thread.sleep(ServiceConfig.QUACKER_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
                running.set(false);
            }
        }

        if (ServiceConfig.QUACKER_DRYRUN) {
            System.out.println("Publisher Terminated");
        } else {
            if (mp != null) {
                mp.shutdownGracefully();
            }
            System.out.println("Publisher Disconnected");
        }
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
        new ServiceConfig();
        new KafkaQuackerService().run();
    }
}