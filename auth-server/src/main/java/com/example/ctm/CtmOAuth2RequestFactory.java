package com.example.ctm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;

import java.util.Map;

/**
 * Created by pweicai on 28/04/17.
 */
public class CtmOAuth2RequestFactory extends DefaultOAuth2RequestFactory {

    private static final Logger LOG = LoggerFactory.getLogger(CtmOAuth2RequestFactory.class.getName());

    public CtmOAuth2RequestFactory(ClientDetailsService clientDetailsService) {
        super(clientDetailsService);
    }

    @Override
    public TokenRequest createTokenRequest(Map<String, String> requestParameters, ClientDetails authenticatedClient) {
        final TokenRequest tokenRequest = super.createTokenRequest(requestParameters, authenticatedClient);
        final CtmTokenRequest result = new CtmTokenRequest();

        final String idProvider = requestParameters.get("idprovider");
        LOG.info("idProvider is:" + idProvider);
        result.setIdProvider(idProvider);
        result.setClientId(tokenRequest.getClientId());
        result.setGrantType(tokenRequest.getGrantType());
        result.setScope(tokenRequest.getScope());
        result.setRequestParameters(tokenRequest.getRequestParameters());
        return tokenRequest;
    }
}
