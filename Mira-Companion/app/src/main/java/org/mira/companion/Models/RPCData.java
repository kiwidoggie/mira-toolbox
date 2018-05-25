package org.mira.companion.Models;


import com.chad.library.adapter.base.entity.MultiItemEntity;

public class RPCData implements MultiItemEntity {

    private  String data;
    private  int from;
    private int statusCode;
    private long timestamp;
    public static int SENT = 1;
    public static int RECEIVED = 2;

    @Override
    public String toString() {
        return "RPCData{" +
                "data='" + data + '\'' +
                ", from=" + from +
                ", statusCode=" + statusCode +
                ", timestamp=" + timestamp +
                '}';
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public static int getSENT() {
        return SENT;
    }

    public static void setSENT(int SENT) {
        RPCData.SENT = SENT;
    }

    public static int getRECEIVED() {
        return RECEIVED;
    }

    public static void setRECEIVED(int RECEIVED) {
        RPCData.RECEIVED = RECEIVED;
    }

    @Override
    public int getItemType() {
        return 0;
    }
}

