package com.saturnringstation.crawlerwithdb;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;

/**
 * Created by tom on 4/11/2017.
 */
public class MyCrawlerFactory implements CrawlController.WebCrawlerFactory {

    ImageRepository imageRepository;

    public MyCrawlerFactory(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public WebCrawler newInstance() throws Exception {
        return new MyCrawler(imageRepository);
    }
}
