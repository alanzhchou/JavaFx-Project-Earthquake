package spider;

import java.util.ArrayList;

/**
 * @Author:  Alan
 * @since : Java_8_151
 * @version: 1.0
 */
public class ArrayListToDB {
    private InputToDatabase input = new InputToDatabase();

    /**
     * insert into DB a earthquake list
     * @param arrayList  the earthquake list for inserting to DB
     */
    public void insertToDB(ArrayList<ArrayList> arrayList){
        ArrayList<String> values = new ArrayList<String>();
        StringBuffer value = new StringBuffer();
        arrayList.stream().forEach(q->{
            q.stream().forEach(p->{
                value.append(p + ",");
            });
            value.deleteCharAt(value.length()-1);
            values.add(value.toString());
            value.delete(0,value.length());
        });

        values.parallelStream().forEach(q->{
            input.insertValue(q);
        });
    }

    /**
     * close the DB connection
     */
    public void closeConnection(){
        input.closeConnection();
    }
}
