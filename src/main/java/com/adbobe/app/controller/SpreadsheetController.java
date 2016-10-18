package com.adbobe.app.controller;

import com.adbobe.app.security.OAuthTokenServices;
import com.adbobe.app.service.GoogleCloudAuthenticationService;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by venkatamunnangi on 10/4/16.
 */
@RestController
public class SpreadsheetController {

    @Autowired
    GoogleCloudAuthenticationService googleCloudAuthenticationService;

    @Autowired
    OAuthTokenServices tokenServices;

    @Autowired
    @Qualifier("googleRestTemplate")
    OAuth2RestOperations restTemplate;

    @Autowired
    OAuth2ProtectedResourceDetails resource;

    @RequestMapping("/")
    public String index() throws Exception {

        System.out.println("HELLO");
        //Credential c = googleCloudAuthenticationService.authorize();

        GoogleCredential gc = googleCloudAuthenticationService.getCredentials();
        gc.refreshToken();
        System.out.println(gc.toString());
        System.out.println(gc.getAccessToken());
        System.out.println(gc.getAccessToken());

        return "Greetings from Spring Boot!";
    }

    @RequestMapping(value = "/auth")
    public @ResponseBody
    String handleAuthCode() {
        System.out.println("HELLO2");
        try {
            OAuth2AccessToken token = restTemplate.getAccessToken();
            tokenServices.saveAccessToken(resource, SecurityContextHolder.getContext().getAuthentication(), token);
            //dataService.addSubscriptions(ActiveUserAccessor.getLoggedInPersonId());
            //return new Response(ResponseType.SUCCESS, null, null, "Token expires on " + token.getExpiration());
            StringBuilder sb = new StringBuilder();
            sb.append("<!DOCTYPE html>");
            sb.append("<html>");
            sb.append("    <head>");
            sb.append("        <title>OAuth Success</title>");
            sb.append("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
            sb.append("        <script type=\"text/javascript\">");
            sb.append("           window.onload = close();");
            sb.append("        </script>");
            sb.append("    </head>");
            sb.append("    <body></body>");
            sb.append("</html>");
            return sb.toString();
        } catch (Exception ex) {
            StringBuilder sb = new StringBuilder();
            sb.append("<!DOCTYPE html>");
            sb.append("<html>");
            sb.append("    <head>");
            sb.append("        <title>OAuth Failure</title>");
            sb.append("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
            sb.append("    </head>");
            sb.append("    <body>");
            StringWriter writer = new StringWriter();
            ex.printStackTrace(new PrintWriter(writer));
            sb.append(writer.toString());
            sb.append("    </body>");
            sb.append("</html>");
            return sb.toString();
        }
    }

}
