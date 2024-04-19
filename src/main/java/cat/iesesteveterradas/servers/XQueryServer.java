package cat.iesesteveterradas.servers;

import org.basex.api.client.ClientSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class XQueryServer {
    private static final Logger logger = LoggerFactory.getLogger(XQueryServer.class);

    private final String host;
    private final String username;
    private final String password;
    private final int port;
    private ClientSession session;

    public XQueryServer(String host, String username, String password, int port) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;
    }

    public ClientSession openSession() {
        try {
            session = new ClientSession(host, port, username, password);
        } catch (IOException e) {
            String exceptionName = e.getClass().getSimpleName();
            logger.error("openSession | {}", exceptionName);
        }
        return session;
    }

    public void closeSession() {
        try {
            session.close();
        } catch (IOException | NullPointerException e) {
            String exceptionName = e.getClass().getSimpleName();
            logger.error("openSession | {}", exceptionName);
        }
    }
}
