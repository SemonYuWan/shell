package com.example.semon.zhihuishu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.semon.zhihuishu.Constant.MessageData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageDataManager {
    //一些宏定义和声明
    private static final String TAG = "MessageDataManager";
    public static final String TABLE_NAME_MESSAGE = "message";
    public static final String ID = "_id";
    public static final String TITLE = "title";//发现标题
    public static final String CONTEXT = "conext";//发现内容
    public static final String IMAGE = "image";//发现图片
    public static final String CREARER = "creater";//创建人
    public static final String CREARE_TIME = "createTime";//创建时间
    public static final String TYPE = "type";//创建时间
    public static final String LIKE = "likeCount";//点赞


    private Context mContext = null;
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataManagerHelper mDatabaseHelper = null;

    public MessageDataManager(Context context) {
        mContext = context;
        Log.i(TAG, "MessageDataManager construction!");
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
    public List findAllMessage() {
        List<MessageData> messageDataList = new ArrayList<>();

        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_MESSAGE, null, null, null, null,
                null, CREARE_TIME + " DESC");

        while (mCursor.moveToNext()) {
            MessageData mMessageData = new MessageData();
            mMessageData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            mMessageData.setTitle(mCursor.getString(mCursor.getColumnIndex(TITLE)));
            mMessageData.setConext(mCursor.getString(mCursor.getColumnIndex(CONTEXT)));
            mMessageData.setImage(mCursor.getString(mCursor.getColumnIndex(IMAGE)));
            mMessageData.setCreater(mCursor.getString(mCursor.getColumnIndex(CREARER)));
            mMessageData.setCreateTime(mCursor.getString(mCursor.getColumnIndex(CREARE_TIME)));
            mMessageData.setType(mCursor.getString(mCursor.getColumnIndex(TYPE)));
            mMessageData.setLike(mCursor.getString(mCursor.getColumnIndex(LIKE)));
            messageDataList.add(mMessageData);
        }
        return messageDataList;
    }

    //通过ID查找发现文章
    public MessageData findMessageById(String id) {


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_MESSAGE, null, ID + " = ?", new
                String[]{id}, null, null, null);

        MessageData mMessageData = new MessageData();
        while (mCursor.moveToNext()) {

            mMessageData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            mMessageData.setTitle(mCursor.getString(mCursor.getColumnIndex(TITLE)));
            mMessageData.setConext(mCursor.getString(mCursor.getColumnIndex(CONTEXT)));
            mMessageData.setImage(mCursor.getString(mCursor.getColumnIndex(IMAGE)));
            mMessageData.setCreater(mCursor.getString(mCursor.getColumnIndex(CREARER)));
            mMessageData.setCreateTime(mCursor.getString(mCursor.getColumnIndex(CREARE_TIME)));
            mMessageData.setType(mCursor.getString(mCursor.getColumnIndex(TYPE)));
            mMessageData.setLike(mCursor.getString(mCursor.getColumnIndex(LIKE)));
        }
        return mMessageData;
    }

    //添加发现文章
    public long addMessage(MessageData mMessageData) {
        ContentValues values = new ContentValues();
        values.put(ID, mMessageData.getId());
        values.put(TITLE, mMessageData.getTitle());
        values.put(CONTEXT, (mMessageData.getConext()));
        values.put(IMAGE, (mMessageData.getImage()));
        values.put(CREARER, (mMessageData.getCreater()));
        values.put(CREARE_TIME, mMessageData.getCreateTime());
        values.put(TYPE,mMessageData.getType());
        values.put(LIKE, mMessageData.getLike());
        return mSQLiteDatabase.insert(TABLE_NAME_MESSAGE, null, values);
    }

    //根据id删除
    public boolean deleteMessageDataById(String id) {
        return mSQLiteDatabase.delete(TABLE_NAME_MESSAGE, ID + "=?", new String[]{id}) > 0;
    }
}
