/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author admin
 */
public class BotIpri extends Bot {

    public BotIpri() {
        this.url = "http://ipri.kiev.ua";
    }

    public void run() {
        List<String> archiveYears = this.getArchiveList();
        int counter = 1;
        for (String linkYear : archiveYears) {
            List<String> articleLinks = this.getListOfArticles(linkYear);
            for (String articleLink : articleLinks) {
                String html = this.getArticle(articleLink);
                this.writeArticleToFile(Integer.toString(counter) + ".txt", html);
                counter++;
//                if (counter > 6) break;
            }
//            if (counter > 6) break;
        }
          
    }

    private List<String> getArchiveList() {
        List<String> links = new ArrayList<>();
        String endpoint = "/index.php?id=52";
        Document document = this.getPage(endpoint);
        Element list = document.select("#c289").next().first();
        list.children().forEach((item) -> {
            links.add(item.child(0).attr("href"));
        });
        return links;
    }
    
    private List<String> getListOfArticles(String endpoint) {
        List<String> listAtricles = new ArrayList<>();
        Document document = this.getPage(endpoint);
        Elements tables = document.select("#content").first().select("table");
        for (Element table : tables) {
            for (int i = 1; i < table.child(0).children().size() - 1; i++) {
                Element tableRow = table.child(0).children().get(i);
                Element link = tableRow.child(1).child(0);
                listAtricles.add(link.attr("href"));
            }
        }
        int a = 43;
        return listAtricles;
    }

    

}
