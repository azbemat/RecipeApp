package ca.senecacollege.recipeapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {Recipe.class})
public abstract class RecipeDatabase extends RoomDatabase {

    abstract public RecipeDAO getRecipeDAO();

}
