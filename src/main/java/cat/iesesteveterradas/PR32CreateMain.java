package cat.iesesteveterradas;

import cat.iesesteveterradas.managers.MongoDBManager;
import cat.iesesteveterradas.managers.XQueryManager;
import cat.iesesteveterradas.readers.MongoDBServer;
import cat.iesesteveterradas.servers.XQueryServer;
import org.apache.commons.lang3.StringEscapeUtils;
import org.bson.Document;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static cat.iesesteveterradas.Constants.FILE_PATH_XQUERY;

public class PR32CreateMain {

    private static final Logger logger = LoggerFactory.getLogger(PR32CreateMain.class);

    public static void main(String[] args) {

        String queryFilePath = FILE_PATH_XQUERY + "/most_viewed_questions_query.xq";
        XQueryManager xQueryManager = XQueryManager.getInstance();

        String host = "127.0.0.1";
        String username = "admin";
        String password = "123";
        String databaseName = "cooking";
        int port = 1984;

        logger.info("Connecting to BaseX...");
        XQueryServer xQueryServer = new XQueryServer(
                host,
                username,
                password,
                port
        );
        logger.info("Executing xQuery from file");
        String xQueryResult = xQueryManager.executeXQueryFromFile(queryFilePath, databaseName, xQueryServer);
        JSONArray xQueryResultJSONArray = new JSONArray(xQueryResult);
        List<Document> xQueryResultDocuments = new ArrayList<>();

        for (int index = 0; index < xQueryResultJSONArray.length(); index++) {
            Document xQueryResultDocument = Document.parse(xQueryResultJSONArray.getJSONObject(index).toString());
            String documentBody = xQueryResultDocument.getString("body");

            // decode html characters
            documentBody = StringEscapeUtils.unescapeHtml4(documentBody);
            xQueryResultDocument.put("body", documentBody);

            Document document = new Document();
            document.put("question", xQueryResultDocument);
            xQueryResultDocuments.add(document);
        }

        MongoDBManager mongoDBManager = MongoDBManager.getInstance();
        logger.info("Connecting to MongoDB...");
            MongoDBServer mongoDBServer = new MongoDBServer("mongodb://root:example@localhost:27017");
        logger.info("Inserting data...");
        mongoDBManager.insertDocumentListToDatabaseCollection(xQueryResultDocuments, "cooking", "posts", mongoDBServer);

        logger.info("Success!");
        mongoDBServer.closeSession();
    }

}
