package com.example.semon.zhihuishu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.semon.zhihuishu.Constant.HeadmasterData;

/**
 * 园长操作相关数据库操作
 */
public class HeadmasterDataManager {
    //一些宏定义和声明
    private static final String TAG = "HeadmasterDataManager";
    public static final String TABLE_NAME_HEADMASTER = "headmaster";
    public static final String ID = "_id";
    public static final String USER = "user";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String TEL = "tel";
    public static final String SCHOOL_ID = "school_id";
    public static final String REMARK = "remark";

    private Context mContext = null;
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataManagerHelper mDatabaseHelper = null;
    private HeadmasterData mHeadmasterData = new HeadmasterData();


    public HeadmasterDataManager(Context context) {
        mContext = context;
        Log.i(TAG, "HeadmasterDataManager construction!");
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
    public long insertHeadmasterData(HeadmasterData headmasterData) {
        ContentValues values = new ContentValues();
        values.put(ID, headmasterData.getId());
        values.put(USER, headmasterData.getUser());
        values.put(PASSWORD, headmasterData.getPassword());
        values.put(NAME, headmasterData.getName());
        values.put(TEL, headmasterData.getTel());
        values.put(SCHOOL_ID, headmasterData.getSchool_id());
        values.put(REMARK, headmasterData.getRemark());
        return mSQLiteDatabase.insert(TABLE_NAME_HEADMASTER, ID, values);
    }

    //更新修改园长信息
    public boolean updateHeadmasterData(HeadmasterData headmasterData) {
        ContentValues values = new ContentValues();
        values.put(ID, headmasterData.getId());
        values.put(USER, headmasterData.getUser());
        values.put(PASSWORD, headmasterData.getPassword());
        values.put(NAME, headmasterData.getName());
        values.put(TEL, headmasterData.getTel());
        values.put(SCHOOL_ID, headmasterData.getSchool_id());
        values.put(REMARK, headmasterData.getRemark());
        return mSQLiteDatabase.update(TABLE_NAME_HEADMASTER, values, null, null) > 0;
    }

    //根据ID查找园长信息
    public HeadmasterData fetchHeadmasterDataById(String id) throws SQLException {
        Cursor mCursor = mSQLiteDatabase.query(false, TABLE_NAME_HEADMASTER, null, ID
                + "=" + id, null, null, null, null, null);
        if (mCursor.getCount() ==1) {
            mCursor.moveToNext();
            mHeadmasterData.setId(mCursor.getString(mCursor.getColumnIndex(HeadmasterDataManager.ID)));
            mHeadmasterData.setUser(mCursor.getString(mCursor.getColumnIndex(HeadmasterDataManager.USER)));
            mHeadmasterData.setName(mCursor.getString(mCursor.getColumnIndex(HeadmasterDataManager.NAME)));
            mHeadmasterData.setPassword(mCursor.getString(mCursor.getColumnIndex(HeadmasterDataManager.PASSWORD)));
            mHeadmasterData.setSchool_id(mCursor.getString(mCursor.getColumnIndex(HeadmasterDataManager.SCHOOL_ID)));
            mHeadmasterData.setTel(mCursor.getString(mCursor.getColumnIndex(HeadmasterDataManager.TEL)));
            mHeadmasterData.setRemark(mCursor.getString(mCursor.getColumnIndex(HeadmasterDataManager.REMARK)));
        }
        return mHeadmasterData;
    }

    //根据园长姓名查找园长信息
    public HeadmasterData fetchHeadmasterDataByName(String name) throws SQLException {
        Cursor mCursor = mSQLiteDatabase.query(false, TABLE_NAME_HEADMASTER, null, NAME
                + "=?", new String[]{name}, null, null, null, null);
        if (mCursor.getCount() ==1) {
            mCursor.moveToNext();
            mHeadmasterData.setId(mCursor.getString(mCursor.getColumnIndex(HeadmasterDataManager.ID)));
            mHeadmasterData.setUser(mCursor.getString(mCursor.getColumnIndex(HeadmasterDataManager.USER)));
            mHeadmasterData.setName(mCursor.getString(mCursor.getColumnIndex(HeadmasterDataManager.NAME)));
            mHeadmasterData.setPassword(mCursor.getString(mCursor.getColumnIndex(HeadmasterDataManager.PASSWORD)));
            mHeadmasterData.setSchool_id(mCursor.getString(mCursor.getColumnIndex(HeadmasterDataManager.SCHOOL_ID)));
            mHeadmasterData.setTel(mCursor.getString(mCursor.getColumnIndex(HeadmasterDataManager.TEL)));
            mHeadmasterData.setRemark(mCursor.getString(mCursor.getColumnIndex(HeadmasterDataManager.REMARK)));
        }
        return mHeadmasterData;
    }

    //根据园长Id查找园长姓名
    public String fetchHeadmasterNameById(String id) throws SQLException {
        Cursor mCursor = mSQLiteDatabase.query(false, TABLE_NAME_HEADMASTER, null, NAME
                + "=?", new String[]{id}, null, null, null, null);
        String name = null;
        if (mCursor.getCount() ==1) {
            mCursor.moveToNext();
            name = mCursor.getString(mCursor.getColumnIndex(HeadmasterDataManager.NAME));

        }
        return name;
    }

    //根据id删除用户
    public boolean deleteHeadmasterData(int id) {
        return mSQLiteDatabase.delete(TABLE_NAME_HEADMASTER, ID + "=" + id, null) > 0;
    }

    //根据用户名注销
    public boolean deleteHeadmasterDatabyname(String name) {
        return mSQLiteDatabase.delete(TABLE_NAME_HEADMASTER, NAME + "=" + name, null) > 0;
    }

    //根据用户名找用户，可以判断注册时用户名是否已经存在
    public int findTeacherByName(String userName) {
        Log.i(TAG, "findUserByName , userName=" + userName);
        int result = 0;
        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_HEADMASTER, null, NAME + "=" + userName,
                null, null, null, null);
        if (mCursor != null) {
            result = mCursor.getCount();
            mCursor.close();
            Log.i(TAG, "findUserByName , result=" + result);
        }
        return result;
    }

    //根据用户名和密码找用户，用于登录
    public String findHeadmasterByUserNameAndPwd(String userName, String pwd) {
        int result = 0;
        String userId = null;
        //        Cursor mCursor = mSQLiteDatabase.rawQuery("select * from "+TABLE_NAME_USER+"
        // where ?=? and ?=?",new String[]{USER_NAME,userName,USER_PWD,pwd});
        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_HEADMASTER, null, USER + "= '" + userName
                        + "' and " + PASSWORD + "='" + pwd + "'",
                null, null, null, null);
        if (mCursor != null) {

            result = mCursor.getCount();
            if (result == 1) {
                mCursor.moveToNext();
                userId = mCursor.getString(mCursor.getColumnIndex(ID));

            }
            mCursor.close();
            Log.i(TAG, "findHeadmasterByUserNameAndPwd , result=" + result);
        }
        return userId;
    }


}
