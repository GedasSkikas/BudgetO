package com.example.gedas.budgetingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Gedas on 03/04/2017.
 */

public class AddExpensesActivity extends AppCompatActivity {
    public static DatabaseSetup myDb;
    EditText editComment, editPrice;
    String formattedDate;
    Button btnAddExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);

        // Setting up the app logo
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.pound_logo);

        myDb = new DatabaseSetup(this);

        Calendar c = Calendar.getInstance();

        // Formatting the date format, to be able to insert the correct date
        // When the user input an expense
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c.getTime());

        editPrice = (EditText) findViewById(R.id.editTextPrice);
        editComment = (EditText) findViewById(R.id.editTextComment);

        btnAddExpense = (Button) findViewById(R.id.btnSubmitExpense);
        addData();
    }

    /**
     * Function which adds data from user input into the database (expenses)
     */
    public void addData() {
        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String priceEnteredCheck = editPrice.getText().toString();
                if (!priceEnteredCheck.matches("")) {
                    boolean isInserted = myDb.insertData(
                            formattedDate,
                            editPrice.getText().toString(),
                            editComment.getText().toString());

                    // Check to ensure the user has entered a price value
                    if (isInserted == true) {
                        Toast.makeText(AddExpensesActivity.this, "Expense successfully added!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddExpensesActivity.this, "Something went wrong, try again", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(AddExpensesActivity.this, "You need to enter a price value first!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
