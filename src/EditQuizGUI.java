import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class EditQuizGUI extends JDialog {
    private Quiz quizToEdit;
    private JPanel panel;
    private JPanel quizInfoPanel;
    private JPanel editOptionsPanel;

    public EditQuizGUI(JFrame parentFrame, Quiz quizToEdit) {
        super(parentFrame, "quiz", true);

        this.quizToEdit = quizToEdit;
        this.panel = new JPanel(new GridLayout(2, 1));
        this.quizInfoPanel = new JPanel(new BorderLayout());
        this.editOptionsPanel = new JPanel(new GridLayout(3, 1));

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(parentFrame);

        setupPanel();
        add(panel);
    }

    private void setupPanel() {
        setupQuizInfo();
        setupEditOptions();
    }

    private void refreshQuizInfo() {
        setVisible(false);
        quizInfoPanel.removeAll();
        setupQuizInfo();
        setVisible(true);
    }

    private void refreshEditOptions() {
        setVisible(false);
        quizInfoPanel.removeAll();
        setupQuizInfo();
        setContentPane(panel);
        setVisible(true);
    }

    /*
     * JLabel accepts HTML and this was the only way to make a new line appear 
     * idfk why this is the case but see more here 
     * https://stackoverflow.com/questions/1090098/newline-in-jlabel
     */
    private void setupQuizInfo() {
        String quizInfo = String.format(
            "<html>Quiz name: %s"
            + "<br>Amount of questions: %s </html>", quizToEdit.getName(), quizToEdit.getQuestions().size()
        );
        quizInfoPanel.add(new JLabel(quizInfo));

        panel.add(quizInfoPanel, 0, 0);
    }

    private void setupEditOptions() {
        JButton editNameButton = new JButton("Edit quiz name");
        editNameButton.addActionListener(e -> {
            String newQuizName = JOptionPane.showInputDialog(null, "Enter new quiz name", "Edit quiz name", JOptionPane.PLAIN_MESSAGE);

            if (newQuizName != null && !newQuizName.isBlank()) {
                System.out.println("Changed quiz name to " + newQuizName);
                quizToEdit.setName(newQuizName);
                refreshQuizInfo();
            } else {
                System.out.println("Invalid quiz name");
            }
        });
        editOptionsPanel.add(editNameButton);

        JButton addWordButton = new JButton("Add word");
        addWordButton.addActionListener(e -> {
            String newWord = JOptionPane.showInputDialog(null, "Enter a word that you'd like add", "Add new word", JOptionPane.PLAIN_MESSAGE);

            // Ends early if word is null or empty
            if (newWord == null || newWord.isBlank()) {
                System.out.println("Error with user input");
                return;
            }
            String newDefiniton = JOptionPane.showInputDialog(null, "Enter the word's definition", "Add new word", JOptionPane.PLAIN_MESSAGE);

            if (newDefiniton == null || newDefiniton.isBlank()) {
                System.out.println("Error ith user input");
                return;
            }
            Question newQuestion = new Question(newWord, newDefiniton);
            quizToEdit.addWord(newQuestion);
            refreshQuizInfo();
        });
        editOptionsPanel.add(addWordButton);

        JButton deleteWordButton = new JButton("Delete word");
        deleteWordButton.addActionListener(e -> {
            setContentPane(displayAllWords());
            revalidate();
            repaint();
        });
        editOptionsPanel.add(deleteWordButton);

        panel.add(editOptionsPanel, 0, 1);
    }

    private JPanel displayAllWords() {
        JPanel wordListPanel = new JPanel(new GridLayout(0, 2));
        List<Question> questions = quizToEdit.getQuestions();

        for (Question question : questions) {
            JButton word = new JButton(question.getWord());
            word.addActionListener(e -> {
                int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + question.getWord(), "Delete word", 
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (confirmation == JOptionPane.YES_OPTION) {
                    questions.remove(question);
                    refreshEditOptions();
                }
            });
            wordListPanel.add(word);
        }
        return wordListPanel;
    }
}
