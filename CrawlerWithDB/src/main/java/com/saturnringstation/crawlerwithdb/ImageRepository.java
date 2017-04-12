package com.saturnringstation.crawlerwithdb;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by tom on 4/11/2017.
 */
public interface ImageRepository extends CrudRepository<ImageEntity, String> {
}
