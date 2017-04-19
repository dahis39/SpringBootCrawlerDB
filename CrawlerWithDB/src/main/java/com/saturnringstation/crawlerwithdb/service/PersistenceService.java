package com.saturnringstation.crawlerwithdb.service;

/**
 * Created by tom on 4/18/2017.
 */
public interface PersistenceService {
    public void saveImage(String url, byte[] imageBytes);
}
