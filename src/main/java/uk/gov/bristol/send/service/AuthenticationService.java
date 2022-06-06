package uk.gov.bristol.send.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import uk.gov.bristol.send.model.Owner;

@Service
@EnableConfigurationProperties
public class AuthenticationService {

    private Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

    @Value("${default.user.token}")
    private String MS_ID_TOKEN_DEFAULT;
   
    // Get the user name of logged in user here from the request
    public Owner getLoggedInUser(HttpServletRequest request) throws Exception{
        Owner owner = new Owner();

        String token;
        // Check the "x-ms-token-aad-id-token" request header exists
        boolean tokenHeaderExists = request.getHeader("x-ms-token-aad-id-token") != null;        
        if (tokenHeaderExists) {
            // Use the "x-ms-token-aad-id-token" request header
            token = request.getHeader("x-ms-token-aad-id-token");
        } else {
            // Hardcoded value for testing
            token = MS_ID_TOKEN_DEFAULT;
        }
        
        //overrideToken query param is used to test the Failure Authentication Service, by passing an in-valid token
        token = StringUtils.isNotEmpty(request.getParameter("overrideToken")) ? request.getParameter("overrideToken"): token;
    
        // Decode the token
        try {
            DecodedJWT jwt = JWT.decode(token); 
            
            //Get a valid email for user
            String currentUserEmail = jwt.getClaim("email").asString();
            if(StringUtils.isBlank(currentUserEmail)){
                currentUserEmail = jwt.getClaim("preferred_username").asString();
            }

            //Get a valid name for user
            String currentUserFirstName = jwt.getClaim("given_name").asString();
            String currentUserLastName = jwt.getClaim("family_name").asString();
            String currentUserName = jwt.getClaim("name").asString();
            if(StringUtils.isBlank(currentUserFirstName) || StringUtils.isBlank(currentUserLastName) && !currentUserName.isBlank())
            {
                owner.setOwnerName(currentUserName);
            }else{
                owner.setOwnerName(currentUserFirstName + " " + currentUserLastName);
            }
            owner.setOwnerEmail(currentUserEmail);

        } catch (JWTDecodeException e) {
            LOGGER.error("Invalid token: " + e.getMessage());
            throw new Exception("Invalid token");
        }
        return owner;
    }

    protected void setDefaultUserToken(String defaultToken){
        this.MS_ID_TOKEN_DEFAULT = defaultToken;
    }

	protected void setLog(Logger log) {
		this.LOGGER = log;
	}

}