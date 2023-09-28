package uk.gov.bristol.send.fileupload;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ClamConnector {

    private static final int defaultConnectionTimeoutMs = 4000;
    private final String host;
    private final int port;
    private final int timeoutMs;
    protected Logger logger = LoggerFactory.getLogger(getClass());


    public ClamConnector(String host, int port, int timeoutMs) {
        this.host = host;
        this.port = port;
        this.timeoutMs = timeoutMs;
    }

    public ClamConnector(String host, int port) {
        this(host, port, defaultConnectionTimeoutMs);
    }

    public boolean canConnect() {
       boolean connect = false;
       
       SocketAddress sockaddr = new InetSocketAddress(host, port);
        try (Socket socket = new Socket()) {
            // Using Java 8 try-with-resource to make sure the socket will be closed
            socket.connect(sockaddr, this.timeoutMs);
            connect = true;
        } catch (IOException e) { 
            logger.error("ClamConnector could not get a response from clamAV");
        }

        return connect;
    }

    public void setLogger(Logger logger){
        this.logger = logger;
    }
}