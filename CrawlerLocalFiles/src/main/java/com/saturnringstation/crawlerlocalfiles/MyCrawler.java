package com.saturnringstation.crawlerlocalfiles;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.BinaryParseData;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by tom on 4/9/2017.
 */

public class MyCrawler extends WebCrawler {

    private static final Pattern FILTERS = Pattern.compile(
            ".*(\\.(css|js|mid|mp2|mp3|mp4|json|wav|avi|flv|mov|mpeg|ram|m4v|pdf" +
                    "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
    private static final Pattern imgPatterns = Pattern.compile(".*\\.jpg$");
    private static final Pattern srcsetPatterns = Pattern.compile("(https):\\/\\/cdn.pixabay.com\\/photo(.*?)\\.jpg");

    private static File storageFolder;
    private static String[] crawlDomains;

    public static void configure(String[] domain, String storageFolderName) {
        crawlDomains = domain;

        storageFolder = new File(storageFolderName);
        if (!storageFolder.exists()) {
            storageFolder.mkdirs();
        }
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        if (FILTERS.matcher(href).matches()) {
            return false;
        }
        for (String domain : crawlDomains) {
            if (href.startsWith(domain)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void visit(Page page) {

        String url = page.getWebURL().getURL();
        System.out.println(url);

        // If it's a regular html page.
        // Extracts new links form attribute: srcset(not supported by crawler4j), then sends them back to links pool.
        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            Document doc = Jsoup.parseBodyFragment(htmlParseData.getHtml());

            Element imgTab = doc.select("#media_container img").first();
            if (imgTab == null) { return; }
            String srcset = imgTab.attr("srcset");

            // Get last image link in srcset.
            LinkedList<String> srcsetLinks = new  LinkedList<>();
            Matcher matcher = srcsetPatterns.matcher(srcset);
            while (matcher.find()) {
                srcsetLinks.add(matcher.group());
            }
            if (!srcsetLinks.isEmpty()){
                this.myController.addSeed(srcsetLinks.peekLast());
            }

        // If it's the targeted images, store them.
        } else if ( imgPatterns.matcher(url).matches() && page.getParseData() instanceof BinaryParseData) {
            if (page.getContentData().length > (100 * 1024) ) {
                storeImageToLocalFile(url, page.getContentData());
            }
        }
    }

    private void storeImageToLocalFile(String url, byte[] imageBytes) {
        String nameWithExtension = url.substring(url.lastIndexOf('/') + 1);
        String filename = storageFolder.getAbsolutePath() + "/" + nameWithExtension;
        try {
            Files.write(Paths.get(filename) , imageBytes);
            logger.info("Stored in file: {}", url);
        } catch (IOException iox) {
            logger.error("Failed to write file: " + filename, iox);
        }
    }
}
