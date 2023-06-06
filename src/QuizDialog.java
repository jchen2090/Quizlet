import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class QuizDialog extends JDialog {

    int num = 0;//takes number of total questions for score at the end
    int score = 0;//score
    private Quiz quiz;//quiz
    private int currentQuestionIndex;//current question

    private JTextArea questionArea;//display question
    private JTextField answerField;//displays place for type
    private boolean isAnswerFieldEmpty;//does wierd/cool animation
    private boolean selectMode = true;

    //creates GUI
    public QuizDialog(JFrame parentFrame, Quiz quiz) {
        super(parentFrame, "Quiz", true);
        this.quiz = quiz;
        this.currentQuestionIndex = 0;
        this.isAnswerFieldEmpty = true;

        initializeComponents();

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(parentFrame);
    }


    private void initializeComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel quizPanel = new JPanel();
        quizPanel.setLayout(new GridLayout(0, 1));

        questionArea = new JTextArea();
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setEditable(false);

        JButton mcq = new JButton("Multiple Choice Mode");
        mcq.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JScrollPane questionScrollPane = new JScrollPane(questionArea);
                quizPanel.add(questionScrollPane);
            }
        });

        JButton term = new JButton("Given term, provide definition");
        term.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JScrollPane questionScrollPane = new JScrollPane(questionArea);
                quizPanel.add(questionScrollPane);

                answerField = new JTextField();
                answerField.setHorizontalAlignment(JTextField.CENTER);
                answerField.setFont(answerField.getFont().deriveFont(Font.ITALIC));
                answerField.setText("Enter your answer here");


                answerField.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (isAnswerFieldEmpty) {
                            answerField.setText("");
                            answerField.setFont(answerField.getFont().deriveFont(Font.PLAIN));
                            isAnswerFieldEmpty = false;
                        }
                    }

                    //cool animation
                    @Override
                    public void focusLost(FocusEvent e) {
                        if (answerField.getText().isEmpty()) {
                            answerField.setText("Enter your answer here");
                            answerField.setFont(answerField.getFont().deriveFont(Font.ITALIC));
                            isAnswerFieldEmpty = true;
                        }
                    }
                });

                quizPanel.add(answerField);
                //moves on to next question
                JButton nextButton = new JButton("Next");
                nextButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        processAnswerForTerm(answerField.getText());
                        showNextQuestion();
                        answerField.setText(""); // Clear the answer field
                        answerField.requestFocusInWindow(); // Set focus back to the answer field
                    }
                });
                quizPanel.add(nextButton);
            }
        });

        JButton def = new JButton("Given definition, provide term");
        def.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answerField = new JTextField();
                answerField.setHorizontalAlignment(JTextField.CENTER);
                answerField.setFont(answerField.getFont().deriveFont(Font.ITALIC));
                answerField.setText("Enter your answer here");


                answerField.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (isAnswerFieldEmpty) {
                            answerField.setText("");
                            answerField.setFont(answerField.getFont().deriveFont(Font.PLAIN));
                            isAnswerFieldEmpty = false;
                        }
                    }

                    //cool animation
                    @Override
                    public void focusLost(FocusEvent e) {
                        if (answerField.getText().isEmpty()) {
                            answerField.setText("Enter your answer here");
                            answerField.setFont(answerField.getFont().deriveFont(Font.ITALIC));
                            isAnswerFieldEmpty = true;
                        }
                    }
                });

                quizPanel.add(answerField);
                //moves on to next question
                JButton nextButton = new JButton("Next");
                nextButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        processAnswerForDef(answerField.getText());
                        showNextQuestion();
                        answerField.setText(""); // Clear the answer field
                        answerField.requestFocusInWindow(); // Set focus back to the answer field
                    }
                });
                quizPanel.add(nextButton);
            }
        });
        if (selectMode) {
            quizPanel.add(mcq);
            quizPanel.add(term);
            quizPanel.add(def);
        }
        else {
            quizPanel.remove(mcq);
            quizPanel.remove(term);
            quizPanel.remove(def);
        }
        mainPanel.add(quizPanel, BorderLayout.CENTER);
        add(mainPanel);

        showNextQuestion();
    }

    private void showNextQuestion() {
        if (currentQuestionIndex < quiz.getQuestions().size()) {
            Question question = quiz.getQuestions().get(currentQuestionIndex);
            questionArea.setText(question.getWord());
            currentQuestionIndex++;
        } else {
            showQuizSummary();
        }
    }

    //checks for right or wrong answer
    private void processAnswerForTerm(String answer) {
        selectMode = false;
        Question currentQuestion = quiz.getQuestions().get(currentQuestionIndex - 1);
        String correctAnswer = currentQuestion.getDefinition();

        if (answer.equalsIgnoreCase(correctAnswer)) {
            JOptionPane.showMessageDialog(this, "Correct!", "Result", JOptionPane.INFORMATION_MESSAGE);
            score++;
            num++;
        } else {
            JOptionPane.showMessageDialog(this, "Wrong!\nThe answer was: " + currentQuestion.getDefinition(), "Result", JOptionPane.INFORMATION_MESSAGE);
            num++;
        }
    }

    private void processAnswerForDef(String answer) {
        selectMode = false;
        Question currentQuestion = quiz.getQuestions().get(currentQuestionIndex - 1);
        String correctAnswer = currentQuestion.getDefinition();

        if (answer.equalsIgnoreCase(correctAnswer)) {
            JOptionPane.showMessageDialog(this, "Correct!", "Result", JOptionPane.INFORMATION_MESSAGE);
            score++;
            num++;
        } else {
            JOptionPane.showMessageDialog(this, "Wrong!\nThe answer was: " + currentQuestion.getDefinition(), "Result", JOptionPane.INFORMATION_MESSAGE);
            num++;
        }
    }

    //shows the conclusion so like how many u got right
    private void showQuizSummary() {
        JOptionPane.showMessageDialog(this, "Quiz completed!\nYou got: "+score+"/"+num, "Result", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }


}
