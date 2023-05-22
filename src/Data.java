import java.util.*;
public class Data {

    public String setName;
    public int score;
    public ArrayList<String> definition = new ArrayList<>();
    public ArrayList<String> word = new ArrayList<>();

    public Data(String name){
        setName = name;
        score = 0;
    }

    public void addWord(String x){
        word.add(x);
    }

    public void addDefinition(String x){
        definition.add(x);
    }


}
