package uk.gov.bristol.send.fileupload.factories;

import org.springframework.stereotype.Component;
import uk.gov.bristol.send.fileupload.ClamConnector;

@Component
public class ClamConnectorFactory {
    public ClamConnector createClamConnector(String host, int port, int timeout){
        return new ClamConnector(host, port, timeout);
    }

    public ClamConnector createClamConnector(String host, int port){
        return new ClamConnector(host, port);
    }
}
