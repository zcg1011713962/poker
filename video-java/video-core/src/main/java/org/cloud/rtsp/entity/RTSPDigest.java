package org.cloud.rtsp.entity;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
@Slf4j
@Data
@ToString
public class RTSPDigest {
    private String username;
    private String realm;
    private String nonce;
    private String uri;
    private String method;
    private String password;

    public RTSPDigest(String username, String realm, String nonce, String uri, String method, String password) {
        this.username = username;
        this.realm = realm;
        this.nonce = nonce;
        this.uri = uri;
        this.method = method;
        this.password = password;
    }

    public String calculateResponse() {
        String HA1 = calculateHA1();
        String HA2 = calculateHA2();
        String response = calculateMD5(HA1 + ":" + nonce + ":" + HA2);
        return response;
    }

    private String calculateHA1() {
        String HA1Raw = username + ":" + realm + ":" + password;
        String HA1 = calculateMD5(HA1Raw);
        return HA1;
    }

    private String calculateHA2() {
        String HA2Raw = method + ":" + uri;
        String HA2 = calculateMD5(HA2Raw);
        return HA2;
    }

    private String calculateMD5(String input) {
        String output = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            output = byteArrayToHexString(md.digest(input.getBytes()));
        } catch (NoSuchAlgorithmException e) {
           log.error("{}", e);
        }
        return output;
    }

    private String byteArrayToHexString(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            sb.append(Integer.toString((data[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

}
