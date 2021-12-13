package ca.senecacollege.recipeapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseManager {

    static RecipeDatabase db;
    ExecutorService databaseExecuter = Executors.newFixedThreadPool(4);
    Handler db_handler = new Handler(Looper.getMainLooper());

    public interface DatabaseListener {
        void databaseAllDonationListener(List<Recipe> list);
    }

    public DatabaseListener listener;

    private static void BuildDBInstance (Context context) {
        db = Room.databaseBuilder(context,RecipeDatabase.class,"recipe_db").build();
    }
    public static RecipeDatabase getDBInstance(Context context){
        if (db == null){
            BuildDBInstance(context);
        }
        return db;
    }

    public void insertNewRecipe(Recipe d){
        databaseExecuter.execute(new Runnable() {
            @Override
            public void run() {
                db.getRecipeDAO().insertNewRecipe(d);
            }
        });
    }

    public void getAllRecipes(){
        databaseExecuter.execute(new Runnable() {
            @Override
            public void run() {
                List<Recipe> list =  db.getRecipeDAO().getAll();
                db_handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.databaseAllDonationListener(list);
                    }
                });

            }
        });

    }


}
