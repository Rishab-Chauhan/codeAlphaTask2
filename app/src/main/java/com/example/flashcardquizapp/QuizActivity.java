package com.example.flashcardquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
public class QuizActivity extends AppCompatActivity {

    private TextView textViewQuestion;
    private TextView textViewScore;
    private EditText editTextAnswerInput;

    private ArrayList<Flashcard> flashcards;
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textViewQuestion = findViewById(R.id.text_view_question);
        textViewScore = findViewById(R.id.text_view_score);
        editTextAnswerInput = findViewById(R.id.edit_text_answer_input);

        flashcards = (ArrayList<Flashcard>) getIntent().getSerializableExtra("flashcards");

        displayQuestion();

        Button buttonSubmitAnswer = findViewById(R.id.button_submit_answer);
        buttonSubmitAnswer.setOnClickListener(v -> checkAnswer());
    }

    private void displayQuestion() {
        if (currentQuestionIndex < flashcards.size()) {
            textViewQuestion.setText(flashcards.get(currentQuestionIndex).getQuestion());
            editTextAnswerInput.setText("");
            updateScoreDisplay();
        } else {
            // Finish quiz and show results (you can implement this part)
            Toast.makeText(this, "Quiz Finished! Your score is " + score + " out of " + flashcards.size(), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void checkAnswer() {
        String userAnswer = editTextAnswerInput.getText().toString();
        Flashcard currentCard = flashcards.get(currentQuestionIndex);

        if (userAnswer.equalsIgnoreCase(currentCard.getAnswer())) {
            score++; // Increment score for correct answer
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Wrong! Correct answer is " + currentCard.getAnswer(), Toast.LENGTH_SHORT).show();
        }

        currentQuestionIndex++;
        displayQuestion();
    }

    private void updateScoreDisplay() {
        textViewScore.setText("Score: " + score + " / " + flashcards.size());
    }
}