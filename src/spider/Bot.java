/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;

/**
 *
 * @author admin
 */
public class Bot {

    protected String url;

    public void run() {

    }

    protected void log(String logString) {
        System.out.println(logString);
    }

    protected Document getPage(String endpoint) {
        try {
            return Jsoup.connect(this.url + endpoint).get();
        } catch (IOException ex) {
            this.log(ex.getMessage());
            return null;
        }
    }

    protected String getArticle(String endpoint) {
        Document document = this.getPage(endpoint);
        Element bodyElement = document.select("body").first();
        String html = Jsoup.clean(bodyElement.toString(), Whitelist.relaxed()).replaceAll("\r", "").replaceAll("\n", "").replaceAll(">\\s+<", "><").replaceAll(">\\s+", ">").replaceAll("\\s+<", "<");
        return html;
    }

    protected void writeArticleToFile(String fileName, String articleString) {
        String siteName = this.url.replaceAll("[^a-zA-Z0-9\\.\\-_]", "");
        siteName = siteName.replaceFirst("http", "");
        siteName = siteName.replaceFirst("https", "");
        String folderPath = "./result/" + siteName;
        File dir = new File(folderPath);
        if (dir.exists()) {
            dir.delete();
        }
        dir.mkdir();
        String filePath = folderPath + "/" + fileName;
        try {
            FileOutputStream fileStream = new FileOutputStream(new File(filePath));
            OutputStreamWriter writer = new OutputStreamWriter(fileStream, StandardCharsets.UTF_8);
            writer.write("\uFEFF");
            writer.write(articleString);
            writer.flush();
        } catch (IOException ex) {
            this.log(ex.getMessage());
        }
    }
}
