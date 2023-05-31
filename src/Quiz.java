import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Quiz implements Serializable {
    private String name;
    private int numCorrect;
    private ArrayList<Question> questions;

    private static int mode = 1;

    public Quiz(String name) {
        this.questions = new ArrayList<>();
        this.name = name;
        this.numCorrect = 0;
    }

    public String getName() {
        return name;
    }

    public static void changeMode(int x){
        mode = x;
    }
    public void addWord(Question question) {
        questions.add(question);
    }

    public void run(final Scanner inputHandler) {
        Collections.shuffle(questions);
        if(mode==1) {
            for (Question question : questions) {
                System.out.println(question.getWord());
                System.out.print("Definition: ");

                String userDefinition = inputHandler.nextLine().trim();
                String wordDefinition = question.getDefinition().trim();

                if (userDefinition.equalsIgnoreCase(wordDefinition)) {
                    System.out.println("Correct!");
                    numCorrect++;
                } else {
                    System.out.println("Wrong! The correct answer is: " + question.getDefinition().trim());
                }
            }
        }
        if(mode==2){
            for(Question question : questions) {
                System.out.println(question.getDefinition());
                System.out.print("Word: ");

                String userWord = inputHandler.nextLine().trim();
                String definitionWord = question.getWord().trim();

                if(userWord.equalsIgnoreCase(definitionWord)) {
                    System.out.println("Correct!");
                    numCorrect++;
                } else{
                    System.out.println("Wrong! The correct answer is: " + question.getWord().trim());
                }
            }
        }
        /*
        BROKEN
        if(mode==3) { //mcq for given word provide definition
            ArrayList<Question> remix = new ArrayList<>(); //create a clone of the arraylist to shuffle around as the mc answers
            if (questions.size() < 4) {
                for (Question question : questions) {
                    System.out.println(question.getWord());
                    Collections.shuffle(remix);
                    int x = 0; // takes note of which answer it is from 1-4
                    for (int i = 0; i < remix.size(); i++) {
                        System.out.println(i + 1 + ". " + remix.get(i).getWord());
                        if (remix.get(i).getWord().equals(question.getWord())) {
                            x = i+1;
                        }
                    }
                    String userDefinition = inputHandler.nextLine().trim();
                    if (userDefinition.equals("" + x)) {
                        System.out.println("Correct!");
                        numCorrect++;
                    } else {
                        System.out.println("Wrong! The correct answer is: " + x);
                    }
                }
            } else { // select only 4 of them out of the arraylist including the answer
                for(Question question : questions) {
                    System.out.println(question.getWord());
                    ArrayList<Question> answers = new ArrayList<>();
                    while(answers.size()<3){
                        int x = (int) (Math.random()*questions.size());
                        if(questions.get(x)!=question){ //unsure if it works; how to check equivalence of objects when not string nor int?
                            answers.add(questions.get(x));
                        }
                    }
                    answers.add(question);
                    Collections.shuffle(answers);
                    int x = 0;
                    for(int i = 0; i<answers.size();i++){
                        System.out.println(i + 1 + ". " + answers.get(i).getWord());
                        if(answers.get(i).getWord().equals(question.getWord())){
                            x = i+1;
                        }
                        String userDefinition = inputHandler.nextLine().trim();
                        if (userDefinition.equals("" + x)) {
                            System.out.println("Correct!");
                            numCorrect++;
                        } else {
                            System.out.println("Wrong! The correct answer is: " + x);
                        }
                    }
                }

            }
        }*/
        System.out.println("You got " + numCorrect + " out of " + questions.size() + " right\n");
        numCorrect = 0;
    }

}
