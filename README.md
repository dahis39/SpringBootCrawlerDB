# Java Crawler Setup

Keywords: Crawler4j, Jsoup, Spring Boot, Spring Data JPA, PostgreSQL, Multithreading, Single client, Image crawler.

A Spring Boot web crawler setup/example, including:  crawler4j for crawling, Jsoup for parsing, Spring Data JPA for ORM (Hibernate), PostgreSQL and direct files output as persistence.

It's a convenience setup that I configurated, so you can save some time. It will be crawling [pixabay.com](https://pixabay.com/) 800~p beautiful images as a working example.

I uploaded two versions: one(CrawlerLocalFiles) is only outputing jpg files, one(CrawlerWithDB) is doing both outputing files and saving image entities to a PostgreSQL DB.


# Quick Installation

I included a fat jar(local files output) at the top level folder. Download it, you will need Java 8 jre, run `java -jar CrawlerLocalFiles.jar` in command line. You gonna see links flying through. A new folder named `images` will appears with freshly crawled jpgs in it. It will keep working till you hit exit `ctrl + c`, or crawled all the links follows the domain: https://pixabay.com/en/ (I have no idea how long it gonna take). 


## Side Nodes of the target

pixabay.com offers 4 sizes/tiers of images:

1. thumbnail 
2. around 800p  <==== We're getting these, and only these.
3. around 1440p
4. 4k+

Tier 3 is behind a CAPTCHA; Tier 4 is behind authentication.


## Side Nodes of the crawler4j

Crawler4j doesn't crawl links in HTML attribute srcset, so I prase it with Jsoup and send the new links back to crawl pool. 


## Credits

[Tom H](http://www.saturnringstation.com/portfolio)

## Usage

Education only

## License

The MIT License
