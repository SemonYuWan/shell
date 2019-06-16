package com.example.semon.zhihuishu.TeacherActivity;

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

import com.example.semon.zhihuishu.Constant.ChildCommentData;
import com.example.semon.zhihuishu.Database.ChildCommentDataManager;
import com.example.semon.zhihuishu.R;

import java.util.List;

public class ChildCommentActivity extends AppCompatActivity {

    private SharedPreferences userSession;
    private String user_id, user_name, password, user_type;

    private ListView childCommentListView;
    private List<ChildCommentData> childCommentDataList;
    private ChildCommentDataManager mChildCommentDataManager;
    private FloatingActionButton addComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_comment);

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

        mChildCommentDataManager = new ChildCommentDataManager(this);
        mChildCommentDataManager.openDataBase();
        childCommentDataList = mChildCommentDataManager.findAllChildCommentByClass();
        mChildCommentDataManager.closeDataBase();

        childCommentListView = findViewById(R.id.child_comment_listview);
        MyBaseAdapter mAdapter = new MyBaseAdapter(childCommentDataList);
        childCommentListView.setAdapter(mAdapter);
        childCommentListView.setOnItemClickListener(itemOnClick);

        addComment = findViewById(R.id.add_child_comment_view_button);
        addComment.bringToFront();
        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG","点击添加按钮");
                Intent addIntent = new Intent(ChildCommentActivity.this,AddChildCommentActivity.class);
                startActivity(addIntent);
            }
        });

        if(user_type.equals("parent")){
            addComment.setVisibility(View.GONE);
        }
        else {
            childCommentListView.setOnItemLongClickListener(itemOnLongClick);

        }

    }


    AdapterView.OnItemClickListener itemOnClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            ChildCommentData mChildCommentData = (ChildCommentData)adapterView.getItemAtPosition(position);
            Log.i("ItemClick","点击了"+mChildCommentData.getId());
            Intent itemDiscoveryIntent = new Intent(ChildCommentActivity.this,ViewChildCommentActivity.class);
            itemDiscoveryIntent.putExtra("childCommentId", mChildCommentData.getId());
            startActivity(itemDiscoveryIntent);
        }
    };

    AdapterView.OnItemLongClickListener itemOnLongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long l) {
            //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
            AlertDialog.Builder builder = new AlertDialog.Builder(ChildCommentActivity.this);
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
                    ChildCommentData mChildCommentData = (ChildCommentData) adapterView.getItemAtPosition(position);
                    mChildCommentDataManager = new ChildCommentDataManager(ChildCommentActivity.this);
                    mChildCommentDataManager.openDataBase();
                    mChildCommentDataManager.deleteChildCommentDataById(mChildCommentData.getId());
                    mChildCommentDataManager.closeDataBase();
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
        private List<ChildCommentData> childCommentDatas;
        public MyBaseAdapter(List<ChildCommentData> childCommentDatas) {
            this.childCommentDatas=childCommentDatas;
        }

        @Override
        public int getCount() {
            return childCommentDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return childCommentDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            View view = View.inflate(ChildCommentActivity.this,R.layout.item_notice,null);
            TextView noticeTitle = view.findViewById(R.id.notice_title_textview);
            ImageView noticeIcon = view.findViewById(R.id.notice_type_icon);
            noticeTitle.setText(childCommentDatas.get(position).getComment_title());
            return view;
        }
    }
}
