package ca.senecacollege.recipeapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonService {

    public ArrayList<Recipe> getRecipesFromJSON(String json){

        ArrayList<Recipe> arrayList = new ArrayList<>(0);

        // JSON
        // OBJ -> Array -> OBJ -> OBJ -> String

        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONArray hits = jsonObject.getJSONArray("hits");

            for (int i = 0; i < hits.length(); i++){

                JSONObject recipesObjects = hits.getJSONObject(i);

                JSONObject recipeObject = recipesObjects.getJSONObject("recipe");

                String recipeLabel = recipeObject.getString("label");
                String recipeImgUrl = recipeObject.getString("image");
                String recipeCalories = recipeObject.getString("calories");
                String recipeTotalWeight = recipeObject.getString("totalWeight");

                JSONArray cuisineTypeArray = recipeObject.getJSONArray("cuisineType");
                String recipeCuisineType = cuisineTypeArray.getString(0);

                JSONArray mealTypeArray = recipeObject.getJSONArray("mealType");
                String recipeMealType = mealTypeArray.getString(0);

                JSONArray dishTypeArray = recipeObject.getJSONArray("dishType");
                String recipeDishType = dishTypeArray.getString(0);

                JSONArray ingredientArray = recipeObject.getJSONArray("ingredientLines");


                JSONArray temp = recipeObject.getJSONArray("ingredientLines");
                int length = temp.length();
                String [] ingredientList = new String [length];

                if (length > 0) {
                    for (int j = 0; j < length; j++) {
                        ingredientList[j] = temp.getString(j);
                    }
                }
                Log.d("Check", "Recipe Name: " + recipeLabel);
                Log.d("Check", "Recipe Name: " + recipeImgUrl);

                for (int k = 0; k < ingredientList.length; k++) {
                    Log.d("check", "IngreMemebr: " + ingredientList[k]);
                }


                Recipe recipe = new Recipe(recipeLabel, recipeImgUrl, recipeCalories, recipeTotalWeight, recipeMealType, recipeCuisineType, recipeDishType, ingredientList);
                arrayList.add(recipe);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arrayList;

    }

}
