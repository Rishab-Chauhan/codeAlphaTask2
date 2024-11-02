package com.example.flashcardquizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCards;
    private ArrayList<Flashcard> flashcards;
    private FlashcardAdapter adapter;
    private DatabaseHelper databaseHelper;
    private static final int EDIT_CARD_REQUEST_CODE = 2; // Or any unique integer value
    private int requestCode;
    private int resultCode;
    private Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewCards = findViewById(R.id.recycler_view_cards);
        flashcards = new ArrayList<>();

        databaseHelper = new DatabaseHelper(this);

        // Load existing cards from the database
        loadFlashcards();

        adapter = new FlashcardAdapter(flashcards, this);

        recyclerViewCards.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCards.setAdapter(adapter);

        Button buttonAddCard = findViewById(R.id.button_add_card);
        buttonAddCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CardActivity.class);
            startActivityForResult(intent, 1);
        });

        Button buttonQuizYourself = findViewById(R.id.button_quiz_yourself);
        buttonQuizYourself.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            intent.putExtra("flashcards", flashcards);
            startActivity(intent);
        });
    }



    private void loadFlashcards() {
        flashcards.clear();
        flashcards.addAll(databaseHelper.getAllFlashcards()); // Load cards from database
    }
    public void deleteFlashcard(int id, int position) {
        // Remove from database
        databaseHelper.deleteFlashcard(id);

        // Remove from list and notify adapter
        flashcards.remove(position);
        adapter.notifyItemRemoved(position);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Handling adding a new flashcard
            Flashcard card = (Flashcard) data.getSerializableExtra("flashcard");
            flashcards.add(card);
            databaseHelper.addFlashcard(card); // Save card to database
            adapter.notifyDataSetChanged();
        } else if (requestCode == EDIT_CARD_REQUEST_CODE && resultCode == RESULT_OK) {
            // Handling updating an existing flashcard
            Flashcard updatedCard = (Flashcard) data.getSerializableExtra("updated_flashcard");
            int positionToUpdate = data.getIntExtra("position", -1);

            if (positionToUpdate != -1) {
                flashcards.set(positionToUpdate, updatedCard); // Update the card in the list
                databaseHelper.updateFlashcard(updatedCard); // Update in the database as well
                adapter.notifyItemChanged(positionToUpdate); // Notify adapter of change
            }
        }
    }
}