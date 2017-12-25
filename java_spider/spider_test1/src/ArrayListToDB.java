import java.util.ArrayList;

/**
 * __Author__: Alan
 * __date__: 2017/12/18
 * __version__: 1.0
 */
public class ArrayListToDB {
    private InputToDatabase input = new InputToDatabase();

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

    public void closeConnection(){
        input.closeConnection();
    }
}
