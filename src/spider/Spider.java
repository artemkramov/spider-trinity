/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author admin
 */
public class Spider {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BotIpri botIpri = new BotIpri();
        botIpri.run();
        
    }
    
}
