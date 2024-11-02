package com.example.flashcardquizapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FlashcardAdapter extends RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder> {

    private ArrayList<Flashcard> flashcards;
    private Context context;

    public FlashcardAdapter(ArrayList<Flashcard> flashcards, Context context) {
        this.flashcards = flashcards;
        this.context = context;
    }

    @NonNull
    @Override
    public FlashcardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_flashcard, parent, false);
        return new FlashcardViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull FlashcardViewHolder holder, int position) {
        Flashcard flashcard = flashcards.get(position);
        holder.textViewQuestion.setText(flashcard.getQuestion());
        holder.textViewAnswer.setText(flashcard.getAnswer());

        // Set onClickListener for editing
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditCardActivity.class);
            intent.putExtra("flashcard", flashcard); // Pass the flashcard object
            context.startActivity(intent);
        });

        // Set onClickListener for deleting (you can add a delete button in your item layout)
        holder.deleteButton.setOnClickListener(v -> {
            ((MainActivity) context).deleteFlashcard(flashcard.getId(), position);
        });
    }

    @Override
    public int getItemCount() {
        return flashcards.size();
    }

    static class FlashcardViewHolder extends RecyclerView.ViewHolder {
        TextView textViewQuestion;
        TextView textViewAnswer;
        Button deleteButton; // Add delete button in your item layout

        public FlashcardViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewQuestion = itemView.findViewById(R.id.text_view_question);
            textViewAnswer = itemView.findViewById(R.id.text_view_answer);
            deleteButton = itemView.findViewById(R.id.button_delete); // Ensure you have a delete button in your item layout
        }
    }

}