package org.zed.kafkaQuacker;

import java.util.Arrays;

public class QuackerMessage {
    private String key;
    private byte[] payload;

    public QuackerMessage(String key, byte[] payload) {
        this.key = key;
        this.payload = payload;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
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
