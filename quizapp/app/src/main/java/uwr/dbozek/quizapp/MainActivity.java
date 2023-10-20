package uwr.dbozek.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private List<Question> allQuestions = Arrays.asList(
            new Question("What is the SI unit of force?", Arrays.asList("A) Newton (N)", "B) Joule (J)", "C) Watt (W)", "D) Pascal (Pa)"), 0),
            new Question("What is the formula for acceleration?", Arrays.asList("A) v = u + at", "B) F = ma", "C) E = mc^2", "D) F = G(m1*m2/r^2)"), 0),
            new Question("Which of the following is a vector quantity?", Arrays.asList("A) Speed", "B) Distance", "C) Velocity", "D) Mass"), 2),
            new Question("What is the conservation of energy?", Arrays.asList("A) Energy cannot be created nor destroyed, only transformed", "B) Energy can be created from nothing", "C) Energy can be destroyed", "D) None of the above"), 0),
            new Question("What is the speed of light in a vacuum?", Arrays.asList("A) 299,792,458 meters per second", "B) 300,000 meters per second", "C) 3,000,000 meters per second", "D) 30,000 meters per second"), 0),
            new Question("Which sorting algorithm has the best average and worst-case time complexity?", Arrays.asList("A) Quick Sort", "B) Bubble Sort", "C) Merge Sort", "D) Insertion Sort"), 0),
            new Question("What is the time complexity of binary search?", Arrays.asList("A) O(n)", "B) O(log n)", "C) O(n^2)", "D) O(1)"), 1),
            new Question("What is the purpose of dynamic programming?", Arrays.asList("A) To efficiently solve problems with overlapping subproblems", "B) To efficiently solve problems with non-overlapping subproblems", "C) To solve problems with constant time complexity", "D) None of the above"), 0),
            new Question("What is the Big O notation for linear search?", Arrays.asList("A) O(n)", "B) O(log n)", "C) O(n^2)", "D) O(1)"), 0),
            new Question("Which data structure uses LIFO (Last In, First Out) principle?", Arrays.asList("A) Stack", "B) Queue", "C) Linked List", "D) Tree"), 0),
            new Question("What is the formula for momentum?", Arrays.asList("A) p = m * v", "B) F = ma", "C) E = mc^2", "D) F = G(m1*m2/r^2)"), 0),
            new Question("Which law of thermodynamics states that energy cannot be created nor destroyed, only transformed?", Arrays.asList("A) First Law", "B) Second Law", "C) Third Law", "D) Zeroth Law"), 0),
            new Question("What is the unit of electric charge?", Arrays.asList("A) Coulomb (C)", "B) Ampere (A)", "C) Volt (V)", "D) Ohm (Ω)"), 0),
            new Question("Which algorithm is used for finding the shortest path in a weighted graph?", Arrays.asList("A) Dijkstra's Algorithm", "B) Depth-First Search (DFS)", "C) Breadth-First Search (BFS)", "D) Prim's Algorithm"), 0),
            new Question("What is the SI unit of power?", Arrays.asList("A) Watt (W)", "B) Joule (J)", "C) Newton (N)", "D) Volt (V)"), 0),
            new Question("What is the formula for work done?", Arrays.asList("A) W = Fd cos(θ)", "B) W = mgh", "C) W = F/a", "D) W = QV"), 0),
            new Question("Which law of motion states that an object at rest tends to stay at rest, and an object in motion tends to stay in motion?", Arrays.asList("A) Newton's First Law", "B) Newton's Second Law", "C) Newton's Third Law", "D) Einstein's Theory of Relativity"), 0),
            new Question("What is the formula for gravitational potential energy?", Arrays.asList("A) U = mgh", "B) U = 1/2mv^2", "C) U = -G(m1*m2/r)", "D) U = kx^2"), 0),
            new Question("What is the value of gravitational acceleration on Earth?", Arrays.asList("A) 9.81 m/s²", "B) 3.00 x 10^8 m/s", "C) 6.67 x 10^-11 Nm^2/kg^2", "D) 1.00 atm"), 0),
            new Question("Which algorithm is used for finding the minimum spanning tree in a weighted graph?", Arrays.asList("A) Prim's Algorithm", "B) Dijkstra's Algorithm", "C) Bellman-Ford Algorithm", "D) Kruskal's Algorithm"), 0),
            new Question("What is the unit of resistance?", Arrays.asList("A) Ohm (Ω)", "B) Ampere (A)", "C) Volt (V)", "D) Watt (W)"), 0),
            new Question("Which algorithm is used for sorting a linked list?", Arrays.asList("A) Merge Sort", "B) Bubble Sort", "C) Quick Sort", "D) Selection Sort"), 0),
            new Question("What is the formula for centripetal force?", Arrays.asList("A) F = mv²/r", "B) F = ma", "C) F = G(m1*m2/r²)", "D) F = kx"), 0)

    );

    private TextView questionNumber, questionText, scoreText, finalScoreText;
    private ProgressBar progressBar;
    private RadioGroup answerRadioGroup;
    private Button nextButton, restartButton;
    private ConstraintLayout quizLayout;
    private LinearLayout congratsLayout;

    private CardView answerCardView;

    private List<Question> selectedQuestions;
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicjalizacja widoków
        questionNumber = findViewById(R.id.questionNumber);
        progressBar = findViewById(R.id.progressBar);
        questionText = findViewById(R.id.questionText);
        answerRadioGroup = findViewById(R.id.answerRadioGroup);
        nextButton = findViewById(R.id.nextButton);
        answerCardView = findViewById(R.id.answerCardView);
//        scoreText = findViewById(R.id.scoreText);

        congratsLayout = findViewById(R.id.congratsLayout);
        quizLayout = findViewById(R.id.quizLayout);

        finalScoreText = findViewById(R.id.finalScoreText);
        restartButton = findViewById(R.id.restartButton);

        // Losowanie 10 pytań z puli
        selectedQuestions = new ArrayList<>(allQuestions);
        Collections.shuffle(selectedQuestions);
        selectedQuestions = selectedQuestions.subList(0, 10);

        showQuestion(selectedQuestions.get(currentQuestionIndex));

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartQuiz();
            }
        });

        answerRadioGroup.setOnCheckedChangeListener(this);
    }

    private void showQuestion(Question question) {
        questionNumber.setText("Question " + (currentQuestionIndex + 1) + "/10");
        progressBar.setProgress(currentQuestionIndex + 1);
        questionText.setText(question.getQuestionText());

        answerRadioGroup.removeAllViews();
        for (String answer : question.getAnswers()) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(answer);
            answerRadioGroup.addView(radioButton);
        }

        // Blokowanie przycisku "Next" na początku każdego pytania
        nextButton.setEnabled(false);
    }

    private void checkAnswer() {
        int selectedAnswerId = answerRadioGroup.getCheckedRadioButtonId();

        if (selectedAnswerId == -1) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
            return; // Return early if no answer is selected
        }

        int selectedAnswerIndex = answerRadioGroup.indexOfChild(
                findViewById(selectedAnswerId));

        if (selectedAnswerIndex == selectedQuestions.get(currentQuestionIndex).getCorrectAnswerIndex()) {
            score += 10;
        }

        currentQuestionIndex++;

        if (currentQuestionIndex < selectedQuestions.size()) {
            showQuestion(selectedQuestions.get(currentQuestionIndex));
            // Blokowanie przycisku "Next" na początku każdego pytania
            nextButton.setEnabled(false);
        } else {
            showFinalScore();
        }
    }

    private void showFinalScore() {
//        quizLayout.setVisibility(View.GONE);

        questionNumber.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        questionText.setVisibility(View.GONE);
        answerRadioGroup.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);
        answerCardView.setVisibility(View.GONE);

        congratsLayout.setVisibility(View.VISIBLE);

        finalScoreText.setText("Your Score: " + score);
    }

    private void restartQuiz() {
        currentQuestionIndex = 0;
        score = 0;
        progressBar.setProgress(0);
//        quizLayout.setVisibility(View.VISIBLE);

        questionNumber.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        questionText.setVisibility(View.VISIBLE);
        answerRadioGroup.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.VISIBLE);
        answerCardView.setVisibility(View.VISIBLE);

        congratsLayout.setVisibility(View.GONE);
        selectedQuestions = new ArrayList<>(allQuestions);
        Collections.shuffle(selectedQuestions);
        selectedQuestions = selectedQuestions.subList(0, 10);
        showQuestion(selectedQuestions.get(currentQuestionIndex));
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        nextButton.setEnabled(true);
    }
}