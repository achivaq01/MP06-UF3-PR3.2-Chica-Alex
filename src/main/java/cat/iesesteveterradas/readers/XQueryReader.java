package cat.iesesteveterradas.readers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public final class XQueryReader {

    private static final Logger logger = LoggerFactory.getLogger(XQueryReader.class);

    public String getXQueryFromFile(String filePath) {
        File xqueryFile = new File(filePath);
        StringBuilder xquery = new StringBuilder();

        try {
            FileReader xqueryFileReader = new FileReader(xqueryFile);
            BufferedReader xqueryFileBufferedReader = new BufferedReader(xqueryFileReader);

            String line;
            while ((line = xqueryFileBufferedReader.readLine()) != null) {
                xquery.append(line).append("\n");
            }

            xqueryFileBufferedReader.close();
            xqueryFileReader.close();
        } catch (IOException e) {
            String exceptionName = e.getClass().getSimpleName();
            logger.error("getXQueryFromFile | {}", exceptionName);
        }

        return xquery.toString();
    }

}
