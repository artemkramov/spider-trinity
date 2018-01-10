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
 * General class of the Bot
 * @author admin
 */
public class Bot {
    
    /**
     * URL of the site
     */
    protected String url;
    
    /**
     * Method to run bot
     */
    public void run() {
    }
    
    /**
     * Log some info
     * @param logString 
     */
    protected void log(String logString) {
        System.out.println(logString);
    }
    
    /**
     * Get HTML page by the given address
     * @param endpoint
     * @return 
     */
    protected Document getPage(String endpoint) {
        try {
            return Jsoup.connect(this.url + endpoint).get();
        } catch (IOException ex) {
            this.log(ex.getMessage());
            return null;
        }
    }
    
    /**
     * Get article by the given address
     * @param endpoint
     * @return 
     */
    protected String getArticle(String endpoint) {
        Document document = this.getPage(endpoint);
        Element bodyElement = document.select("body").first();
        // Clean HTML from the whitespaces
        String html = Jsoup.clean(bodyElement.toString(), Whitelist.relaxed()).replaceAll("\r", "").replaceAll("\n", "").replaceAll(">\\s+<", "><").replaceAll(">\\s+", ">").replaceAll("\\s+<", "<");
        return html;
    }
    
    /**
     * Write article to file
     * @param fileName
     * @param articleString 
     */
    protected void writeArticleToFile(String fileName, String articleString) {
        // Remove forbidden symbols from the folder path
        String siteName = this.url.replaceAll("[^a-zA-Z0-9\\.\\-_]", "");
        siteName = siteName.replaceFirst("http", "");
        siteName = siteName.replaceFirst("https", "");
        String folderPath = "./result/" + siteName;
        File dir = new File(folderPath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String filePath = folderPath + "/input/" + fileName;
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
