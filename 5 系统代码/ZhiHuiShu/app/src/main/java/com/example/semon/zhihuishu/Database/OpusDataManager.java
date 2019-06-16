package com.example.semon.zhihuishu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.semon.zhihuishu.Constant.OpusData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 学生作品相关数据库操作
 */
public class OpusDataManager {
    //一些宏定义和声明
    private static final String TAG = "OpusDataManager";
    public static final String TABLE_NAME_OPUS = "opus";
    public static final String ID = "_id";
    public static final String OPUS_TITLE = "opus_title";
    public static final String OPUS_CONTEXT = "opus_context";
    public static final String OPUS_IMAGE = "opus_image";
    public static final String SCHOOL_ID = "school_id";
    public static final String CLASS_ID = "class_id";
    public static final String CREATER = "creater";
    public static final String CREATE_TIME = "createTime";
    public static final String REMARK = "remark";


    private Context mContext = null;
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataManagerHelper mDatabaseHelper = null;

    public OpusDataManager(Context context) {
        mContext = context;
        Log.i(TAG, "OpusDataManager construction!");
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

    public List findAllOpusByClass(String classId){
        List <OpusData> opusDataList = new ArrayList<>();

//        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_OPUS, null, null, null, null, null, CREATE_TIME + " DESC");
        Cursor mCursor = mSQLiteDatabase.rawQuery("select * from "+TABLE_NAME_OPUS +" where "+CLASS_ID +" =?;",new String[]{classId});
        while (mCursor.moveToNext()){
            OpusData opusData = new OpusData();
            opusData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            opusData.setOpus_title(mCursor.getString(mCursor.getColumnIndex(OPUS_TITLE)));
            opusData.setOpus_context(mCursor.getString(mCursor.getColumnIndex(OPUS_CONTEXT)));
            opusData.setOpusImage(mCursor.getString(mCursor.getColumnIndex(OPUS_IMAGE)));
            opusData.setSchool_id(mCursor.getString(mCursor.getColumnIndex(SCHOOL_ID)));
            opusData.setClass_id(mCursor.getString(mCursor.getColumnIndex(CLASS_ID)));
            opusData.setCreater(mCursor.getString(mCursor.getColumnIndex(CREATER)));
//            opusData.setCreateTime(Date.valueOf(mCursor.getString(mCursor.getColumnIndex(CREATE_TIME))));
            opusData.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
            opusDataList.add(opusData);
        }
        return opusDataList;
    }
    //添加通知
    public long addOpus(OpusData opusData){
        ContentValues values = new ContentValues();
        values.put(ID,opusData.getId());
        values.put(OPUS_TITLE,opusData.getOpus_title());
        values.put(OPUS_CONTEXT,opusData.getOpus_context());
        values.put(OPUS_IMAGE,opusData.getOpusImage());
        values.put(SCHOOL_ID,opusData.getSchool_id());
        values.put(CLASS_ID,opusData.getClass_id());
        values.put(CREATER,opusData.getCreater());
        values.put(CREATE_TIME, (new Date()).toLocaleString());
        values.put(REMARK,opusData.getRemark());
        return mSQLiteDatabase.insert(TABLE_NAME_OPUS, null, values);
    }
    //更新修改通知
    public long updateOpus(OpusData opusData){
        ContentValues values = new ContentValues();
        values.put(ID,opusData.getId());
        values.put(OPUS_TITLE,opusData.getOpus_title());
        values.put(OPUS_CONTEXT,opusData.getOpus_context());
        values.put(OPUS_IMAGE,opusData.getOpusImage());
        values.put(SCHOOL_ID,opusData.getSchool_id());
        values.put(CLASS_ID,opusData.getClass_id());
        values.put(CREATER,opusData.getCreater());
        values.put(CREATE_TIME, (new Date()).toLocaleString());
        values.put(REMARK,opusData.getRemark());
        return mSQLiteDatabase.update(TABLE_NAME_OPUS,values,ID + "=?",new String[] {opusData.getId()});
    }

    //通过ID查找发现文章
    public OpusData findOpusById(String id) {


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(System.currentTimeMillis());

        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_OPUS, null, ID + " = ?", new
                String[]{id}, null, null, null);

        OpusData mOpusData = new OpusData();
        while (mCursor.moveToNext()) {

            mOpusData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            mOpusData.setOpus_title(mCursor.getString(mCursor.getColumnIndex(OPUS_TITLE)));
            mOpusData.setOpus_context(mCursor.getString(mCursor.getColumnIndex(OPUS_CONTEXT)));
            mOpusData.setOpusImage(mCursor.getString(mCursor.getColumnIndex(OPUS_IMAGE)));
            mOpusData.setSchool_id(mCursor.getString(mCursor.getColumnIndex(SCHOOL_ID)));
            mOpusData.setClass_id(mCursor.getString(mCursor.getColumnIndex(CLASS_ID)));
            mOpusData.setCreater(mCursor.getString(mCursor.getColumnIndex(CREATER)));
            mOpusData.setCreateTime(mCursor.getString(mCursor.getColumnIndex(CREATE_TIME)));
            mOpusData.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
        }
        return mOpusData;
    }

    //根据id删除
    public boolean deleteOpusDataById(String id) {
        return mSQLiteDatabase.delete(TABLE_NAME_OPUS, ID + "=?", new String[]{id}) > 0;
    }

}
