package com.example.semon.zhihuishu.Database;
/**
 * 主要是用户信息的管理操作
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.semon.zhihuishu.User.UserData;

public class UserDataManager {             //用户数据管理类
    //一些宏定义和声明
    private static final String TAG = "UserDataManager";
    public static final String TABLE_NAME_USER = "user";
    public static final String ID = "_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_PWD = "user_pwd";
    public static final String USER_ROLE = "user_role";

    private Context mContext = null;
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataManagerHelper mDatabaseHelper = null;


    public UserDataManager(Context context) {
        mContext = context;
        Log.i(TAG, "UserDataManager construction!");
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
    public long insertUserData(UserData userData) {
        String userName = userData.getUserName();
        String userPwd = userData.getUserPwd();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, userName);
        values.put(USER_PWD, userPwd);
        return mSQLiteDatabase.insert(TABLE_NAME_USER, ID, values);
    }

    //更新用户信息，如修改密码
    public boolean updateUserData(UserData userData) {
        //int id = userData.getUserId();
        String userName = userData.getUserName();
        String userPwd = userData.getUserPwd();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, userName);
        values.put(USER_PWD, userPwd);
        return mSQLiteDatabase.update(TABLE_NAME_USER, values, null, null) > 0;
        //return mSQLiteDatabase.update(TABLE_NAME, values, ID + "=" + id, null) > 0;
    }

    //
    public Cursor fetchUserData(int id) throws SQLException {
        Cursor mCursor = mSQLiteDatabase.query(false, TABLE_NAME_USER, null, ID
                + "=" + id, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //
    public Cursor fetchAllUserDatas() {
        return mSQLiteDatabase.query(TABLE_NAME_USER, null, null, null, null, null,
                null);
    }

    //根据id删除用户
    public boolean deleteUserData(int id) {
        return mSQLiteDatabase.delete(TABLE_NAME_USER, ID + "=" + id, null) > 0;
    }

    //根据用户名注销
    public boolean deleteUserDatabyname(String name) {
        return mSQLiteDatabase.delete(TABLE_NAME_USER, USER_NAME + "=" + name, null) > 0;
    }

    //删除所有用户
    public boolean deleteAllUserDatas() {
        return mSQLiteDatabase.delete(TABLE_NAME_USER, null, null) > 0;
    }

    //
    public String getStringByColumnName(String columnName, int id) {
        Cursor mCursor = fetchUserData(id);
        int columnIndex = mCursor.getColumnIndex(columnName);
        String columnValue = mCursor.getString(columnIndex);
        mCursor.close();
        return columnValue;
    }

    //
    public boolean updateUserDataById(String columnName, int id,
                                      String columnValue) {
        ContentValues values = new ContentValues();
        values.put(columnName, columnValue);
        return mSQLiteDatabase.update(TABLE_NAME_USER, values, ID + "=" + id, null) > 0;
    }

    //根据用户名找用户，可以判断注册时用户名是否已经存在
    public int findUserByName(String userName) {
        Log.i(TAG, "findUserByName , userName=" + userName);
        int result = 0;
        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_USER, null, USER_NAME + "=" + userName, null, null, null, null);
        if (mCursor != null) {
            result = mCursor.getCount();
            mCursor.close();
            Log.i(TAG, "findUserByName , result=" + result);
        }
        return result;
    }

    //根据用户名和密码找用户，用于登录
    public int findUserByNameAndPwd(String userName, String pwd) {
        Log.i(TAG, "findUserByNameAndPwd");
        int result = 0;
//        Cursor mCursor = mSQLiteDatabase.rawQuery("select * from "+TABLE_NAME_USER+" where ?=? and ?=?",new String[]{USER_NAME,userName,USER_PWD,pwd});
        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_USER, null, USER_NAME + "= '" + userName + "' and " + USER_PWD + "='" + pwd + "'",
                null, null, null, null);
        if (mCursor != null) {
            result = mCursor.getCount();
            mCursor.close();
            Log.i(TAG, "findUserByNameAndPwd , result=" + result);
        }
        return result;
    }

}
