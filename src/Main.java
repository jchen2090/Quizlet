import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        //constructs the data set and gives it the name of the set//
        System.out.println("What's the name of your set?");
        String gabababboo = scan.nextLine();
        Data set = new Data(gabababboo);
        //creates the set's words and definitions
        boolean stop  = true;
        while(stop){
            System.out.println("What is a word you'd like to add?");
            set.addWord(scan.nextLine());
            System.out.println("What is its definition?");
            set.addDefinition(scan.nextLine());
            System.out.println("Is this enough words? (say 'yes' to stop. else, say anything)");
            if (scan.nextLine().equals("yes")){
                stop = false;
            }
        }
        //serves as a blocker so the quiz doesn't start instantly; starts when user is ready//
        stop = true;
        while(stop){
            System.out.println("Would you like to be quizzed now?(say 'yes' to be quizzed)");
            if (scan.nextLine().equals("yes")) {
                stop = false;
            }
        }
        //quizzes the user// // add possible variation through a boolean set on/off= randomization of the data set//
        for(int i = 0; i<set.word.size();i++){
            System.out.println(set.word.get(i));
            System.out.println("WHATS THE DEFINITION?");
            if(scan.nextLine().equals(set.definition.get(i))){
                System.out.println("GOOD JOB HERE'S ONE POINT");
                set.score++;

            } else {
                System.out.println("WRONG");
            }
        }
        System.out.println("Your score was " + set.score + " out of " + set.word.size() + ".");
        set.score = 0;
        //repeats quiz (do you want to be quizzed again?)//
        stop = true;
        while(stop){
            System.out.println("Would you like to be quizzed again?(say 'yes' to be quizzed again)");
            if(scan.nextLine().equals("yes")){
                for(int i = 0; i<set.word.size();i++){
                    System.out.println(set.word.get(i));
                    System.out.println("WHATS THE DEFINITION?");
                    if(scan.nextLine().equals(set.definition.get(i))){
                        System.out.println("GOOD JOB HERE'S ONE POINT");
                        set.score++;
                    } else {
                        System.out.println("WRONG");
                    }
                }
            } else{
                System.out.println("ok bye");
                stop = false;
            }
        }

    }
}
