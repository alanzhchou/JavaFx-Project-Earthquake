package spider;

import java.io.IOException;
import java.util.ArrayList;

//继续插入1-100页
public class Main {

    public static void main(String[] args) throws IOException {
        SpiderOnePage spider = new SpiderOnePage();
        ArrayListToDB toDB = new ArrayListToDB();
        ArrayList<Thread> threads = new ArrayList<Thread>();
        for (int i=0; i<5; i++){
            threads.add(new Thread(new MyThreads(spider,toDB,i*5+0,5)));
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




