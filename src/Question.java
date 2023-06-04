import java.io.Serializable;
public class Question implements Serializable {
    private String word;
    private String definition;

    public Question(String word, String definition) {
        this.word = word;
        this.definition = definition;
    }

    public String getWord() {
        return word;
    }

    public String getDefinition() {
        return definition;
    }



}
