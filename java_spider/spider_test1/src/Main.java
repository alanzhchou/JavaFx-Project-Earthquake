import dao.ArrayListToDB;
import spider.SpiderOnePage;
import java.io.IOException;
import java.util.ArrayList;

//继续插入1-100页
public class Main {
    public static void main(String[] args) throws IOException {
        SpiderOnePage spider = new SpiderOnePage();
        ArrayListToDB toDB = new ArrayListToDB();
        ArrayList<Thread> threads = new ArrayList<Thread>();
        for (int i=0; i<5; i++){
            threads.add(new Thread(new MyThreads(spider,toDB,i*20+0)));
        }
        for (Thread thread:threads){
            thread.start();
        }
        for (Thread thread:threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        toDB.closeConnection();
    }
}

class MyThreads implements Runnable{
    private SpiderOnePage spider;
    private ArrayListToDB toDB;
    private int start;
    private String url = "https://www.emsc-csem.org/Earthquake/?view=";
    public MyThreads(SpiderOnePage spider,ArrayListToDB toDB,int start){
        this.spider = spider;
        this.toDB = toDB;
        this.start = start;
    }
    @Override
    public void run() {
        try {
            for (int i=start; i<start+21; i++){
                System.out.println(url + i + "---Start!");
                toDB.insertToDB(spider.getOnePage(url+i));
                System.out.println(url + i + "---End!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}