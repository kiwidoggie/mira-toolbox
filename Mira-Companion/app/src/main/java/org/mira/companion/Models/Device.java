package org.mira.companion.Models;

/**
 * Created by @AstonBraham on 24/05/2018.
 */
public class Device {

    private int status;
    private String ip;
    private String port;
    private String mac;
    private String name;
    private String firmware_version;
    private String mira_version;

    private static Device INSTANCE = new Device();

    public static Device getInstance() {
        return(INSTANCE);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirmware_version() {
        return firmware_version;
    }

    public void setFirmware_version(String firmware_version) {
        this.firmware_version = firmware_version;
    }

    public String getMira_version() {
        return mira_version;
    }

    public void setMira_version(String mira_version) {
        this.mira_version = mira_version;
    }

    @Override
    public String toString() {
        return "Device{" +
                "status=" + status +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", mac='" + mac + '\'' +
                ", name='" + name + '\'' +
                ", firmware_version='" + firmware_version + '\'' +
                ", mira_version='" + mira_version + '\'' +
                '}';
    }
}
