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
        int botSwitcher = 7; // 1 - ipri.kiev.ua, 2 - journal.iasa.kpi.ua
        switch (botSwitcher) {
            case 1:
                bot = new BotIpri();
                break;
            case 2:
                bot = new BotIasa();
                break;
            case 3:
                bot = new BotInfotelesc();
                break;
            case 4:
                bot = new BotBulletinEconom();
                break;
            case 5:
                bot = new BotVisnykGeo();
                break;
            case 6:
                bot = new BotAstroBulletin();
                break;
            case 7:
                bot = new BotVisnykSoc();
                break;
            default:
                bot = new BotIpri();
                break;
        }
        bot.run();
    }

}
