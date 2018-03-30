package tests.TestService;

import org.junit.Test;
import service.MyServer;

import java.io.IOException;

/**
 * @author : Alan
 * @version : 1.0
 * @serial : 2018/3/28
 * @since : Java_8
 */
public class testServer {
    @Test
    public void testServerCreate() throws IOException {
        try
        {
            MyServer server = new MyServer(4567);
            Thread t = server;
            t.run();
        }catch(IOException e)
        {
            e.printStackTrace();
        }

    }
}
