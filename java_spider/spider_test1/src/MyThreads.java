import java.io.IOException;

/**
 * @__Author__: Alan
 * @__date__: 2017/12/25
 * @__version__: 1.0
 */
public class MyThreads implements Runnable{
    private SpiderOnePage spider;
    private ArrayListToDB toDB;
    private int start;
    private int interval;
    private String url = "https://www.emsc-csem.org/Earthquake/?view=";
    public MyThreads(SpiderOnePage spider,ArrayListToDB toDB,int start,int interval){
        this.spider = spider;
        this.toDB = toDB;
        this.start = start;
        this.interval = interval;
    }
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