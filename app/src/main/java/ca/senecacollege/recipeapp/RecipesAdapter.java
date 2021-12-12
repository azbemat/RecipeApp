package ca.senecacollege.recipeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.TasksViewHolder> {

    interface recipeClickListner {
        public void recipeClicked(Recipe selectedRecipe);
    }
    private Context context;
    private List<Recipe> recipeList;
    recipeClickListner listner;

    public RecipesAdapter(Context ctx, List<Recipe> recipeList) {
        this.context = ctx;
        this.recipeList = recipeList;
        listner = (recipeClickListner)ctx;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_recipes, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        Recipe rec = recipeList.get(position);
        holder.recipeNameView.setText(rec.getRecipeName());
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recipeNameView;
        public TasksViewHolder(View itemView) {
            super(itemView);
            recipeNameView = itemView.findViewById(R.id.recipeName);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            Recipe recipe = recipeList.get(getAdapterPosition());
            listner.recipeClicked(recipe);
        }
    }

}
