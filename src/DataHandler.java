import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

//todo saving/loading is clunky, could maybe be simplified
public class DataHandler {

    private final static File file = new File("quizzes.txt");

    public static boolean dataFileExists() {
        return file.exists();
    }

    public static void saveData(final ArrayList<Quiz> quizzes) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectWriter = new ObjectOutputStream(fileOutputStream);

            objectWriter.writeObject(quizzes);

            fileOutputStream.close();
            objectWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found or does not exist\n");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error setting up output streams\n");
        }
    }

    public static ArrayList<Quiz> loadData() {

        ArrayList<Quiz> quizzes = new ArrayList<>();

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectReader = new ObjectInputStream(fileInputStream);

            quizzes = (ArrayList<Quiz>) objectReader.readObject();

            fileInputStream.close();
            objectReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found\n");
        } catch (IOException e) {
            System.out.println("Error with setting up input streams\n");
        } catch (ClassNotFoundException e) {
            System.out.println("Class does not exist\n");
        }
        return quizzes;
    }
}
