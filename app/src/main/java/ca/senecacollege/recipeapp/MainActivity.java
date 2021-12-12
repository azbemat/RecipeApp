package ca.senecacollege.recipeapp;

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
//                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

                Log.d("Check", "UPP");

                // Search for Recipe
                networkingManager.searchRecipes(query);

                Log.d("Check", "DOWN");



                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("query change", newText);

                if(newText.length() == 0){
                    recipes = new ArrayList<>(0);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }

                return false;
            }
        });
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

//        findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);


        //String testString = "{q:chicken,from:0,to:10,more:true,count:7000,hits:[{recipe:{uri:http://www.edamam.com/ontologies/edamam.owl#recipe_b79327d05b8e5b838ad6cfd9576b30b6,label:Chicken Vesuvio,image:https://www.edamam.com/web-img/e42/e42f9119813e890af34c259785ae1cfb.jpg,source:Serious Eats,url:http://www.seriouseats.com/recipes/2011/12/chicken-vesuvio-recipe.html,shareAs:http://www.edamam.com/recipe/chicken-vesuvio-b79327d05b8e5b838ad6cfd9576b30b6/chicken,yield:4.0,ingredientLines:[1/2 cup olive oil,5 cloves garlic, peeled,2 large russet potatoes, peeled and cut into chunks,1 3-4 pound chicken, cut into 8 pieces (or 3 pound chicken legs),3/4 cup white wine,3/4 cup chicken stock,3 tablespoons chopped parsley,1 tablespoon dried oregano,Salt and pepper,1 cup frozen peas, thawed],calories:4228.043058200812,totalWeight:2976.8664549004047,cuisineType:[american],mealType:[lunch/dinner],dishType:[main course]}}]}";
        recipes = jsonService.getRecipesFromJSON(jsonString);
        adapter = new RecipesAdapter(this,recipes);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}