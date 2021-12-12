package ca.senecacollege.recipeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.recipeClickListner, NetworkingService.NetworkingListner {

    ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    RecyclerView recyclerView;
    RecipesAdapter adapter;
    ProgressBar pb;

    NetworkingService networkingManager;
    JsonService jsonService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        recyclerView = findViewById(R.id.recipeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecipesAdapter(this,recipes);
        recyclerView.setAdapter(adapter);
        setTitle("Search for new recipes");

        networkingManager = ((myApp)getApplication()).getNetworkingService();
        jsonService = ((myApp)getApplication()).getJsonService();
        networkingManager.listner = this;

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchViewMenuItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) searchViewMenuItem.getActionView();
        String searchFor = searchView.getQuery().toString();

        if (!searchFor.isEmpty()) {
            searchView.setIconified(false);
            searchView.setQuery(searchFor, false);
        }

        searchView.setQueryHint("Search for recipes");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("query", query);
                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

                Log.d("Check", "UPP");

                // Search for Recipe
                networkingManager.searchRecipes(query);

                Log.d("Check", "DOWN");



                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("query change", newText);

                    recipes.clear();
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();



                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.favouriteMenu: {

                Log.d("Check", "onOptionsItemSelected: Favourite");

                Intent myIntent = new Intent(this, FavouriteList.class);
                startActivity(myIntent);

                break;
            }
        }
        return true;
    }


    @Override
    public void recipeClicked(Recipe selectedRecipe) {

        Recipe singleRecipe = new Recipe(selectedRecipe.getRecipeName(), selectedRecipe.getImgUrl(), selectedRecipe.getCalories(), selectedRecipe.getTotalWeight(), selectedRecipe.getMealType(), selectedRecipe.getCuisineType(), selectedRecipe.getDishType(), selectedRecipe.getIngredientList());
        Intent intent = new Intent(this,RecipeDetail.class);

        Log.d("Check", "recipeClicked: " + selectedRecipe.getRecipeName());
        Log.d("Check", "recipeClicked: " + selectedRecipe.getImgUrl());

        intent.putExtra("recipeObj", singleRecipe);
        startActivity(intent);
    }

    @Override
    public void dataFromAPI(String jsonString) {

        findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);

        recipes = jsonService.getRecipesFromJSON(jsonString);
        adapter = new RecipesAdapter(this,recipes);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}