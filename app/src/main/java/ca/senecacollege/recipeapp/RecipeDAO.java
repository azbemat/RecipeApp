package ca.senecacollege.recipeapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeDAO {

    @Insert
    void insertNewRecipe(Recipe recipe);

    @Query("SELECT * From Recipe")
    List<Recipe> getAll();


}
