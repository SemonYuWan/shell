package com.example.semon.zhihuishu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.semon.zhihuishu.Constant.ChildData;

import java.util.ArrayList;
import java.util.List;

/**
 * 孩子相关数据库操作
 */
public class ChildDataManager {
    //一些宏定义和声明
    private static final String TAG = "ChildDataManager";
    public static final String TABLE_NAME_CHILD = "child";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String SEX = "sex";
    public static final String BIRTHDAY = "birthday";
    public static final String PARENT_ID = "parent_id";
    public static final String SCHOOL_ID = "school_id";
    public static final String CLASS_ID = "class_id";
    public static final String REMARK = "remark";

    private Context mContext = null;
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataManagerHelper mDatabaseHelper = null;


    public ChildDataManager(Context context) {
        mContext = context;
        Log.i(TAG, "ChildDataManager construction!");
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

    //添加新用户，即注册
    public long insertChildData(ChildData childData) {
        ContentValues values = new ContentValues();
        values.put(ID, childData.getId());
        values.put(NAME, childData.getName());
        values.put(SEX, childData.getSex());
        values.put(BIRTHDAY, childData.getBirthday());
        values.put(PARENT_ID, childData.getParent_id());
        values.put(SCHOOL_ID, childData.getSchool_id());
        values.put(CLASS_ID, childData.getClass_id());
        values.put(REMARK, childData.getRemark());
        return mSQLiteDatabase.insert(TABLE_NAME_CHILD, ID, values);
    }

    //更新修改孩子信息
    public boolean updateChildData(ChildData childData) {
        ContentValues values = new ContentValues();
        //        values.put(ID, childData.getId());
        values.put(NAME, childData.getName());
        values.put(SEX, childData.getSex());
        values.put(BIRTHDAY, childData.getBirthday());
        values.put(PARENT_ID, childData.getParent_id());
        values.put(SCHOOL_ID, childData.getSchool_id());
        values.put(CLASS_ID, childData.getClass_id());
        values.put(REMARK, childData.getRemark());
        return mSQLiteDatabase.update(TABLE_NAME_CHILD, values, ID + " = ?", new String[]{childData.getId()}) > 0;
    }

    //通过孩子ID查找孩子
    public ChildData findChildDataByChildId(String childId){
        ChildData mChildData = new ChildData();
        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_CHILD, null, ID + "=?", new String[]{childId}, null, null, null);
        if(mCursor.getCount()==1){
            mCursor.moveToNext();
            mChildData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            mChildData.setName(mCursor.getString(mCursor.getColumnIndex(NAME)));
            mChildData.setSex(mCursor.getString(mCursor.getColumnIndex(SEX)));
            mChildData.setBirthday(mCursor.getString(mCursor.getColumnIndex(BIRTHDAY)));
            mChildData.setParent_id(mCursor.getString(mCursor.getColumnIndex(PARENT_ID)));
            mChildData.setSchool_id(mCursor.getString(mCursor.getColumnIndex(SCHOOL_ID)));
            mChildData.setClass_id(mCursor.getString(mCursor.getColumnIndex(CLASS_ID)));
            mChildData.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
        }
        return mChildData;
    }

    //查找班级中的所有孩子
    public List findAllChildByClassId(String classId) {
        Log.i(TAG, "findAllChildByClassId");
        List<ChildData> childDataList = new ArrayList<>();

//        String findAllChildByClassIdSql = "select * from " + TABLE_NAME_CHILD + " where " + CLASS_ID + " ='?';";
//        Cursor mCursor = mSQLiteDatabase.rawQuery(findAllChildByClassIdSql, new String[]{classId});
        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_CHILD, null, CLASS_ID + "=?", new String[]{classId}, null, null, null);

        while (mCursor.moveToNext()) {
            ChildData mChildData = new ChildData();
            mChildData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            mChildData.setName(mCursor.getString(mCursor.getColumnIndex(NAME)));
            mChildData.setSex(mCursor.getString(mCursor.getColumnIndex(SEX)));
            mChildData.setBirthday(mCursor.getString(mCursor.getColumnIndex(BIRTHDAY)));
            mChildData.setParent_id(mCursor.getString(mCursor.getColumnIndex(PARENT_ID)));
            mChildData.setSchool_id(mCursor.getString(mCursor.getColumnIndex(SCHOOL_ID)));
            mChildData.setClass_id(mCursor.getString(mCursor.getColumnIndex(CLASS_ID)));
            mChildData.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
            childDataList.add(mChildData);
        }
        return childDataList;
    }



    //根据id删除用户
    public boolean deleteUserData(int id) {
        return mSQLiteDatabase.delete(TABLE_NAME_CHILD, ID + "=" + id, null) > 0;
    }

    //根据用户名注销
    public boolean deleteUserDatabyname(String name) {
        return mSQLiteDatabase.delete(TABLE_NAME_CHILD, NAME + "=" + name, null) > 0;
    }

    //根据用户名找用户，可以判断注册时用户名是否已经存在
    public int findChildDataByName(String userName) {
        Log.i(TAG, "findChildDataByName , userName=" + userName);
        int result = 0;
        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_CHILD, null, NAME + "=?", new String[]{userName}, null, null, null);
        if (mCursor != null) {
            result = mCursor.getCount();
            mCursor.close();
            Log.i(TAG, "findChildDataByName , result=" + result);
        }
        return result;
    }


}
