package uk.gov.bristol.send.fileupload;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.AfterAll;
import org.mockito.Mock;
import org.slf4j.Logger;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import uk.gov.bristol.send.fileupload.ClamConnector;


@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClamConnectorTest {
    
    private ClamConnector clamConnector;

    @Mock
    private Logger log;

    @AfterAll
    public void tearDown() throws Exception {
        clamConnector = null;
    }

    @Test
    public void whenClamConnectorCanConnect_returnsTrue() throws Exception {
        clamConnector = new ClamConnector("www.bristol.gov.uk", 80, 4000);        
        assertTrue(clamConnector.canConnect());
    }  
    
    @Test
    public void whenClamConnectorCannotConnect_returnsFalse_logsError() throws Exception {
        clamConnector = new ClamConnector("localhost", 1234, 1);  
        clamConnector.setLogger(log);     
        assertFalse(clamConnector.canConnect());
        verify(log).error("ClamConnector could not get a response from clamAV"); 
    } 

}
