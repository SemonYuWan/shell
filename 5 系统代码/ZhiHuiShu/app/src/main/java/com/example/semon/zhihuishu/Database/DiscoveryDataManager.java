package com.example.semon.zhihuishu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.semon.zhihuishu.Constant.DiscoveryData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiscoveryDataManager {
    //一些宏定义和声明
    private static final String TAG = "DiscoveryDataManager";
    public static final String TABLE_NAME_DISCOVERY = "discovery";
    public static final String ID = "_id";
    public static final String TITLE = "title";//发现标题
    public static final String CONTEXT = "conext";//发现内容
    public static final String IMAGE = "image";//发现图片
    public static final String CREATER = "creater";//创建人
    public static final String CREATE_TIME = "createTime";//创建时间
    public static final String LIKE = "likeCount";//点赞


    private Context mContext = null;
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataManagerHelper mDatabaseHelper = null;

    public DiscoveryDataManager(Context context) {
        mContext = context;
        Log.i(TAG, "DiscoveryDataManager construction!");
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

    //查找所有发现文章
    public List findAllDiscovery() {
        List<DiscoveryData> discoveryDataList = new ArrayList<>();

        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_DISCOVERY, null, null, null, null,
                null, CREATE_TIME + " DESC");
        //        Cursor mCursor = mSQLiteDatabase.rawQuery("select * from "+TABLE_NAME_NOTICE,
        // null);
        while (mCursor.moveToNext()) {
            DiscoveryData mDiscoveryData = new DiscoveryData();
            mDiscoveryData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            mDiscoveryData.setTitle(mCursor.getString(mCursor.getColumnIndex(TITLE)));
            mDiscoveryData.setConext(mCursor.getString(mCursor.getColumnIndex(CONTEXT)));
            mDiscoveryData.setImage(mCursor.getString(mCursor.getColumnIndex(IMAGE)));
            mDiscoveryData.setCreater(mCursor.getString(mCursor.getColumnIndex(CREATER)));
            mDiscoveryData.setCreateTime(mCursor.getString(mCursor.getColumnIndex(CREATE_TIME)));
            mDiscoveryData.setLike(mCursor.getString(mCursor.getColumnIndex(LIKE)));
            discoveryDataList.add(mDiscoveryData);
        }
        return discoveryDataList;
    }

    //通过ID查找发现文章
    public DiscoveryData findDiscoveryById(String id) {


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        //        Cursor mCursor = mSQLiteDatabase.rawQuery("select * from " +
        // TABLE_NAME_DISCOVERY +
        //                " WHERE " + DATE + " = ?", new String[]{simpleDateFormat.format(date)});
        //        Cursor mCursor = mSQLiteDatabase.rawQuery("select * from " +
        // TABLE_NAME_DISCOVERY + " where " + ID + " ='?';"
        //                , new String[]{id});
        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_DISCOVERY, null, ID + " = ?", new
                String[]{id}, null, null, null);

        DiscoveryData mDiscoveryData = new DiscoveryData();
        while (mCursor.moveToNext()) {

            mDiscoveryData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            mDiscoveryData.setTitle(mCursor.getString(mCursor.getColumnIndex(TITLE)));
            mDiscoveryData.setConext(mCursor.getString(mCursor.getColumnIndex(CONTEXT)));
            mDiscoveryData.setImage(mCursor.getString(mCursor.getColumnIndex(IMAGE)));
            mDiscoveryData.setCreater(mCursor.getString(mCursor.getColumnIndex(CREATER)));
            mDiscoveryData.setCreateTime(mCursor.getString(mCursor.getColumnIndex(CREATE_TIME)));
            mDiscoveryData.setLike(mCursor.getString(mCursor.getColumnIndex(LIKE)));
        }
        return mDiscoveryData;
    }

    //添加发现文章
    public long addDiscovery(DiscoveryData mDiscoveryData) {
        ContentValues values = new ContentValues();
        values.put(ID, mDiscoveryData.getId());
        values.put(TITLE, mDiscoveryData.getTitle());
        values.put(CONTEXT, (mDiscoveryData.getConext()));
        values.put(IMAGE, (mDiscoveryData.getImage()));
        values.put(CREATER, (mDiscoveryData.getCreater()));
        values.put(CREATE_TIME, mDiscoveryData.getCreateTime());
        values.put(LIKE, mDiscoveryData.getLike());
        return mSQLiteDatabase.insert(TABLE_NAME_DISCOVERY, null, values);
    }

    //根据id删除发现
    public boolean deleteDiscoveryDataById(String id) {
        return mSQLiteDatabase.delete(TABLE_NAME_DISCOVERY, ID + "=?", new String[]{id}) > 0;
    }

}
