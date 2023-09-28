package uk.gov.bristol.send.fileupload.factories;

import org.springframework.stereotype.Component;
import fi.solita.clamav.ClamAVClient;

@Component
public class ClamAVClientFactory {
   public ClamAVClient createClamAVClient(String host, int port){
        return new ClamAVClient(host, port);
   } 

   public ClamAVClient createClamAVClient(String host, int port, int timeout){
    return new ClamAVClient(host, port, timeout);
} 
}
