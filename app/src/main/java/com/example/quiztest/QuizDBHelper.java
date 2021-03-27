package com.example.quiztest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.example.quiztest.QuizContract.*;

public class QuizDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Quiz.db";
    private static final int VERSION = 1;
    private SQLiteDatabase db;
    public QuizDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUETIONS_TABLE = "CREATE TABLE " +
                QuetionsTable.TABLE_NAME + " ( " +
                QuetionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuetionsTable.COLUMN_QUETION + " TEXT, " +
                QuetionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuetionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuetionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuetionsTable.COLUMN_ANSWER_NR + " INTEGER" + ")";

        db.execSQL(SQL_CREATE_QUETIONS_TABLE);
        fillQuetionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuetionsTable.TABLE_NAME);
        onCreate(db);
    }
    private void fillQuetionsTable() {
        Quetion quetion1 = new Quetion("Capital of Telangana", "Varangal", "Hyderabad", "Khammam", 2);
        Quetion quetion2 = new Quetion("Capital of Andrapradesh", "Vishakapatanama", "Amaravati", "Guntur", 2);
        Quetion quetion3 = new Quetion("Capital of Arunachal Pradesh", "Itanagar", "Tawang", "Walong", 1);
        Quetion quetion4 = new Quetion("Capital of Assam", "Dibrugarh", "Guwahati", "Dispur", 3);
        Quetion quetion5 = new Quetion("Capital of Bihar", "Bhagalpur", "Patna", "Purnia", 2);
        addQuetion(quetion1);
        addQuetion(quetion2);
        addQuetion(quetion3);
        addQuetion(quetion4);
        addQuetion(quetion5);
    }

    private void addQuetion(Quetion quetion) {
        ContentValues cv = new ContentValues();
        cv.put(QuetionsTable.COLUMN_QUETION, quetion.getQuetion());
        cv.put(QuetionsTable.COLUMN_OPTION1, quetion.getOption1());
        cv.put(QuetionsTable.COLUMN_OPTION2, quetion.getOption2());
        cv.put(QuetionsTable.COLUMN_OPTION3, quetion.getOption3());
        cv.put(QuetionsTable.COLUMN_ANSWER_NR, quetion.getAnswerNr());
        db.insert(QuetionsTable.TABLE_NAME, null, cv);
    }

    public List<Quetion> getAllQuetions () {
        List<Quetion> quetionsList = new ArrayList<>();

        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuetionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Quetion quetion = new Quetion();
                quetion.setQuetion(c.getString(c.getColumnIndex(QuetionsTable.COLUMN_QUETION)));
                quetion.setOption1(c.getString(c.getColumnIndex(QuetionsTable.COLUMN_OPTION1)));
                quetion.setOption2(c.getString(c.getColumnIndex(QuetionsTable.COLUMN_OPTION2)));
                quetion.setOption3(c.getString(c.getColumnIndex(QuetionsTable.COLUMN_OPTION3)));
                quetion.setAnswerNr(c.getInt(c.getColumnIndex(QuetionsTable.COLUMN_ANSWER_NR)));
                quetionsList.add(quetion);
            } while (c.moveToNext());
        }
        c.close();
        return quetionsList;
    }
}
