import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Quiz implements Serializable {
    private String name;
    private int numCorrect;
    private ArrayList<Question> questions;

    public Quiz(String name) {
        this.questions = new ArrayList<>();
        this.name = name;
        this.numCorrect = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void addWord(Question question) {
        questions.add(question);
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
        System.out.println("You got " + numCorrect + " out of " + questions.size() + " right\n");
        numCorrect = 0;
    }

    private void listWords() {
        for (int i = 0; i < questions.size(); i++) {
            Question word = questions.get(i);
            System.out.println(i + 1 + ". " + word.getWord());
        }
    }

}
