package crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SimpleCrawl {

    public static List<String> crawlLinks(String url) {
        List<String> links = new ArrayList<>();
        
        try {
            Document document = Jsoup.connect(url).get();
            document.getElementsByClass("references").remove();
            document.getElementsByClass("vector-header-container").remove();
            document.getElementsByClass("mw-footer-container").remove();
            document.getElementsByClass("external").remove();

            Elements linksElements = document.select("a[href]");

            for (Element link : linksElements) {
                String href = link.attr("abs:href");

                if(href.contains("wiki/") && 
                    href.contains("en.") &&
                    !href.contains("#") &&
                    !href.contains("wikiversity") &&
                    !href.contains("wikiquote") &&
                    !href.contains("wikivoyage") &&
                    (colonCount(href) < 2)
                ) links.add(href);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return links;
    }

    private static int colonCount(String link) {
        int count = 0;
        for (char c : link.toCharArray()) {
            if(c == ':') {
                count++;
            }
        }
        return count;
    }

}
