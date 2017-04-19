package com.saturnringstation.crawlerwithdb.service;

import com.saturnringstation.crawlerwithdb.ImageEntity;
import org.hibernate.HibernateError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Created by tom on 4/18/2017.
 */
@Service
@Profile({"dBService", "doBothService"})
public class DBService implements PersistenceService {

    @Autowired
    private ImageRepository imageRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void saveImage(String url, byte[] imageBytes) {
        String nameWithExtension = url.substring(url.lastIndexOf('/') + 1);

        ImageEntity newImageObj = new ImageEntity();
        newImageObj.setFileName(nameWithExtension);
        newImageObj.setUrl(url);
        newImageObj.setImage(imageBytes);

        try {
            ImageEntity returnImageObj = imageRepository.save(newImageObj);
            logger.info("Stored in DB, ID:" + returnImageObj.getFileName());
        } catch (HibernateError error) {
            logger.error("Failed to insert:" + newImageObj.getFileName(), error);
        }
    }
}
