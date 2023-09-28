package uk.gov.bristol.send.fileupload.factories;

import org.springframework.stereotype.Component;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ClamConnectorFactoryTest {

    @Test
    public void factoryHasAnnotation() throws Exception{
        assertTrue(ClamConnectorFactory.class.isAnnotationPresent(Component.class));
    }   
}
