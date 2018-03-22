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
public class BotVisnykGeo extends Bot {
    
    public BotVisnykGeo() {
        this.url = "http://visnyk-geo.univ.kiev.ua";
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
        List<Document> documents = new ArrayList<>();
        String endpoint = "/?page_id=679&lang=en";
        Document doc = this.getPage(endpoint);
        documents.add(doc);
        ArrayList<String> issueLinks = new ArrayList<>();
        for (Document document : documents) {
            Elements links = document.select(".entry-summary").select("a");
            links.forEach((link) -> {
                issueLinks.add(link.attr("href").replace(this.url, ""));
            });
        }
        return issueLinks;
    }

    public ArrayList<String> getListOfArtciles(String endpoint) {
        ArrayList<String> links = new ArrayList<>();
        Document document = this.getPage(endpoint);
        Elements titles = document.select(".entry-summary").select("a");
        for (int i = 0; i < titles.size(); i++) {
            String link = titles.get(i).attr("href").replace(this.url, "");
            if (!link.contains(".pdf")) {
                links.add(link);
            }
            
        }
        return links;
    }
    
}
