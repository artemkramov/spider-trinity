/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spider;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author admin
 */
public class BotIasa extends Bot {

    public BotIasa() {
        this.url = "http://journal.iasa.kpi.ua";
    }

    public void run() {
//        Document doc = this.getPage("/user/setLocale/en_US?source=%2Farticle%2Fview%2F96793");
//        String s = doc.selectFirst("#articleTitle").child(0).text();
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
        int pageCounter = 1;
        while (true) {
            String endpoint = "/issue/archive?issuesPage=" + Integer.toString(pageCounter);
            Document doc = this.getPage(endpoint);
            if (documents.size() > 0) {
                Document lastDocument = documents.get(documents.size() - 1);
                if (!lastDocument.selectFirst("#content").toString().equals(doc.selectFirst("#content").toString())) {
                    documents.add(doc);
                } else {
                    break;
                }
            } else {
                documents.add(doc);
            }
            pageCounter++;
        }
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
                String linkEn = "/user/setLocale/en_US?source=%2Farticle%2Fview%2F" + articleID;
                links.add(linkEn);
            }
        }
        return links;
    }

}
