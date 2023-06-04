import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class QuizAppGUI extends JFrame {
    //does the adding of quizzes
    private ArrayList<Quiz> quizzes;
    private JComboBox<Quiz> quizComboBox;

    public QuizAppGUI() {
        super("Quizzard");
        initializeVariables();

        // Create GUI components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel quizPanel = new JPanel();
        quizPanel.setLayout(new FlowLayout());

        JLabel quizLabel = new JLabel("Select a quiz:");
        quizPanel.add(quizLabel);

        quizComboBox = new JComboBox<>(quizzes.toArray(new Quiz[0]));
        quizComboBox.setRenderer(new QuizComboBoxRenderer());
        quizPanel.add(quizComboBox);

        JButton startQuizButton = new JButton("Start Quiz");
        startQuizButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startQuiz();
            }
        });
        quizPanel.add(startQuizButton);

        JButton createQuizButton = new JButton("Create New Quiz");
        createQuizButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createQuiz();
            }
        });
        quizPanel.add(createQuizButton);

        mainPanel.add(quizPanel, BorderLayout.CENTER);

        add(mainPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1500);
        setVisible(true);
    }

    private void initializeVariables() {
        quizzes = DataHandler.dataFileExists() ? DataHandler.loadData() : new ArrayList<>();
    }

    private void startQuiz() {
        Quiz selectedQuiz = (Quiz) quizComboBox.getSelectedItem();
        if (selectedQuiz != null) {
            QuizDialog quizDialog = new QuizDialog(this, selectedQuiz);
            quizDialog.setVisible(true);
        }
    }

    private void createQuiz() {
        String quizName = JOptionPane.showInputDialog(this, "Enter the topic of your quiz:", "Create New Quiz", JOptionPane.PLAIN_MESSAGE);
        if (quizName != null && !quizName.isEmpty()) {
            Quiz quiz = new Quiz(quizName);
            String continueOrNot;

            do {
                String word = JOptionPane.showInputDialog(this, "Enter the word you'd like to add:", "Create New Quiz", JOptionPane.PLAIN_MESSAGE);
                String definition = JOptionPane.showInputDialog(this, "Enter the word's definition:", "Create New Quiz", JOptionPane.PLAIN_MESSAGE);

                Question question = new Question(word, definition);
                quiz.addWord(question);

                continueOrNot = JOptionPane.showInputDialog(this, "If finished adding words, type 'yes'", "Create New Quiz", JOptionPane.PLAIN_MESSAGE);
            } while (continueOrNot != null && !continueOrNot.equalsIgnoreCase("yes"));

            quizzes.add(quiz);
            quizComboBox.addItem(quiz);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new QuizAppGUI();
            }
        });
    }

    // Custom ListCellRenderer for Quiz objects
    private class QuizComboBoxRenderer extends JLabel implements ListCellRenderer<Quiz> {
        public QuizComboBoxRenderer() {
            setOpaque(true);
        }

        public Component getListCellRendererComponent(JList<? extends Quiz> list, Quiz quiz, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            if (quiz != null) {
                setText(quiz.getName()); // Set the display text to quiz name
            } else {
                setText(""); // Set an empty text if quiz is null
            }
            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            return this;
        }
    }
}
