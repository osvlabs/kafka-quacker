package org.zed.kafkaQuacker;

import java.util.Arrays;

public class QuackerMessage {
    private MessageKey key;
    private byte[] payload;

    public QuackerMessage(MessageKey key, byte[] payload) {
        this.key = key;
        this.payload = payload;
    }

    public MessageKey getKey() {
        return key;
    }

    public void setKey(MessageKey key) {
        this.key = key;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return key + ", " + Arrays.toString(payload);
    }
}
