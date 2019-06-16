package com.example.semon.zhihuishu.Notice;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.semon.zhihuishu.Constant.NoticeBean;
import com.example.semon.zhihuishu.Database.NoticeDataManager;
import com.example.semon.zhihuishu.R;

import java.util.List;

public class NoticeActivity extends AppCompatActivity {

    private SharedPreferences userSession;
    private String user_id, user_name, password, user_type;
    private ListView noticeListView;
    private List<NoticeBean> noticeBeans;
    private NoticeDataManager mNoticeDataManager;
    private FloatingActionButton addNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        initData();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    private void initData() {

        userSession = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        user_id = userSession.getString("USER_ID", null);//(key,若无数据需要赋的值)
        user_name = userSession.getString("USER_NAME", null);
        password = userSession.getString("PASSWORD", null);
        user_type = userSession.getString("USER_TYPE", null);

        mNoticeDataManager = new NoticeDataManager(this);
        mNoticeDataManager.openDataBase();
        noticeBeans = mNoticeDataManager.findAllNotice();
        noticeListView = findViewById(R.id.notice_listview);
        MyBaseAdapter mAdapter = new MyBaseAdapter(noticeBeans);
        noticeListView.setAdapter(mAdapter);

        noticeListView.setOnItemClickListener(itemOnClick);
        noticeListView.setOnItemLongClickListener(itemOnLongClick);

        mNoticeDataManager.closeDataBase();

        addNotice = findViewById(R.id.add_notice_view_button);
        addNotice.bringToFront();
        addNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG","点击添加按钮");
                Intent addIntent = new Intent(NoticeActivity.this,AddNoticeActivity.class);
                startActivity(addIntent);
            }
        });

        if(user_type.equals("parent")){
            addNotice.setVisibility(View.GONE);
        }
        else{
            noticeListView.setOnItemLongClickListener(itemOnLongClick);
        }
    }

    //item点击事件
    AdapterView.OnItemClickListener itemOnClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            NoticeBean mNoticeBean = (NoticeBean) adapterView.getItemAtPosition(position);
            Log.i("ItemClick", "点击了" + mNoticeBean.getId());
            Intent itemNoticeIntent = new Intent(NoticeActivity.this, ViewNoticeActivity.class);
            itemNoticeIntent.putExtra("noticeId", mNoticeBean.getId());
            startActivity(itemNoticeIntent);
        }
    };

    AdapterView.OnItemLongClickListener itemOnLongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long l) {
            //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
            AlertDialog.Builder builder = new AlertDialog.Builder(NoticeActivity.this);
            //    设置Title的图标
            builder.setIcon(R.drawable.warn);
            //    设置Title的内容
            builder.setTitle("删除");
            //    设置Content来显示一个信息
            builder.setMessage("确定删除吗？");
            //    设置一个PositiveButton
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    NoticeBean mNoticeBean = (NoticeBean) adapterView.getItemAtPosition(position);
                    mNoticeDataManager = new NoticeDataManager(NoticeActivity.this);
                    mNoticeDataManager.openDataBase();
                    mNoticeDataManager.deleteNoticeDataById(mNoticeBean.getId());
                    mNoticeDataManager.closeDataBase();
                    onRestart();
                }
            });
            //    设置一个NegativeButton
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    onRestart();
                }
            });
            builder.show();

            return true;
        }
    };


    private class MyBaseAdapter extends BaseAdapter {
        private List<NoticeBean> noticeBeans;
        public MyBaseAdapter(List<NoticeBean> noticeBeans) {
            this.noticeBeans=noticeBeans;
        }

        @Override
        public int getCount() {
            return noticeBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return noticeBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            View view = View.inflate(NoticeActivity.this,R.layout.item_notice,null);
            TextView noticeTitle = view.findViewById(R.id.notice_title_textview);
            ImageView noticeIcon = view.findViewById(R.id.notice_type_icon);
            noticeTitle.setText(noticeBeans.get(position).getTitle());
            return view;
        }
    }
}
