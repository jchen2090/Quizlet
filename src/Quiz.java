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
    public ArrayList<String> answerArray() {
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < quizSize(); i++) {
            temp.add(questions.get(i).getDefinition());
        }
        return temp;
    }

    public static void changeMode(int x){
        mode = x;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void addWord(Question question) {
        questions.add(question);
    }
    public int quizSize() {
        return questions.size();
    }

    public void editScreen(final Scanner inputHandler) {
        while (true) {
            System.out.printf(
                    "Quiz name: %s"
                    + "\n1. Edit name"
                    + "\n2. Add a word"
                    + "\n3. Delete a word"
                    + "\n9. Return"
                    + "\n>>> ", this.name
            );
            String userChoice = inputHandler.nextLine();

            switch (userChoice) {
                case "1":
                    System.out.print("Enter new name: ");
                    String newName = inputHandler.nextLine();
                    setName(newName);
                    break;
                case "2":
                    System.out.print("Enter word: ");
                    String word = inputHandler.nextLine();
                    System.out.print("Enter the definition: ");
                    String definition = inputHandler.nextLine();

                    addWord(new Question(word, definition));
                    break;
                case "3":
                    listWords();
                    String wordToDelete = inputHandler.nextLine();
                    System.out.println("Word to delete: ");

                    try {
                        int wordIdx = Integer.parseInt(wordToDelete);
                        questions.remove(wordIdx - 1);
                    } catch (NumberFormatException | IndexOutOfBoundsException e) {
                        System.out.println("Word number doesn't exist");
                    }
                    break;
                case "9":
                    return;
            }
        }


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

        if(mode==3) { //mcq for given word provide definition
            //create a clone of the arraylist to shuffle around as the mc answers
            ArrayList<Question> remix = new ArrayList<>(questions);
                for (Question question : questions) {
                    System.out.println(question.getWord());
                    Collections.shuffle(remix);

                    int x = 0; // takes note of which answer it is from 1-4
                    for (int i = 0; i < remix.size(); i++) {

                        System.out.println(i + 1 + ". " + remix.get(i).getDefinition());
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
        }
        System.out.println("You got " + numCorrect + " out of " + questions.size() + " right\n");
        numCorrect = 0;
    }

    private void listWords() {
        for (int i = 0; i < questions.size(); i++) {
            Question word = questions.get(i);
            System.out.println(i + 1 + ". " + word.getWord());
        }
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

}
