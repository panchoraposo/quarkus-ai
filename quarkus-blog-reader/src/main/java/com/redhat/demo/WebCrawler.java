package com.redhat.demo;

import java.io.IOException;
import java.io.UncheckedIOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WebCrawler {

    String crawl(String url) {
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return doc.body().getElementsByClass("rh-generic--component").first().html();
    }

}