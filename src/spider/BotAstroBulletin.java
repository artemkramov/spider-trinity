/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spider;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author admin
 */
public class BotAstroBulletin extends Bot {
    
    public BotAstroBulletin() {
        this.url = "http://www.astrobulletin.univ.kiev.ua";
    }

    public void run() {
        List<String> issueLinks = this.getArchiveDocuments();
        int counter = 1;
        for (String issueLink : issueLinks) {
            ArrayList<String> artciles = this.getListOfArtciles(issueLink);
            for (String articleLink : artciles) {
                // Get HTML string of the article
                String html = this.getArticle(articleLink);
                this.writeArticleToFile(Integer.toString(counter) + ".txt", html);
                counter++;
//                if (counter > 10) break;
            }
//            if (counter > 10) break;
        }
    }

    public ArrayList<String> getArchiveDocuments() {
        ArrayList<String> issueLinks = new ArrayList<>();
        issueLinks.add("/bulletin-of-taras-shevchenko-national-university-of-kyiv-astronomy-№511-2014");
        issueLinks.add("/52-2");
        issueLinks.add("/bulletin-of-taras-shevchenko-national-university-of-kyiv-astronomy-№531");
        issueLinks.add("/bulletin-of-taras-shevchenko-national-university-of-kyiv-astronomy-№542");
        issueLinks.add("/bulletin-of-taras-shevchenko-national-university-of-kyiv-astronomy-№551");
        return issueLinks;
    }

    public ArrayList<String> getListOfArtciles(String endpoint) {
        ArrayList<String> links = new ArrayList<>();
        Document document = this.getPage(endpoint);
        Elements titles = document.select(".entry-content").select("a");
        for (int i = 0; i < titles.size(); i++) {
            String link = titles.get(i).attr("href").replace(this.url, "");
            links.add(link);
        }
        return links;
    }
    
}
