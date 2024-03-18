package com.swd391.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int answerID;
    private String key; //A, B, C
    private String text;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question_id;
    private boolean isCorrect;
}
