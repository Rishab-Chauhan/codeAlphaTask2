package com.example.flashcardquizapp;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "flashcards.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FLASHCARDS = "flashcards";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_ANSWER = "answer";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_FLASHCARDS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_QUESTION + " TEXT, " +
                COLUMN_ANSWER + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLASHCARDS);
        onCreate(db);
    }

    // Method to add a flashcard
    public void addFlashcard(Flashcard flashcard) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION, flashcard.getQuestion());
        values.put(COLUMN_ANSWER, flashcard.getAnswer());
        db.insert(TABLE_FLASHCARDS, null, values);
        db.close();
    }

    public ArrayList<Flashcard> getAllFlashcards() {
        ArrayList<Flashcard> flashcards = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FLASHCARDS,
                new String[]{COLUMN_ID, COLUMN_QUESTION, COLUMN_ANSWER},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                // Retrieve ID from cursor
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String question = cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION));
                @SuppressLint("Range") String answer = cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER));

                // Create Flashcard with ID
                Flashcard flashcard = new Flashcard(id, question, answer);
                flashcards.add(flashcard);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return flashcards;
    }
    public void updateFlashcard(Flashcard flashcard) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION, flashcard.getQuestion());
        values.put(COLUMN_ANSWER, flashcard.getAnswer());
        db.update(TABLE_FLASHCARDS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(flashcard.getId())});
        db.close();
    }


    public void deleteFlashcard(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FLASHCARDS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}