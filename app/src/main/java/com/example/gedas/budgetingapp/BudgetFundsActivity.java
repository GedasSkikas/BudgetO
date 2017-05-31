package com.example.gedas.budgetingapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Gedas on 30/03/2017.
 */

public class BudgetFundsActivity extends AppCompatActivity {


    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_funds);
        settingBudgetLimits();

        // Setting up the app logo
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.pound_logo);
    }

    /**
     * Function which allows users to set their budgeting limits
     * Using caching
     */
    public void settingBudgetLimits() {

        // Creating a preferences file, which will store the budgeting limits
        prefs = getSharedPreferences("MY_PREFS", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();

        Button alertsSetBtn = (Button) findViewById(R.id.alertsSetBtn);
        alertsSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText limitVal = (EditText) findViewById(R.id.alertLowValue);
                String textFromVal = limitVal.getText().toString();

                // Check to ensure the user has entered values
                if (textFromVal.matches("")) {

                    // Displaying an error message if the user hasn't a value
                    Toast.makeText(BudgetFundsActivity.this, "You need to enter a value first!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Changing the value  to an integer, so it's easier to convert later
                int setBudgetValue = new Integer(textFromVal).intValue();

                // Entering the user input value into the previously created SharedPreferences file
                editor.putInt("LIMIT", setBudgetValue);
                editor.commit();

                // Displaying a success message if the inputs were correctly set
                Toast toast = Toast.makeText(BudgetFundsActivity.this, "Limit has been successfully set!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
