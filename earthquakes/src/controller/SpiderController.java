package controller;

import spider.ArrayListToDB;
import spider.MyThreads;
import spider.SpiderOnePage;

import java.util.ArrayList;

/**
 * @Author:  Alan
 * @since : Java_8_151
 * @version: 1.0
 */
public class SpiderController {
    /**
     * use spider to fill the spider DB
     */
    public void fillSpDB(){
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
        System.out.println("spider completed");
    }
}
