package com.example.semon.zhihuishu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.semon.zhihuishu.Constant.RecipeData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecipeDataManager {
    //一些宏定义和声明
    private static final String TAG = "RecipeDataManager";
    public static final String TABLE_NAME_RECIPE = "recipe";
    public static final String ID = "_id";
    public static final String RECIPE = "recipe";//
    public static final String RECIPE_IMAGE = "recipe_image";//
    public static final String SCHOOL_ID = "school_id";//
    public static final String CHECK = "mcheck";//
    public static final String CREARER = "creater";//
    public static final String CREARE_TIME = "createTime";//
    public static final String REMARK = "remark";//


    private Context mContext = null;
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataManagerHelper mDatabaseHelper = null;

    public RecipeDataManager(Context context) {
        mContext = context;
        Log.i(TAG, "RecipeDataManager construction!");
    }

    //打开数据库
    public void openDataBase() throws SQLException {
        mDatabaseHelper = new DataManagerHelper(mContext);
        mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
    }

    //关闭数据库
    public void closeDataBase() throws SQLException {
        mDatabaseHelper.close();
    }

    //查找所有
    public List findAllRecipe() {
        List<RecipeData> mRecipeDataList = new ArrayList<>();

        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_RECIPE, null, null, null, null,
                null, CREARE_TIME + " DESC");

        while (mCursor.moveToNext()) {
            RecipeData mRecipeData = new RecipeData();
            mRecipeData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            mRecipeData.setRecipe(mCursor.getString(mCursor.getColumnIndex(RECIPE)));
            mRecipeData.setRecipe_image(mCursor.getString(mCursor.getColumnIndex(RECIPE_IMAGE)));
            mRecipeData.setSchoolId(mCursor.getString(mCursor.getColumnIndex(SCHOOL_ID)));
            mRecipeData.setCheck(mCursor.getString(mCursor.getColumnIndex(CHECK)));
            mRecipeData.setCreater(mCursor.getString(mCursor.getColumnIndex(CREARER)));
            mRecipeData.setCreateTime(mCursor.getString(mCursor.getColumnIndex(CREARE_TIME)));
            mRecipeData.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
            mRecipeDataList.add(mRecipeData);
        }
        return mRecipeDataList;
    }

    //查找所有
    public List findAllRecipeBySchoolid(String schoolId) {
        List<RecipeData> mRecipeDataList = new ArrayList<>();

        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_RECIPE, null, SCHOOL_ID +"=?", new String[]{schoolId}, null,
                null, CREARE_TIME + " DESC");

        while (mCursor.moveToNext()) {
            RecipeData mRecipeData = new RecipeData();
            mRecipeData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            mRecipeData.setRecipe(mCursor.getString(mCursor.getColumnIndex(RECIPE)));
            mRecipeData.setRecipe_image(mCursor.getString(mCursor.getColumnIndex(RECIPE_IMAGE)));
            mRecipeData.setSchoolId(mCursor.getString(mCursor.getColumnIndex(SCHOOL_ID)));
            mRecipeData.setCheck(mCursor.getString(mCursor.getColumnIndex(CHECK)));
            mRecipeData.setCreater(mCursor.getString(mCursor.getColumnIndex(CREARER)));
            mRecipeData.setCreateTime(mCursor.getString(mCursor.getColumnIndex(CREARE_TIME)));
            mRecipeData.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
            mRecipeDataList.add(mRecipeData);
        }
        return mRecipeDataList;
    }

    //通过ID查找
    public RecipeData findRecipeById(String id) {


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_RECIPE, null, ID + " = ?", new
                String[]{id}, null, null, null);

        RecipeData mRecipeData = new RecipeData();
        while (mCursor.moveToNext()) {

            mRecipeData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            mRecipeData.setRecipe(mCursor.getString(mCursor.getColumnIndex(RECIPE)));
            mRecipeData.setRecipe_image(mCursor.getString(mCursor.getColumnIndex(RECIPE_IMAGE)));
            mRecipeData.setSchoolId(mCursor.getString(mCursor.getColumnIndex(SCHOOL_ID)));
            mRecipeData.setCheck(mCursor.getString(mCursor.getColumnIndex(CHECK)));
            mRecipeData.setCreater(mCursor.getString(mCursor.getColumnIndex(CREARER)));
            mRecipeData.setCreateTime(mCursor.getString(mCursor.getColumnIndex(CREARE_TIME)));
            mRecipeData.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
        }
        return mRecipeData;
    }

    //添加
    public long addRecipe(RecipeData mRecipeData) {
        ContentValues values = new ContentValues();
        values.put(ID, mRecipeData.getId());
        values.put(RECIPE, mRecipeData.getRecipe());
        values.put(RECIPE_IMAGE, (mRecipeData.getRecipe_image()));
        values.put(SCHOOL_ID, (mRecipeData.getSchoolId()));
        values.put(CHECK, (mRecipeData.getCheck()));
        values.put(CREARER, (mRecipeData.getCreater()));
        values.put(CREARE_TIME, mRecipeData.getCreateTime());
        values.put(REMARK,mRecipeData.getRemark());
        return mSQLiteDatabase.insert(TABLE_NAME_RECIPE, null, values);
    }

    //根据id删除
    public boolean deleteRecipeDataById(String id) {
        return mSQLiteDatabase.delete(TABLE_NAME_RECIPE, ID + "=?", new String[]{id}) > 0;
    }
}
