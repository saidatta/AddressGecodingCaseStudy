package com.adbobe.app.config;

import com.adbobe.app.security.OAuthTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.Collections;

/**
 * Created by venkatamunnangi on 10/11/16.
 */
@EnableOAuth2Client
@Configuration
@PropertySource({"classpath:application.yml"})
public class GoogleOauth2Configuration {

    @Autowired
    private Environment env;

    @Autowired
    OAuthTokenServices tokenServices;

//    @Resource
//    @Qualifier("accessTokenRequest")
//    private AccessTokenRequest accessTokenRequest;


    @Value("${google.cliendId}")
    private String clientId;

    @Value("${google.clientSecret}")
    private String clientSecret;

    @Value("${google.accessTokenUri}")
    private String accessTokenUri;

    @Value("${google.userAuthorizationUri}")
    private String userAuthorizationUri;

    @Value("${google.authCallbackUri}")
    private String googleAuthCallbackUri;

    @Bean
    public OAuth2ProtectedResourceDetails googleResource() {
        System.out.println("Authentication Resourece!!!! ");
        System.out.println("Authentication Resourece!!!! ");

        System.out.println("Authentication Resourece!!!! ");
        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
        details.setId("google-oauth-client");
        details.setClientId(clientId);
        details.setClientSecret(clientSecret);
        details.setAccessTokenUri(accessTokenUri);
        details.setUserAuthorizationUri(userAuthorizationUri);
        //details.setTokenName(env.getProperty("google.authorization.code"));
        details.setScope(Collections.singletonList("https://www.googleapis.com/auth/devstorage.full_control"));
        details.setUseCurrentUri(false);
        details.setPreEstablishedRedirectUri(googleAuthCallbackUri);
//        details.setUseCurrentUri(false);
        details.setAuthenticationScheme(AuthenticationScheme.query);
        details.setClientAuthenticationScheme(AuthenticationScheme.form);
        return details;
    }

    @Bean
    public OAuth2RestTemplate googleRestTemplate(OAuth2ClientContext oAuth2ClientContext) {
        OAuth2RestTemplate template =  new OAuth2RestTemplate(googleResource(),  new DefaultOAuth2ClientContext());
        AccessTokenProviderChain provider = new AccessTokenProviderChain(Collections.singletonList(new AuthorizationCodeAccessTokenProvider()));
        provider.setClientTokenServices(tokenServices);
        template.setAccessTokenProvider(provider);

        return template;
    }
}
