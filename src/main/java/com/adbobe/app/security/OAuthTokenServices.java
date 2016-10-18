package com.adbobe.app.security;

import com.adbobe.app.domain.AuthResourceToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

@Service
public class OAuthTokenServices implements ClientTokenServices {

//    @Autowired
//    AuthResourceTokenRepository accessTokenRepository;

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
        AuthResourceToken resourceToken = null;// accessTokenRepository.findByPersonIdAndResourceId(getPersonId(authentication), resource.getId());
        return resourceToken == null ? null : resourceToken.getAccessToken();
    }

    @Override
    public void saveAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication, OAuth2AccessToken accessToken) {
        AuthResourceToken resourceToken =  null; //accessTokenRepository.findByPersonIdAndResourceId(getPersonId(authentication), resource.getId());
        if (resourceToken == null) {
            resourceToken = new AuthResourceToken(getPersonId(authentication), resource.getId(), accessToken);
        } else {
            resourceToken.setAccessToken(accessToken);
        }
        //accessTokenRepository.save(resourceToken);
    }

    @Override
    public void removeAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
        AuthResourceToken resourceToken =  null; //accessTokenRepository.findByPersonIdAndResourceId(getPersonId(authentication), resource.getId());
        //if (resourceToken != null)
            //accessTokenRepository.delete(resourceToken);
    }

    private String getPersonId(Authentication authentication) {
        return "1234";
    }
}
