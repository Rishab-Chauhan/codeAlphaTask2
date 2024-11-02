package com.example.flashcardquizapp;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EditCardActivity extends AppCompatActivity {

    private EditText editTextQuestion;
    private EditText editTextAnswer;
    private Button buttonSave;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card);

        editTextQuestion = findViewById(R.id.edit_text_question);
        editTextAnswer = findViewById(R.id.edit_text_answer);
        buttonSave = findViewById(R.id.button_save);

        databaseHelper = new DatabaseHelper(this);

        // Get the passed flashcard object
        Flashcard currentFlashcard = (Flashcard) getIntent().getSerializableExtra("flashcard");

        if (currentFlashcard != null) {
            editTextQuestion.setText(currentFlashcard.getQuestion());
            editTextAnswer.setText(currentFlashcard.getAnswer());
        }

        buttonSave.setOnClickListener(v -> {
            String updatedQuestion = editTextQuestion.getText().toString();
            String updatedAnswer = editTextAnswer.getText().toString();

            if (!updatedQuestion.isEmpty() && !updatedAnswer.isEmpty()) {
                Flashcard updatedFlashcard = new Flashcard(currentFlashcard.getId(), updatedQuestion, updatedAnswer);
                databaseHelper.updateFlashcard(updatedFlashcard); // Update card in database

                Intent resultIntent = new Intent();
                resultIntent.putExtra("updated_flashcard", updatedFlashcard);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}