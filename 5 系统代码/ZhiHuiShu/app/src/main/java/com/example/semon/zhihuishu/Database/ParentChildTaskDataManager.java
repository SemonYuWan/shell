package com.example.semon.zhihuishu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.semon.zhihuishu.Constant.ParentChildTaskData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 亲子任务相关数据库操作
 */
public class ParentChildTaskDataManager {
    //一些宏定义和声明
    private static final String TAG = "ParentTaskDataManager";
    public static final String TABLE_NAME_PARENTCHILDTASK = "parentChildTask";
    public static final String ID = "_id";
    public static final String TASK_TITLE = "task_title";
    public static final String TASK_CONTEXT = "task_context";
    public static final String TASK_IMAGE = "task_image";
    public static final String SCHOOL_ID = "school_id";
    public static final String CLASS_ID = "class_id";
    public static final String CREATER = "creater";
    public static final String CREATE_TIME = "createTime";
    public static final String REMARK = "remark";


    private Context mContext = null;
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataManagerHelper mDatabaseHelper = null;

    public ParentChildTaskDataManager(Context context) {
        mContext = context;
        Log.i(TAG, "ParentChildTaskDataManager construction!");
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

    public List findAllParentChildTask(){
        List <ParentChildTaskData> parentChildTaskDataList = new ArrayList<>();

        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_PARENTCHILDTASK, null, null, null, null, null, CREATE_TIME + " DESC");
        while (mCursor.moveToNext()){
            ParentChildTaskData parentChildTaskData = new ParentChildTaskData();
            parentChildTaskData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            parentChildTaskData.setTask_title(mCursor.getString(mCursor.getColumnIndex(TASK_TITLE)));
            parentChildTaskData.setTask_context(mCursor.getString(mCursor.getColumnIndex(TASK_CONTEXT)));
            parentChildTaskData.setTask_image(mCursor.getString(mCursor.getColumnIndex(TASK_IMAGE)));
            parentChildTaskData.setSchool_id(mCursor.getString(mCursor.getColumnIndex(SCHOOL_ID)));
            parentChildTaskData.setClass_id(mCursor.getString(mCursor.getColumnIndex(CLASS_ID)));
            parentChildTaskData.setCreater(mCursor.getString(mCursor.getColumnIndex(CREATER)));
//            parentChildTaskData.setCreateTime(Date.valueOf(mCursor.getString(mCursor.getColumnIndex(CREATE_TIME))));
            parentChildTaskData.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
            parentChildTaskDataList.add(parentChildTaskData);
        }
        return parentChildTaskDataList;
    }
    //通过ID查找
    public ParentChildTaskData findParentChildTaskById(String id) {


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_PARENTCHILDTASK, null, ID + " = ?", new
                String[]{id}, null, null, null);

        ParentChildTaskData parentChildTaskData = new ParentChildTaskData();
        while (mCursor.moveToNext()) {
            parentChildTaskData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            parentChildTaskData.setTask_title(mCursor.getString(mCursor.getColumnIndex(TASK_TITLE)));
            parentChildTaskData.setTask_context(mCursor.getString(mCursor.getColumnIndex(TASK_CONTEXT)));
            parentChildTaskData.setTask_image(mCursor.getString(mCursor.getColumnIndex(TASK_IMAGE)));
            parentChildTaskData.setSchool_id(mCursor.getString(mCursor.getColumnIndex(SCHOOL_ID)));
            parentChildTaskData.setClass_id(mCursor.getString(mCursor.getColumnIndex(CLASS_ID)));
            parentChildTaskData.setCreater(mCursor.getString(mCursor.getColumnIndex(CREATER)));
            //            parentChildTaskData.setCreateTime(Date.valueOf(mCursor.getString(mCursor.getColumnIndex(CREATE_TIME))));
            parentChildTaskData.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
        }
        return parentChildTaskData;
    }

    //添加亲子任务
    public long addParentChildTask(ParentChildTaskData parentChildTaskData){
        ContentValues values = new ContentValues();
        values.put(ID,parentChildTaskData.getId());
        values.put(TASK_TITLE,parentChildTaskData.getTask_title());
        values.put(TASK_CONTEXT,parentChildTaskData.getTask_context());
        values.put(TASK_IMAGE,parentChildTaskData.getTask_image());
        values.put(SCHOOL_ID,parentChildTaskData.getSchool_id());
        values.put(CLASS_ID,parentChildTaskData.getClass_id());
        values.put(CREATER,parentChildTaskData.getCreater());
        values.put(CREATE_TIME, (new Date()).toLocaleString());
        values.put(REMARK,parentChildTaskData.getRemark());
        return mSQLiteDatabase.insert(TABLE_NAME_PARENTCHILDTASK, null, values);
    }
    //更新修改亲子任务
    public long updateParentChildTask(ParentChildTaskData parentChildTaskData){
        ContentValues values = new ContentValues();
        values.put(ID,parentChildTaskData.getId());
        values.put(TASK_TITLE,parentChildTaskData.getTask_title());
        values.put(TASK_CONTEXT,parentChildTaskData.getTask_context());
        values.put(TASK_IMAGE,parentChildTaskData.getTask_image());
        values.put(SCHOOL_ID,parentChildTaskData.getSchool_id());
        values.put(CLASS_ID,parentChildTaskData.getClass_id());
        values.put(CREATER,parentChildTaskData.getCreater());
        values.put(CREATE_TIME, (new Date()).toLocaleString());
        values.put(REMARK,parentChildTaskData.getRemark());
        return mSQLiteDatabase.update(TABLE_NAME_PARENTCHILDTASK,values,ID + "=?",new String[] {parentChildTaskData.getId()});
    }
    //根据id删除用户
    public boolean deleteParentChildTaskById(String id) {
        return mSQLiteDatabase.delete(TABLE_NAME_PARENTCHILDTASK, ID + "=?", new String[]{id}) > 0;
    }

}
