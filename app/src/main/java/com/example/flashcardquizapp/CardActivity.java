package com.example.flashcardquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
public class CardActivity extends AppCompatActivity {

    private EditText editTextQuestion;
    private EditText editTextAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        editTextQuestion = findViewById(R.id.edit_text_question);
        editTextAnswer = findViewById(R.id.edit_text_answer);

        Button buttonSaveCard = findViewById(R.id.button_save_card);

        buttonSaveCard.setOnClickListener(v -> {
            String question = editTextQuestion.getText().toString();
            String answer = editTextAnswer.getText().toString();

            // Create a new Flashcard with an ID of -1 (or any default value)
            Flashcard card = new Flashcard(-1, question, answer); // Use -1 for new cards
            Intent intent = new Intent();
            intent.putExtra("flashcard", card);
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}