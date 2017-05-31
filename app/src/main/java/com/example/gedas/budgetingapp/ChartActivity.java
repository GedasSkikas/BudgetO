package com.example.gedas.budgetingapp;


import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by Gedas on 03/04/2017.
 */

public class ChartActivity extends AppCompatActivity {

    PieChart pieChart;
    ArrayList<PieEntry> entries;
    ArrayList<String> PieEntryLabels;
    PieDataSet pieDataSet;
    PieData pieData;
    float spentSum;
    int setBudget;

    // Custom colors for the pie chart
    int[] colors = {Color.rgb(62, 211, 55), Color.rgb(214, 53, 74)};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        // Setting up the app logo
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.pound_logo);


        //Setting up the pie chart and data for it
        pieChart = (PieChart) findViewById(R.id.pieChart);
        entries = new ArrayList<>();
        PieEntryLabels = new ArrayList<>();
        pieDataSet = new PieDataSet(entries, "");
        pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setHoleRadius(15);
        pieChart.setTransparentCircleRadius(20);
        Description des = pieChart.getDescription();
        des.setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.animateY(2000);

        AddValuesToPIEENTRY();
    }

    /**
     * Function to sum up all of the spending data
     * From the database
     */
    public float costCalculator() {
        String tempString;
        float allSpending = 0;
        DatabaseSetup mydb = new DatabaseSetup(ChartActivity.this);
        Cursor result = mydb.getAllData();
        while (result.moveToNext()) {
            tempString = result.getString(2);
            allSpending = allSpending + Float.parseFloat(tempString);
        }
        return allSpending;
    }

    /**
     * Function which takes information from the database
     * And the cached budget limits and compares the data
     * In a pie chart by drawing it
     */
    public void AddValuesToPIEENTRY() {
        spentSum = costCalculator();
        SharedPreferences prefs = getSharedPreferences("MY_PREFS", Activity.MODE_PRIVATE);
        setBudget = prefs.getInt("LIMIT", 0);

        // Compares the set cached budget limit value with a sum value
        // Calculated from the database and outputs differently depending if
        // It's over the budgeting limit or not
        if (setBudget >= spentSum) {
            pieData.setValueTextSize(20f);
            pieData.setValueTextColor(Color.WHITE);
            pieDataSet.setColors(ColorTemplate.createColors(colors));
            entries.add(new PieEntry(spentSum, 0));
            entries.add(new PieEntry(setBudget, 1));
        } else {
            pieData.setValueTextSize(0f);
            pieData.setValueTextColor(Color.WHITE);
            // If budget has been exceeded, turn the whole pie chart red
            pieDataSet.setColors(ColorTemplate.rgb("#ff0000"));
            entries.add(new PieEntry(spentSum, 0));
            entries.add(new PieEntry(setBudget, 1));
            float overSpent = spentSum - setBudget;
            Toast.makeText(ChartActivity.this, "You've exceeded your budget by: £" + overSpent, Toast.LENGTH_LONG).show();
        }
    }
}
