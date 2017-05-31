package com.example.gedas.budgetingapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.example.gedas.budgetingapp.DatabaseSetup.TABLE_NAME;


/**
 * Created by Gedas on 03/04/2017.
 */

public class HistoryActivity extends AppCompatActivity {

    Button btnViewHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Setting up the app logo
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.pound_logo);

        btnViewHistory = (Button) findViewById(R.id.btnDisplayHistory);
        viewAllHistory();
        resetHistory();
    }

    /**
     * Function which takes last 25 entries of data (budget entries) inserted into the
     * Database and uses the showMessage functions which then displays it
     */
    public void viewAllHistory() {
        btnViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseSetup mydb = new DatabaseSetup(HistoryActivity.this);
                Cursor result = mydb.getAllData();
                if (result.getCount() == 0) {
                    showMessage("Error", "No History Found!");
                    return;
                }
                StringBuilder buffer = new StringBuilder();
                int counter = 0;
                while (result.moveToNext()) {
                    buffer.append("Date: " + result.getString(1) + "\n");
                    buffer.append("Price: " + result.getString(2) + "\n");
                    buffer.append("Comment: " + result.getString(3) + "\n\n");
                    counter++;
                    if (counter >= 25)
                        break;
                }
                showMessage("Last 25 purchases made:", buffer.toString());
            }

        });
    }

    /**
     * Builds the message and shows it when called
     */
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    /**
     * Function which deletes the database table
     */
    public void resetHistory(){
        Button btnResetHistory = (Button) findViewById(R.id.btnResetHistory);
        btnResetHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseSetup myDb = new DatabaseSetup(HistoryActivity.this);
                SQLiteDatabase db = myDb.getWritableDatabase();
                db.execSQL("delete from "+ TABLE_NAME);

                // Check if it was deleted
                Cursor resetCheck = myDb.getAllData();
                if (resetCheck.getCount() == 0) {
                    Toast.makeText(HistoryActivity.this, "History has been successfully reset", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}

