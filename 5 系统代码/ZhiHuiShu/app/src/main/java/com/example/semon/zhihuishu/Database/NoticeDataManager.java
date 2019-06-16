package com.example.semon.zhihuishu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.semon.zhihuishu.Constant.NoticeBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 通知相关数据库操作
 */
public class NoticeDataManager {
    //一些宏定义和声明
    private static final String TAG = "NoticeDataManager";
    public static final String TABLE_NAME_NOTICE = "notice";
    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String CONTEXT = "context";
    public static final String SCHOOL_ID = "school_id";
    public static final String CREATE_TIME = "createTime";
    public static final String TYPE = "type";
    public static final String REMARK = "remark";


    private Context mContext = null;
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataManagerHelper mDatabaseHelper = null;

    public NoticeDataManager(Context context) {
        mContext = context;
        Log.i(TAG, "NoticeDataManager construction!");
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

    public List findAllNotice(){
        List <NoticeBean> noticeBeanList = new ArrayList<>();

        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_NOTICE, null, null, null, null, null, CREATE_TIME + " DESC");
//        Cursor mCursor = mSQLiteDatabase.rawQuery("select * from "+TABLE_NAME_NOTICE,null);
        while (mCursor.moveToNext()){
            NoticeBean noticeBean = new NoticeBean();
            noticeBean.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            noticeBean.setTitle(mCursor.getString(mCursor.getColumnIndex(TITLE)));
            noticeBean.setContext(mCursor.getString(mCursor.getColumnIndex(CONTEXT)));
            noticeBean.setSchool_id(mCursor.getString(mCursor.getColumnIndex(SCHOOL_ID)));
            noticeBean.setCreateTime(mCursor.getString(mCursor.getColumnIndex(CREATE_TIME)));
            noticeBean.setType(mCursor.getString(mCursor.getColumnIndex(TYPE)));
            noticeBean.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
            noticeBeanList.add(noticeBean);
        }
        return noticeBeanList;
    }

    public NoticeBean findNoticeById(String noticeId){
        NoticeBean noticeBean = new NoticeBean();

        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_NOTICE, null, ID +"=?", new String[]{noticeId}, null, null, CREATE_TIME + " DESC");
        while (mCursor.moveToNext()){
            noticeBean.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            noticeBean.setTitle(mCursor.getString(mCursor.getColumnIndex(TITLE)));
            noticeBean.setContext(mCursor.getString(mCursor.getColumnIndex(CONTEXT)));
            noticeBean.setSchool_id(mCursor.getString(mCursor.getColumnIndex(SCHOOL_ID)));
            noticeBean.setCreateTime(mCursor.getString(mCursor.getColumnIndex(CREATE_TIME)));
            noticeBean.setType(mCursor.getString(mCursor.getColumnIndex(TYPE)));
            noticeBean.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
        }
        return noticeBean;
    }

    //添加通知
    public long addNotice(NoticeBean noticeBean){
        ContentValues values = new ContentValues();
        values.put(ID,noticeBean.getId());
        values.put(TITLE,noticeBean.getTitle());
        values.put(CONTEXT,noticeBean.getContext());
        values.put(SCHOOL_ID,noticeBean.getSchool_id());
        values.put(CREATE_TIME, (new Date()).toLocaleString());
        values.put(TYPE,noticeBean.getType());
        values.put(REMARK,noticeBean.getRemark());
        return mSQLiteDatabase.insert(TABLE_NAME_NOTICE, null, values);
    }
    //更新修改通知
    public long updateNotice(NoticeBean noticeBean){
        ContentValues values = new ContentValues();
        values.put(TITLE,noticeBean.getTitle());
        values.put(CONTEXT,noticeBean.getContext());
        values.put(SCHOOL_ID,noticeBean.getSchool_id());
        values.put(CREATE_TIME, (new Date()).toLocaleString());
        values.put(TYPE,noticeBean.getType());
        values.put(REMARK,noticeBean.getRemark());
        return mSQLiteDatabase.update(TABLE_NAME_NOTICE,values,ID + "=?",new String[] {noticeBean.getId()});
    }

    //根据id删除
    public boolean deleteNoticeDataById(String id) {
        return mSQLiteDatabase.delete(TABLE_NAME_NOTICE, ID + "=?", new String[]{id}) > 0;
    }

}
