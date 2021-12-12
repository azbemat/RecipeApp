package ca.senecacollege.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FavouriteList extends AppCompatActivity implements DatabaseManager.DatabaseListener{

    ArrayList<Recipe> listFromDB;
    ListView listOfRecipes;
    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);

        listOfRecipes = findViewById(R.id.list_of_recipes);
        dbManager = ((myApp) getApplication()).getDatabaseManager();
        dbManager.listener = this;
        //listFromMA = getIntent().getParcelableArrayListExtra("listOfDonations");
        dbManager.getAllRecipes();

        listOfRecipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                Log.d("Check", "onItemClick: " + i);

                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + i, Toast.LENGTH_LONG)
                        .show();

            }
        });

    }

    @Override
    public void databaseAllDonationListener(List<Recipe> list) {

        listFromDB = new ArrayList<>(list);
        FavouriteListAdapter adapter = new FavouriteListAdapter(listFromDB, this);
        listOfRecipes.setAdapter(adapter);

        Log.d("Check", "databaseAllDonationListener: " + listFromDB.size());

    }
}