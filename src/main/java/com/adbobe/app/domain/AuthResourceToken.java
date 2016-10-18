package com.adbobe.app.domain;

import com.adbobe.app.model.GuidIdentifiableDomain;
import org.springframework.security.oauth2.common.OAuth2AccessToken;


public class AuthResourceToken extends GuidIdentifiableDomain {

    private String personId;
    private String resourceId;
    private OAuth2AccessToken accessToken;

    public AuthResourceToken(String personId, String resourceId, OAuth2AccessToken accessToken) {
        this.personId = personId;
        this.resourceId = resourceId;
        this.accessToken = accessToken;
    }

    public OAuth2AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(OAuth2AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

}
