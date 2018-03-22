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
public class BotBulletinEconom extends Bot {
    
    public BotBulletinEconom() {
        this.url = "http://bulletin-econom.univ.kiev.ua";
    }
    
    public void run() {
        List<String> years = this.getArchiveDocuments();
        int counter = 1;
        for (String year : years) {
            ArrayList<String> issues = this.getListOfIssues(year);
            for (String issueLink: issues) {
                ArrayList<String> articles = this.getListOfArticles(issueLink);
                for (String articleLink : articles) {
                    // Get HTML string of the article
                    String html = this.getArticle(articleLink);
                    this.writeArticleToFile(Integer.toString(counter) + ".txt", html);
                    counter++;
                }
            }
        }
    }

    public ArrayList<String> getArchiveDocuments() {
        List<Document> documents = new ArrayList<>();
        
        String endpoint = "/archive";
        Document doc = this.getPage(endpoint);
        documents.add(doc);
        
        ArrayList<String> issueLinks = new ArrayList<>();
        for (Document document : documents) {
            Elements links = document.select("ul.page-list").select("a");
            links.forEach((link) -> {
                issueLinks.add(link.attr("href").replace(this.url, ""));
            });
        }
        return issueLinks;
    }

    public ArrayList<String> getListOfIssues(String endpoint) {
        ArrayList<String> links = new ArrayList<>();
        Document document = this.getPage(endpoint);
        Elements inputs = document.select(".entry-content").select("input");
        for (int i = 0; i < inputs.size(); i++) {
            String onClickString = inputs.get(i).attr("onclick");
            if (onClickString.contains("javascript") && !onClickString.contains(".pdf")) {
                int start = onClickString.indexOf("http");
                String link = onClickString.substring(start, onClickString.length() - 1).replace(this.url, "");
                links.add(link);
            }
        }
        return links;
    }
    
    public ArrayList<String> getListOfArticles(String endpoint) {
        ArrayList<String> links = new ArrayList<>();
        Document document = this.getPage(endpoint);
        Elements inputs = document.select("h1.entry-title").select("a");
        for (int i = 0; i < inputs.size(); i++) {
            String link = inputs.get(i).attr("href").replace(this.url, "");
            links.add(link);
        }
        return links;
    }
    
}
