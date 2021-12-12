package ca.senecacollege.recipeapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavouriteListAdapter extends BaseAdapter {

    ArrayList<Recipe> listOfRecipes;
    Context list_activity_Context;

    FavouriteListAdapter(ArrayList<Recipe> list, Context activity_context){
        listOfRecipes = list;
        list_activity_Context = activity_context;
    }

    @Override
    public int getCount() {
        return listOfRecipes.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(list_activity_Context).inflate(R.layout.recipe_item,null);

        TextView recName = view.findViewById(R.id.singleRecipeName);

        recName.setText(listOfRecipes.get(i).getRecipeName());

        return view;
    }
}
