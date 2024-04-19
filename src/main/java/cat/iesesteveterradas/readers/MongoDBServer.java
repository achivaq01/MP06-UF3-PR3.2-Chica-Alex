package cat.iesesteveterradas.readers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBServer {
    private final MongoClient mongoClient;

    public MongoDBServer(String mongoServerUri) {
        mongoClient = MongoClients.create(mongoServerUri);
    }

    public void startSession() {
        mongoClient.startSession();
    }

    public void closeSession() {
        mongoClient.close();
    }

    public MongoDatabase getMongoClientDatabase(String databaseName) {
        return mongoClient.getDatabase(databaseName);
    }

    public MongoCollection<Document> getMongoCollection(String collectionName, MongoDatabase mongoDatabase) {
        return mongoDatabase.getCollection(collectionName);
    }


}
