package com.saturnringstation.crawlerwithdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Created by tom on 4/18/2017.
 */
@Service
@Profile("doBothService")
public class DoBothService implements PersistenceService {

    @Autowired
    private DBService dbService;
    @Autowired
    private LocalFileService localFileService;

    @Override
    public void saveImage(String url, byte[] imageBytes) {
        localFileService.saveImage(url, imageBytes);
        dbService.saveImage(url, imageBytes);
    }
}
