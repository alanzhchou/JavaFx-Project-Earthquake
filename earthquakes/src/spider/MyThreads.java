package spider;

import java.io.IOException;

/**
 * @Author: Alan
 * @since : Java_8_151
 * @version: 1.0
 */
public class MyThreads implements Runnable{
    private SpiderOnePage spider;
    private ArrayListToDB toDB;
    private int start;
    private int interval;
    private String url = "https://www.emsc-csem.org/Earthquake/?view=";

    /**
     * @param spider  one page spider
     * @param toDB  ArrayListToDB, for insert into DB using given pagespider
     * @param start page spider will first spider this page
     * @param interval how many will spider after startpage
     */
    public MyThreads(SpiderOnePage spider, ArrayListToDB toDB, int start, int interval){
        this.spider = spider;
        this.toDB = toDB;
        this.start = start;
        this.interval = interval;
    }

    /**
     * run for thread
     */
    @Override
    public void run() {
        try {
            for (int i=start; i<start+interval+1; i++){
                System.out.println(url + i + "---Start!");
                toDB.insertToDB(spider.getOnePage(url+i));
                System.out.println(url + i + "---End!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}