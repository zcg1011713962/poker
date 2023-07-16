package org.cloud.rtsp.entity;


import org.cloud.entity.common.BaseRequest;

public class RtspEntity extends BaseRequest {

    private String uri;
    private String realm;
    private String nonce;
    private String userName;
    private String password;
    private String authenticate;

    public RtspEntity(String uri, String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.uri = uri;
    }


    public String getAuthenticate() {
        return authenticate;
    }

    public void setAuthenticate(String authenticate) {
        this.authenticate = authenticate;
    }

    public String getUri() {
        return uri;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
