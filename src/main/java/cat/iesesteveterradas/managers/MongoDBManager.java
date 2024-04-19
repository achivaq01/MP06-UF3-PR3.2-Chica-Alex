package cat.iesesteveterradas.managers;

import cat.iesesteveterradas.readers.MongoDBServer;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MongoDBManager {

    private static final Logger logger = LoggerFactory.getLogger(MongoDBManager.class);

    private static MongoDBManager mongoDBManagerInstance;

    private MongoDBManager() {}

    public static MongoDBManager getInstance() {
        if (mongoDBManagerInstance == null) {
            mongoDBManagerInstance = new MongoDBManager();
        }

        return mongoDBManagerInstance;
    }

    public void insertDocumentListToDatabaseCollection(List<Document> data, String databaseName, String collectionName, MongoDBServer mongoDBServer) {
        MongoDatabase mongoDatabase = mongoDBServer.getMongoClientDatabase(databaseName);
        MongoCollection<Document> mongoCollection = mongoDBServer.getMongoCollection(collectionName, mongoDatabase);

        mongoCollection.insertMany(data);
    }


}
