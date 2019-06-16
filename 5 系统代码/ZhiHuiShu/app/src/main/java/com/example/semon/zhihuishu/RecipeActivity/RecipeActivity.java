package com.example.semon.zhihuishu.RecipeActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.semon.zhihuishu.Constant.HeadmasterData;
import com.example.semon.zhihuishu.Constant.RecipeData;
import com.example.semon.zhihuishu.Database.HeadmasterDataManager;
import com.example.semon.zhihuishu.Database.ParentDataManager;
import com.example.semon.zhihuishu.Database.RecipeDataManager;
import com.example.semon.zhihuishu.R;

import java.io.File;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    private Context context;
    private SharedPreferences userSession;
    private String user_id,user_name,password,user_type;
    private FloatingActionButton addRecipeBtn;
    private ListView recipeListView;
    private List<RecipeData> recipeDataList;
    private RecipeDataManager mRecipeDataManager;
    private HeadmasterDataManager mHeadmasterDataManager;
    private ParentDataManager mParentDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        context = RecipeActivity.this;

        userSession = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        user_id = userSession.getString("USER_ID", null);//(key,若无数据需要赋的值)
        user_name = userSession.getString("USER_NAME", null);
        password = userSession.getString("PASSWORD", null);
        user_type = userSession.getString("USER_TYPE", null);

        initData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    //初始化数据
    private void initData() {
        String schoolId = null;
        if (user_type.equals("headmaster")){
            mHeadmasterDataManager = new HeadmasterDataManager(this);
            mHeadmasterDataManager.openDataBase();
            HeadmasterData mHeadmasterData = mHeadmasterDataManager.fetchHeadmasterDataById(user_id);
            mHeadmasterDataManager.closeDataBase();
            schoolId = mHeadmasterData.getSchool_id();
        }else if (user_type.equals("parent")){
            mParentDataManager = new ParentDataManager(this);
            mParentDataManager.openDataBase();
            schoolId = mParentDataManager.findChildSchoolIdByParentId(user_id);
            mParentDataManager.closeDataBase();
        }

        mRecipeDataManager = new RecipeDataManager(this);
        mRecipeDataManager.openDataBase();
        recipeDataList = mRecipeDataManager.findAllRecipeBySchoolid(schoolId);
        recipeListView = findViewById(R.id.recipe_listview);
        MyBaseAdapter mAdapter = new MyBaseAdapter(recipeDataList);
        recipeListView.setAdapter(mAdapter);

        recipeListView.setOnItemClickListener(itemOnClick);

        addRecipeBtn = findViewById(R.id.add_recipe_view_button);
        addRecipeBtn.bringToFront();
        addRecipeBtn.setOnClickListener(addRecipeOnClick);

        if(user_type.equals("parent") || user_type.equals("teacher")){
            addRecipeBtn.setVisibility(View.GONE);
        }
        else{
            recipeListView.setOnItemLongClickListener(itemOnLongClick);

        }

    }

    //点击事件
    View.OnClickListener addRecipeOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.add_recipe_view_button:
                    Log.i("TAG","添加食谱");
                    Intent addIntent = new Intent(RecipeActivity.this,AddRecipeActivity.class);
                    startActivity(addIntent);
                    break;
            }
        }
    };

    AdapterView.OnItemLongClickListener itemOnLongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long l) {
            //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
            AlertDialog.Builder builder = new AlertDialog.Builder(RecipeActivity.this);
            //    设置Title的图标
            builder.setIcon(R.drawable.warn);
            //    设置Title的内容
            builder.setTitle("删除");
            //    设置Content来显示一个信息
            builder.setMessage("确定删除吗？");
            //    设置一个PositiveButton
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    RecipeData mRecipeData = (RecipeData) adapterView.getItemAtPosition(position);
                    mRecipeDataManager = new RecipeDataManager(RecipeActivity.this);
                    mRecipeDataManager.openDataBase();
                    mRecipeDataManager.deleteRecipeDataById(mRecipeData.getId());
                    mRecipeDataManager.closeDataBase();
                    onRestart();
                }
            });
            //    设置一个NegativeButton
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    onRestart();
                }
            });
            builder.show();

            return true;
        }
    };

    private class MyBaseAdapter extends BaseAdapter {
        private List<RecipeData> mRecipeDataList;
        public MyBaseAdapter(List<RecipeData> mRecipeDataList) {
            this.mRecipeDataList=mRecipeDataList;
        }

        @Override
        public int getCount() {
            return mRecipeDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mRecipeDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            View view = View.inflate(RecipeActivity.this,R.layout.item_recipe,null);
            TextView recipeTitle = view.findViewById(R.id.recipe_title_textview);
            ImageView recipeImage = view.findViewById(R.id.recipe_image);
            TextView recipeCreateTime = view.findViewById(R.id.recipe_createtime_textview);
            TextView recipeLike = view.findViewById(R.id.recipe_like_textview);
            recipeTitle.setText(mRecipeDataList.get(position).getCreateTime().split(" ")[0]+"食谱");
            recipeImage.setImageURI(Uri.fromFile(new File(mRecipeDataList.get(position).getRecipe_image())));
            recipeCreateTime = view.findViewById(R.id.recipe_createtime_textview);
            recipeLike = view.findViewById(R.id.recipe_like_textview);
            return view;
        }
    }

    //item点击事件
    AdapterView.OnItemClickListener itemOnClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            RecipeData mRecipe = (RecipeData)adapterView.getItemAtPosition(position);
            Log.i("ItemClick","点击了"+mRecipe.getId());
            Intent itemRecipeIntent = new Intent(RecipeActivity.this,ViewRecipeActivity.class);
            itemRecipeIntent.putExtra("recipeId", mRecipe.getId());
            startActivity(itemRecipeIntent);
        }
    };


}
