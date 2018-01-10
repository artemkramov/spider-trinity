/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spider;

/**
 *
 * @author admin
 */
public class Spider {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Bot bot;
        int botSwitcher = 2; // 1 - ipri.kiev.ua, 2 - journal.iasa.kpi.ua
        switch (botSwitcher) {
            case 1:
                bot = new BotIpri();
                break;
            case 2:
                bot = new BotIasa();
                break;
            default:
                bot = new BotIpri();
                break;
        }
        bot.run();
    }

}
