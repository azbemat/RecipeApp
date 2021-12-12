package ca.senecacollege.recipeapp;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Arrays;

@Entity
public class Recipe implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String recipeName;
    private String imgUrl;
    private String calories;
    private String totalWeight;
    private String mealType;
    private String cuisineType;
    private String dishType;

    @Ignore
    private String[] ingredientList;

    public Recipe() {
        this.recipeName = "";
        this.imgUrl = "";
        this.calories = "";
        this.totalWeight = "";
        this.mealType = "";
        this.cuisineType = "";
        this.dishType = "";
        this.ingredientList = null;
    }

    public Recipe(String recipeName, String imgUrl, String calories, String totalWeight, String mealType, String cuisineType, String dishType, String[] ingredientList) {
        this.recipeName = recipeName;
        this.imgUrl = imgUrl;
        this.calories = calories;
        this.totalWeight = totalWeight;
        this.mealType = mealType;
        this.cuisineType = cuisineType;
        this.dishType = dishType;
        this.ingredientList = ingredientList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public String getDishType() {
        return dishType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }

    public String[] getIngredientList() {
        return ingredientList;
    }

    public String getIngredientStringList() {

        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < ingredientList.length; i++) {
            sb.append(ingredientList[i] + "\n");
        }
        String str = Arrays.toString(ingredientList);
        System.out.println(str);

        return str;
    }


    public void setIngredientList(String[] ingredientList) {
        this.ingredientList = ingredientList;
    }
}
