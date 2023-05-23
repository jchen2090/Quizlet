import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Scanner inputHandler;
    private static ArrayList<Quiz> quizzes;

    private static void listQuizzes() {
        for (int i = 0; i < quizzes.size(); i++) {
            Quiz quiz = quizzes.get(i);
            System.out.println(i + 1 + ". " + quiz.getName());
        }

        System.out.print("Enter topic do you want to get quizzed on: ");
        String topic = inputHandler.nextLine();

        try {
            int topicIdx = Integer.parseInt(topic);
            Quiz quizToRun = quizzes.get(topicIdx - 1);
            quizToRun.run(inputHandler);
        } catch (NumberFormatException e) {
            System.out.println("Quiz number does not exist");
        }
    }

    private static void initializeVariables() {
        inputHandler = new Scanner(System.in);
        quizzes = new ArrayList<>();
    }

    private static void createQuiz() {
        System.out.println("What is the topic of your quiz?");
        String quizName = inputHandler.nextLine();
        Quiz quiz = new Quiz(quizName);

        String continueOrNot;

        do {
            System.out.print("Enter word you'd like to add: ");
            String word = inputHandler.nextLine();

            System.out.print("Enter the word's definition: ");
            String definition = inputHandler.nextLine();

            Question question = new Question(word, definition);
            quiz.addWord(question);

            System.out.println("If finished adding words type yes");
            continueOrNot = inputHandler.nextLine();
        } while (!continueOrNot.equalsIgnoreCase("yes"));
        quizzes.add(quiz);
    }

    public static void main(String[] args) {
        initializeVariables();
        createQuiz();

        while (true) {
            System.out.print(
                    "1. Start quiz now"
                    + "\n2. Create new quiz"
                    + "\n9. Quit"
                    + "\n>>> "
            );

            String choice = inputHandler.nextLine();
            switch (choice) {
                case "1":
                    listQuizzes();
                    break;
                case "2":
                    createQuiz();
                    break;
                case "9":
                    System.out.println("ok bye bye");
                    System.exit(0);
                default:
                    System.out.println("Idk how you got this");
                    break;
            }
        }


    }
}
