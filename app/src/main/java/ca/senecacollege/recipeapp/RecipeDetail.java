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

public class RecipeDetail extends AppCompatActivity implements View.OnClickListener {

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

        recipe_name.setText(recipeReceived.getRecipeName());
        Picasso.get().load(recipeReceived.getImgUrl()).resize(400, 400).into(recipe_img);
        recipe_calories.setText("Calories: " + decimalFormat(recipeReceived.getCalories()));
        recipe_weight.setText("Total Weight: " + decimalFormat(recipeReceived.getTotalWeight()));
        recipe_meal.setText("Meal: " + recipeReceived.getMealType());
        recipe_cuisine.setText("Cuisine: " + recipeReceived.getCuisineType());
        recipe_dish.setText("Dish: " + recipeReceived.getDishType());
        //recipe_ingredient.setText(recipeReceived.getIngredientList());
        recipe_ingredient.setText("Ingredients: " + recipeReceived.getIngredientStringList());

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
                Log.d("Check", "onClick executed ");
                Toast.makeText(RecipeDetail.this, "Recipe added to Favourite List", Toast.LENGTH_LONG).show();
                break;

        }

    }
}