package ca.senecacollege.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import ca.senecacollege.recipeapp.Recipe;
import ca.senecacollege.recipeapp.RecipeDatabase;
import ca.senecacollege.recipeapp.DatabaseManager;


public class RecipeDetail extends AppCompatActivity implements View.OnClickListener {

    Recipe recipeObject;
    DatabaseManager dbManager;
    RecipeDatabase db;

    TextView recipe_name, recipe_calories, recipe_weight, recipe_meal, recipe_cuisine, recipe_dish, recipe_ingredient;
    ImageView recipe_img;
    Button favouriteBtn;

    public static final DecimalFormat df = new DecimalFormat("0.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        recipe_name = findViewById(R.id.recipeName);
        recipe_img = findViewById(R.id.recipeImage);
        recipe_calories = findViewById(R.id.recipeCalories);
        recipe_weight = findViewById(R.id.recipeWeight);
        recipe_meal = findViewById(R.id.recipeMeal);
        recipe_cuisine = findViewById(R.id.recipeCuisine);
        recipe_dish = findViewById(R.id.recipeDish);
        recipe_ingredient = findViewById(R.id.recipeIngredient);

        favouriteBtn = findViewById(R.id.favouriteButton);
        favouriteBtn.setOnClickListener(this);

        Intent intent = getIntent();
        Recipe recipeReceived = (Recipe) intent.getSerializableExtra("recipeObj");
        recipeObject = new Recipe();

        db = DatabaseManager.getDBInstance(this);
        dbManager = ((myApp)getApplication()).getDatabaseManager();

        recipe_name.setText(recipeReceived.getRecipeName());
        Picasso.get().load(recipeReceived.getImgUrl()).resize(400, 400).into(recipe_img);
        recipe_calories.setText("Calories: " + decimalFormat(recipeReceived.getCalories()));
        recipe_weight.setText("Total Weight: " + decimalFormat(recipeReceived.getTotalWeight()));
        recipe_meal.setText("Meal: " + recipeReceived.getMealType());
        recipe_cuisine.setText("Cuisine: " + recipeReceived.getCuisineType());
        recipe_dish.setText("Dish: " + recipeReceived.getDishType());
        recipe_ingredient.setText("Ingredients: " + recipeReceived.getIngredientStringList());


        recipeObject.setRecipeName(recipeReceived.getRecipeName());
        recipeObject.setCalories(recipeReceived.getCalories());
        recipeObject.setTotalWeight(recipeReceived.getTotalWeight());
        recipeObject.setMealType(recipeReceived.getMealType());
        recipeObject.setCuisineType(recipeReceived.getCuisineType());
        recipeObject.setDishType(recipeReceived.getDishType());
        recipeObject.setIngredientList(recipeReceived.getIngredientList());


    }

    public static String decimalFormat(String val){

        Double result = Double.parseDouble(val.toString());

        return df.format(result);

    }


    @Override
    public void onClick(View view) {

        String btn_value = ((Button)view).getText().toString(); // capture button text

        switch (btn_value) {
            case "Add to favourite":

                // Response User
                Toast.makeText(RecipeDetail.this, "Recipe added to Favourite List", Toast.LENGTH_LONG).show();

                dbManager.insertNewRecipe(recipeObject);

                recipeObject = new Recipe();

                break;

        }

    }
}