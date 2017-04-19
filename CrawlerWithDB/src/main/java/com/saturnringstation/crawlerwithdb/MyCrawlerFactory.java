package com.saturnringstation.crawlerwithdb;

import com.saturnringstation.crawlerwithdb.service.PersistenceService;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;

/**
 * Created by tom on 4/11/2017.
 */
public class MyCrawlerFactory implements CrawlController.WebCrawlerFactory {

    PersistenceService persistenceService;

    public MyCrawlerFactory(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    @Override
    public WebCrawler newInstance() throws Exception {
        return new MyCrawler(persistenceService);
    }
}
