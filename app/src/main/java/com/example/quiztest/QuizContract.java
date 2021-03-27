package com.example.quiztest;

import android.provider.BaseColumns;

public final class QuizContract {
    private QuizContract() {}
    public static final class  QuetionsTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_quetions";
        public static final String COLUMN_QUETION = "quetion";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_ANSWER_NR = "answer_nr";
    }
}
