package com.example.semon.zhihuishu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.semon.zhihuishu.Constant.ChildCommentData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 学生点评相关数据库操作
 */
public class ChildCommentDataManager {
    //一些宏定义和声明
    private static final String TAG = "ChildCommentDataManager";
    public static final String TABLE_NAME_CHILDCOMMENT = "childComment";
    public static final String ID = "_id";
    public static final String COMMENT_TITLE = "comment_title";
    public static final String COMMENT_CONTEXT = "comment_context";
    public static final String SCHOOL_ID = "school_id";
    public static final String CLASS_ID = "class_id";
    public static final String CREATER = "creater";
    public static final String CREATE_TIME = "createTime";
    public static final String REMARK = "remark";


    private Context mContext = null;
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataManagerHelper mDatabaseHelper = null;

    public ChildCommentDataManager(Context context) {
        mContext = context;
        Log.i(TAG, "ChildCommentDataManager construction!");
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

    public List findAllChildCommentByClass(){
        List <ChildCommentData> childCommentDataList = new ArrayList<>();

        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_CHILDCOMMENT, null, null, null, null, null, CREATE_TIME + " DESC");
        while (mCursor.moveToNext()){
            ChildCommentData childCommentData = new ChildCommentData();
            childCommentData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            childCommentData.setComment_title(mCursor.getString(mCursor.getColumnIndex(COMMENT_TITLE)));
            childCommentData.setComment_context(mCursor.getString(mCursor.getColumnIndex(COMMENT_CONTEXT)));
            childCommentData.setSchool_id(mCursor.getString(mCursor.getColumnIndex(SCHOOL_ID)));
            childCommentData.setClass_id(mCursor.getString(mCursor.getColumnIndex(CLASS_ID)));
            childCommentData.setCreater(mCursor.getString(mCursor.getColumnIndex(CREATER)));
            childCommentData.setCreateTime(mCursor.getString(mCursor.getColumnIndex(CREATE_TIME)));
            childCommentData.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
            childCommentDataList.add(childCommentData);
        }
        return childCommentDataList;
    }

    public ChildCommentData findChildCommentById(String commentId){
        ChildCommentData mChildCommentDataList = new ChildCommentData();

        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_CHILDCOMMENT, null, ID +"=?", new String[]{commentId}, null, null, CREATE_TIME + " DESC");
        while (mCursor.moveToNext()){
            mChildCommentDataList.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            mChildCommentDataList.setComment_title(mCursor.getString(mCursor.getColumnIndex(COMMENT_TITLE)));
            mChildCommentDataList.setComment_context(mCursor.getString(mCursor.getColumnIndex(COMMENT_CONTEXT)));
            mChildCommentDataList.setSchool_id(mCursor.getString(mCursor.getColumnIndex(SCHOOL_ID)));
            mChildCommentDataList.setClass_id(mCursor.getString(mCursor.getColumnIndex(CLASS_ID)));
            mChildCommentDataList.setCreater(mCursor.getString(mCursor.getColumnIndex(CREATER)));
            mChildCommentDataList.setCreateTime(mCursor.getString(mCursor.getColumnIndex(CREATE_TIME)));
            mChildCommentDataList.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));

        }
        return mChildCommentDataList;
    }

    //添加
    public long addChildComment(ChildCommentData childCommentData){
        ContentValues values = new ContentValues();
        values.put(ID,childCommentData.getId());
        values.put(COMMENT_TITLE,childCommentData.getComment_title());
        values.put(COMMENT_CONTEXT,childCommentData.getComment_context());
        values.put(SCHOOL_ID,childCommentData.getSchool_id());
        values.put(CLASS_ID,childCommentData.getClass_id());
        values.put(CREATER,childCommentData.getCreater());
        values.put(CREATE_TIME, (new Date()).toLocaleString());
        values.put(REMARK,childCommentData.getRemark());
        return mSQLiteDatabase.insert(TABLE_NAME_CHILDCOMMENT, null, values);
    }
    //更新修改
    public long updateChildComment(ChildCommentData childCommentData){
        ContentValues values = new ContentValues();
        values.put(ID,childCommentData.getId());
        values.put(COMMENT_TITLE,childCommentData.getComment_title());
        values.put(COMMENT_CONTEXT,childCommentData.getComment_context());
        values.put(SCHOOL_ID,childCommentData.getSchool_id());
        values.put(CLASS_ID,childCommentData.getClass_id());
        values.put(CREATER,childCommentData.getCreater());
        values.put(CREATE_TIME, (new Date()).toLocaleString());
        values.put(REMARK,childCommentData.getRemark());
        return mSQLiteDatabase.update(TABLE_NAME_CHILDCOMMENT,values,ID + "=?",new String[] {childCommentData.getId()});
    }

    //根据id删除
    public boolean deleteChildCommentDataById(String id) {
        return mSQLiteDatabase.delete(TABLE_NAME_CHILDCOMMENT, ID + "=?", new String[]{id}) > 0;
    }

}
