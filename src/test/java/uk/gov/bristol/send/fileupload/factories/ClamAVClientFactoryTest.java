package uk.gov.bristol.send.fileupload.factories;

import org.springframework.stereotype.Component;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ClamAVClientFactoryTest {
    
    @Test
    public void factoryHasAnnotation() throws Exception{
        assertTrue(ClamAVClientFactory.class.isAnnotationPresent(Component.class));
    }
}
