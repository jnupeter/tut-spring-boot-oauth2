package com.example.ctm;

import org.springframework.security.oauth2.provider.TokenRequest;

import java.util.Collection;
import java.util.Map;

/**
 * Created by pweicai on 28/04/17.
 */
public class CtmTokenRequest extends TokenRequest {

    private String idProvider;

    protected CtmTokenRequest() {
        super();
    }


    public CtmTokenRequest(Map<String, String> requestParameters, String clientId, Collection<String> scope,
                           String grantType, String idProvider) {
        super(requestParameters, clientId, scope, grantType);
        this.idProvider = idProvider;
    }

    public void setIdProvider(final String idProvider) {
        this.idProvider = idProvider;
    }

    public String getIdProvider() {
        return this.idProvider;
    }
}
