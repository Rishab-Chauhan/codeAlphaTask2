package com.example.flashcardquizapp;
import java.io.Serializable;

public class Flashcard implements Serializable {
    private int id;  // ID of the flashcard
    private String question;
    private String answer;

    public Flashcard(int id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}