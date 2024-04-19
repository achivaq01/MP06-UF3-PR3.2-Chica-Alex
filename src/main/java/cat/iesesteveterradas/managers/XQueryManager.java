package cat.iesesteveterradas.managers;


import cat.iesesteveterradas.readers.XQueryReader;
import cat.iesesteveterradas.servers.XQueryServer;
import org.basex.api.client.ClientSession;
import org.basex.core.cmd.Open;
import org.basex.core.cmd.XQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class XQueryManager {

    private static final Logger logger = LoggerFactory.getLogger(XQueryManager.class);

    private static XQueryManager xQueryManagerInstance;
    private final XQueryReader xQueryReader;

    private XQueryManager() {
        xQueryReader = new XQueryReader();
    }

    public static XQueryManager getInstance() {
        if (xQueryManagerInstance == null) {
            xQueryManagerInstance = new XQueryManager();
        }
        return xQueryManagerInstance;
    }

    public String executeXQueryFromFile(String filePath, String databaseName, XQueryServer server) {
        String xQuery = xQueryReader.getXQueryFromFile(filePath);
        String xQueryResult = "";
        ClientSession session = server.openSession();

        try {
            session.execute(new Open(databaseName));
            xQueryResult = session.execute(new XQuery(xQuery));

        } catch (IOException | NullPointerException e) {
            String exceptionName = e.getClass().getSimpleName();
            logger.error("executeXQueryFromFile | {}", exceptionName);
        }

        server.closeSession();
        return xQueryResult;
    }

}
