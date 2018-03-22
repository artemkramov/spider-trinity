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
public class BotVisnykSoc extends Bot {
    
    public BotVisnykSoc() {
        this.url = "http://visnyk.soc.univ.kiev.ua";
    }

    @Override
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
        String endpoint = "/index.php/soc/issue/archive";
        Document doc = this.getPage(endpoint);
        documents.add(doc);
        
        ArrayList<String> issueLinks = new ArrayList<>();
        for (Document document : documents) {
            Elements links = document.select("h4").select("a");
            links.forEach((link) -> {
                issueLinks.add(link.attr("href").replace(this.url, "") + "/showToc");
            });
        }
        return issueLinks;
    }

    public ArrayList<String> getListOfArtciles(String endpoint) {
        ArrayList<String> links = new ArrayList<>();
        Document document = this.getPage(endpoint);
        Elements titles = document.select(".tocTitle");
        for (int i = 0; i < titles.size(); i++) {
            Elements linkTag = titles.get(i).select("a");
            if (linkTag.size() > 0) {
                String link = linkTag.first().attr("href");
                String articleID = link.substring(link.lastIndexOf("/") + 1);
                String linkEn = "/index.php/soc/user/setLocale/en_US?source=%2Findex.php%2Fsoc%2Farticle%2Fview%2F" + articleID;
                links.add(linkEn);
            }
        }
        return links;
    }
    
}
