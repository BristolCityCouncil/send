package uk.gov.bristol.send.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import uk.gov.bristol.send.model.Owner;
import uk.gov.bristol.send.repo.AssessmentsRepository;

@MockBean(AssessmentsRepository.class)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnableConfigurationProperties
public class AuthenticationServiceTest {
        
    private AuthenticationService authenticationService;

    private final String MS_ID_TOKEN_NAME = "x-ms-token-aad-id-token";
    private final String MS_ID_TOKEN_INVALID = "asdfnotvalidtokenwoacidmtne";
    //TODO change the found user token for a generic one where given_name + family_name does not equal name claim
    //TODO it must also have email address claim filled in. 
    private final String FOUND_USER_NAME = "";
    private final String FOUND_USER_EMAIL = "";    
    private final String DEFAULT_USER_NAME = "Abe Lincoln";
    private final String DEFAULT_USER_EMAIL = "abeli@microsoft.com";
  
    private String MS_ID_TOKEN_FOUND; 
    private String MS_ID_TOKEN_DEFAULT;
    
    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
	private Logger log;

    @BeforeAll
	public void setUp() {
        authenticationService = new AuthenticationService(); 
        Properties properties = new Properties();    

        // TODO: shouldn't need to load from a properties file or have authenticationService.setDefaultUserToken. 
        // Been unable to get the class under test to read the /src/main/resources/application.properties file, even though that works at runtime. 
        try{
            // this will load file in /src/test/resources not /src/main/resources
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties")); 
            MS_ID_TOKEN_FOUND = properties.getProperty("found.user.token");
            MS_ID_TOKEN_DEFAULT = properties.getProperty("default.user.token");
            authenticationService.setDefaultUserToken(MS_ID_TOKEN_DEFAULT);
        }catch(IOException ioe){
            System.out.println("Unable to get test properties");
        }    
    }

	@AfterAll
	public void tearDown() throws Exception {
        authenticationService = null;
    }

    @Test
    public void whenGetLoggedInUser_headerNotFound_usesDefaultToken()  throws Exception {
        when(httpServletRequest.getHeader(MS_ID_TOKEN_NAME)).thenReturn(null);                  
        Owner owner = authenticationService.getLoggedInUser(httpServletRequest);
        assert(owner.getOwnerEmail()).equals(DEFAULT_USER_EMAIL);     
        assert(owner.getOwnerName()).equals(DEFAULT_USER_NAME);     
    }

    @Test
    public void whenGetLoggedInUser_tokenInvalid_throwsJWTDecodeException() throws Exception {
        when(httpServletRequest.getHeader(MS_ID_TOKEN_NAME)).thenReturn(MS_ID_TOKEN_INVALID);
        authenticationService.setLog(log);        
        assertThrows(Exception.class, () -> {
        	authenticationService.getLoggedInUser(httpServletRequest); 
       });
        verify(log).error("Invalid token: The token was expected to have 3 parts, but got 1.");        
    }

    @Test
    public void whenGetLoggedInUser_tokenValid_returnsUser() throws Exception {
        when(httpServletRequest.getHeader(MS_ID_TOKEN_NAME)).thenReturn(MS_ID_TOKEN_FOUND);
        Owner owner = authenticationService.getLoggedInUser(httpServletRequest);
        assert(owner.getOwnerEmail()).equals(FOUND_USER_EMAIL);     
        assert(owner.getOwnerName()).equals(FOUND_USER_NAME); 
    }

}
