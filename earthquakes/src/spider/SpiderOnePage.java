package spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * @Author: Alan
 * @since : Java_8_151
 * @version: 1.0
 */
public class SpiderOnePage {
    private User_agents agents = new User_agents();

    /**
     * spider onepage from the target website
     * @param url  the target website url
     * @return ArrayList of earthquakes
     * @throws IOException
     */
    public ArrayList<ArrayList> getOnePage(String url) throws IOException {
        ArrayList<ArrayList> onePage = new ArrayList<ArrayList>();

        Document doc = Jsoup.connect(url).userAgent(agents.randomAgent()).get();
        Elements tables = doc.select("tr[class~=ligne(1|2)]");
        for (Element row: tables) {
            Elements cols = row.select("td[class~=(tabev[1-6])|(tb_region)]");
            ArrayList oneRow = new ArrayList();
            try {
                for (int i=0; i<cols.size(); i++){
                    Element a = cols.get(i);
                    if (i == 0){
                        oneRow.add("\"" + a.selectFirst("a").text() + "\"");
                    }else if (i == 8){
                        oneRow.add("\"" + a.text() + "\"");
                    }else if (i == 1||i==3){
                        if (Pattern.matches("S|W",cols.get(i+1).text())){
                            oneRow.add("-" + a.text());
                        }else {
                            oneRow.add(a.text());
                        }i++;
                    }else if (i != 6){
                        oneRow.add(a.text());
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            onePage.add(oneRow);
        }
        return onePage;
    }
}
