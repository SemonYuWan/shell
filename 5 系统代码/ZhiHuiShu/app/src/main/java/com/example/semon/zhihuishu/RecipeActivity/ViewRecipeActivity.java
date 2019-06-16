package com.example.semon.zhihuishu.RecipeActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.semon.zhihuishu.Constant.RecipeData;
import com.example.semon.zhihuishu.Database.RecipeDataManager;
import com.example.semon.zhihuishu.R;

import java.io.File;

public class ViewRecipeActivity extends AppCompatActivity {

    private RecipeDataManager mRecipeDataManager;
    private TextView recipeTitle;
    private TextView recipeContext;
    private ImageView recipeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        initData();
    }

    private void initData() {
        recipeTitle = findViewById(R.id.recipe_title_view);
        recipeContext = findViewById(R.id.recipe_context_view);
        recipeImage = findViewById(R.id.recipe_image_view);

        mRecipeDataManager = new RecipeDataManager(this);
        mRecipeDataManager.openDataBase();
        Intent itemRecipeIntent = getIntent();
        String itemRecipeId = itemRecipeIntent.getStringExtra("recipeId");
        RecipeData itemRecipe = new RecipeData();
        itemRecipe = mRecipeDataManager.findRecipeById(itemRecipeId);
        recipeTitle.setText(itemRecipe.getCreateTime().split(" ")[0]+"食谱");
        recipeContext.setText(itemRecipe.getRecipe());
        recipeImage.setImageURI(Uri.fromFile(new File(itemRecipe.getRecipe_image())));
    }
}
