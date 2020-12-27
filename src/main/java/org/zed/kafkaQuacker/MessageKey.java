package org.zed.kafkaQuacker;

public class MessageKey {
    private String identifier;
    private String deviceMold;

    public MessageKey(String identifier, String deviceMold) {
        this.identifier = identifier;
        this.deviceMold = deviceMold;
    }

    public String getDeviceMold() {
        return deviceMold;
    }

    public void setDeviceMold(String deviceMold) {
        this.deviceMold = deviceMold;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return String.format("%s:%s", deviceMold, identifier);
    }
}
