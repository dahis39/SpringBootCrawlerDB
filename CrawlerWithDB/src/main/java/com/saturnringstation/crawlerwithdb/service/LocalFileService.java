package com.saturnringstation.crawlerwithdb.service;

import com.saturnringstation.crawlerwithdb.MyCrawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by tom on 4/18/2017.
 */
@Service
@Profile({"localFileService", "default", "doBothService"})
public class LocalFileService implements PersistenceService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void saveImage(String url, byte[] imageBytes) {
        String nameWithExtension = url.substring(url.lastIndexOf('/') + 1);
        String filename = MyCrawler.storageFolder.getAbsolutePath() + "/" + nameWithExtension;
        try {
            Files.write(Paths.get(filename) , imageBytes);
            logger.info("Stored in file: {}", url);
        } catch (IOException iox) {
            logger.error("Failed to write file: " + filename, iox);
        }
    }
}
