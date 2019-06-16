package com.example.semon.zhihuishu.RecipeActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.example.semon.zhihuishu.Constant.HeadmasterData;
import com.example.semon.zhihuishu.Constant.RecipeData;
import com.example.semon.zhihuishu.Database.HeadmasterDataManager;
import com.example.semon.zhihuishu.Database.RecipeDataManager;
import com.example.semon.zhihuishu.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 添加食谱
 */

public class AddRecipeActivity extends AppCompatActivity {

    private SharedPreferences userSession;
    private String user_id,user_name,password,user_type;
    private MultiAutoCompleteTextView newRecipeContext;
    private Button addRecipeBtn;
    private RecipeDataManager mRecipeDataManager;
    private HeadmasterDataManager mHeadmasterDataManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        newRecipeContext = findViewById(R.id.add_recipe_context);
        addRecipeBtn = findViewById(R.id.add_recipe_btn);
        addRecipeBtn.setOnClickListener(addRecipeListener);

        if (mRecipeDataManager == null){
            mRecipeDataManager = new RecipeDataManager(this);
        }

        userSession = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        user_id = userSession.getString("USER_ID", null);//(key,若无数据需要赋的值)
        user_name = userSession.getString("USER_NAME", null);
        password = userSession.getString("PASSWORD", null);
        user_type = userSession.getString("USER_TYPE", null);

    }

    View.OnClickListener addRecipeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mHeadmasterDataManager = new HeadmasterDataManager(AddRecipeActivity.this);
            mHeadmasterDataManager.openDataBase();
            HeadmasterData mHeadmasterData = mHeadmasterDataManager.fetchHeadmasterDataById(user_id);
            mHeadmasterDataManager.closeDataBase();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            RecipeData mRecipeData = new RecipeData();
            mRecipeData.setId(UUID.randomUUID().toString());
            mRecipeData.setRecipe(newRecipeContext.getText().toString().trim());
            mRecipeData.setSchoolId(mHeadmasterData.getSchool_id());
            mRecipeData.setCreateTime(simpleDateFormat.format(date));
            mRecipeData.setRecipe_image("/data/data/com.example.semon.zhihuishu/image/recipe.jpg");
            mRecipeDataManager.openDataBase();
            if(mRecipeDataManager.addRecipe(mRecipeData) > 0){
                Log.i("addRecipe","发布食谱成功");
                Toast.makeText(AddRecipeActivity.this,"发布食谱成功！",Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    };
}
