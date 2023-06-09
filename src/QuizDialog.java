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

    private JRadioButton option1Button, option2Button, option3Button, option4Button; //choices for mcq
    private ButtonGroup optionButtonGroup; //group buttons together

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

        JPanel quizMode = new JPanel();
        quizMode.setLayout(new GridLayout(0, 1));

        JPanel quizQuestion = new JPanel();
        quizQuestion.setLayout(new GridLayout(0, 1));

        questionArea = new JTextArea();
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setEditable(false);

        JButton mcq = new JButton("Multiple Choice Mode");
        mcq.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (quiz.quizSize() > 3) {
                    quizMode.setVisible(false);

                    JScrollPane questionScrollPane = new JScrollPane(questionArea);
                    quizMode.add(questionScrollPane);

                    option1Button = new JRadioButton();
                    option2Button = new JRadioButton();
                    option3Button = new JRadioButton();
                    option4Button = new JRadioButton();

                    optionButtonGroup = new ButtonGroup();
                    optionButtonGroup.add(option1Button);
                    optionButtonGroup.add(option2Button);
                    optionButtonGroup.add(option3Button);
                    optionButtonGroup.add(option4Button);
                }
                else {
                    JOptionPane no = new JOptionPane("The following list has less than 4 terms.");
                    JOptionPane.showMessageDialog(null, "The following list has less than 4 terms.","error", JOptionPane.PLAIN_MESSAGE);

                }

            }
        });

        JButton term = new JButton("Given term, provide definition");
        term.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quizMode.setVisible(false);

                JScrollPane questionScrollPane = new JScrollPane(questionArea);
                quizQuestion.add(questionScrollPane);

                answerField = new JTextField();
                answerField.setHorizontalAlignment(JTextField.CENTER);
                answerField.setFont(answerField.getFont().deriveFont(Font.ITALIC));
                answerField.setText("Enter your answer here");

                mainPanel.add(quizQuestion);

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

                quizQuestion.add(answerField);
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
                quizQuestion.add(nextButton);
            }
        });

        JButton def = new JButton("Given definition, provide term");
        def.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quizMode.setVisible(false);

                JScrollPane questionScrollPane = new JScrollPane(questionArea);
                quizQuestion.add(questionScrollPane);

                answerField = new JTextField();
                answerField.setHorizontalAlignment(JTextField.CENTER);
                answerField.setFont(answerField.getFont().deriveFont(Font.ITALIC));
                answerField.setText("Enter your answer here");

                mainPanel.add(quizQuestion);


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

                quizQuestion.add(answerField);
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
                quizQuestion.add(nextButton);
            }
        });
        if (selectMode) {
            quizMode.add(mcq);
            quizMode.add(term);
            quizMode.add(def);
        }
        else {
            quizMode.remove(mcq);
            quizMode.remove(term);
            quizMode.remove(def);
        }
        mainPanel.add(quizMode, BorderLayout.CENTER);
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
        String correctAnswer = currentQuestion.getWord();

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
