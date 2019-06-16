package com.example.semon.zhihuishu.MessageActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.semon.zhihuishu.Circle.CircleActivity;
import com.example.semon.zhihuishu.Constant.MessageData;
import com.example.semon.zhihuishu.Database.MessageDataManager;
import com.example.semon.zhihuishu.DiscoveryActivity.DiscoveryActivity;
import com.example.semon.zhihuishu.HeadmasterActivity.HeadmasterMainActivity;
import com.example.semon.zhihuishu.MineActivity.MineActivity;
import com.example.semon.zhihuishu.ParentActivity.ParentMainActivity;
import com.example.semon.zhihuishu.R;
import com.example.semon.zhihuishu.TeacherActivity.TeacherMainActivity;

import java.io.File;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS;

public class MessageActivity extends AppCompatActivity {

    private Context context;
    private SharedPreferences userSession;
    private String user_id,user_name,password,user_type;
    private FloatingActionButton addMessageBtn;
    private ListView messageListView;
    private List<MessageData> messageDataList;
    private MessageDataManager mMessageDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        context = MessageActivity.this;

        userSession = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        user_id = userSession.getString("USER_ID", null);//(key,若无数据需要赋的值)
        user_name = userSession.getString("USER_NAME", null);
        password = userSession.getString("PASSWORD", null);
        user_type = userSession.getString("USER_TYPE", null);

        initData();
        initMenu();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    //初始化数据
    private void initData() {
        mMessageDataManager = new MessageDataManager(this);
        mMessageDataManager.openDataBase();
        messageDataList = mMessageDataManager.findAllMessage();
        messageListView = findViewById(R.id.message_listview);
        MyBaseAdapter mAdapter = new MyBaseAdapter(messageDataList);
        messageListView.setAdapter(mAdapter);

        messageListView.setOnItemClickListener(itemOnClick);
        messageListView.setOnItemLongClickListener(itemOnLongClick);


        addMessageBtn = findViewById(R.id.add_message_view_button);
        addMessageBtn.bringToFront();
        addMessageBtn.setOnClickListener(addMessageOnClick);

        if(user_type.equals("parent")){
            addMessageBtn.setVisibility(View.GONE);
        }
        else{
            messageListView.setOnItemLongClickListener(itemOnLongClick);

        }

    }

    //点击事件
    View.OnClickListener addMessageOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.add_message_view_button:
                    Log.i("TAG","添加消息文章");
                    Intent addIntent = new Intent(MessageActivity.this,AddMessageActivity.class);
                    startActivity(addIntent);
                    break;
            }
        }
    };

    AdapterView.OnItemLongClickListener itemOnLongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long l) {
            //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
            AlertDialog.Builder builder = new AlertDialog.Builder(MessageActivity.this);
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
                    MessageData mMessageData = (MessageData) adapterView.getItemAtPosition(position);
                    mMessageDataManager = new MessageDataManager(MessageActivity.this);
                    mMessageDataManager.openDataBase();
                    mMessageDataManager.deleteMessageDataById(mMessageData.getId());
                    mMessageDataManager.closeDataBase();
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
        private List<MessageData> mMessageDataList;
        public MyBaseAdapter(List<MessageData> mMessageDataList) {
            this.mMessageDataList=mMessageDataList;
        }

        @Override
        public int getCount() {
            return mMessageDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mMessageDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            View view = View.inflate(MessageActivity.this,R.layout.item_message,null);
            TextView messageTitle = view.findViewById(R.id.message_title_textview);
            ImageView messageImage = view.findViewById(R.id.message_image);
            TextView messageCreateTime = view.findViewById(R.id.message_createtime_textview);
            TextView messageLike = view.findViewById(R.id.message_like_textview);
            messageTitle.setText(mMessageDataList.get(position).getTitle());
            messageImage.setImageURI(Uri.fromFile(new File(mMessageDataList.get(position).getImage())));
            messageCreateTime = view.findViewById(R.id.message_createtime_textview);
            messageLike = view.findViewById(R.id.message_like_textview);
            return view;
        }
    }

    //item点击事件
    AdapterView.OnItemClickListener itemOnClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            MessageData mMessage = (MessageData)adapterView.getItemAtPosition(position);
            Log.i("ItemClick","点击了"+mMessage.getId());
            Intent itemMessageIntent = new Intent(MessageActivity.this,ViewMessageActivity.class);
            itemMessageIntent.putExtra("messageId", mMessage.getId());
            startActivity(itemMessageIntent);
        }
    };

    private void initMenu() {
        LinearLayout menuFirst;//
        LinearLayout menuDiscovery;//
        LinearLayout menuCircle;//
        LinearLayout menuMessage;//
        LinearLayout menuMine;

        menuFirst = findViewById(R.id.menu_first);
        menuDiscovery = findViewById(R.id.menu_discovery);
        menuCircle = findViewById(R.id.menu_cicle);
        menuMessage = findViewById(R.id.menu_message);
        menuMine = findViewById(R.id.menu_mine);

        TextView menu_textview;
        menu_textview = findViewById(R.id.menu_textView4);
        menu_textview.setTextColor(Color.parseColor("#FF00CF30"));
        menu_textview.setTextSize(16);

        menuFirst.setOnClickListener(menuListener);
        menuDiscovery.setOnClickListener(menuListener);
        menuCircle.setOnClickListener(menuListener);
        menuMessage.setOnClickListener(menuListener);
        menuMine.setOnClickListener(menuListener);

    }

    View.OnClickListener menuListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent menuIntent = null;
            switch (view.getId()) {
                case R.id.menu_first:
                    if (user_type.equals("parent")){
                        menuIntent = new Intent(MessageActivity.this,ParentMainActivity.class);
                    }else if(user_type.equals("teacher")){
                        menuIntent = new Intent(MessageActivity.this,TeacherMainActivity.class);
                    }else if(user_type.equals("headmaster")){
                        menuIntent = new Intent(MessageActivity.this,HeadmasterMainActivity.class);
                    }
                    menuIntent.addFlags(FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(menuIntent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.menu_discovery:
                    menuIntent = new Intent(context,DiscoveryActivity.class);
                    menuIntent.addFlags(FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(menuIntent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.menu_cicle:
                    menuIntent = new Intent(context,CircleActivity.class);
                    menuIntent.addFlags(FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(menuIntent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.menu_message:
                    onRestart();
                    break;
                case R.id.menu_mine:
                    menuIntent = new Intent(context,MineActivity.class);
                    menuIntent.addFlags(FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(menuIntent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;

            }
        }
    };

    //退出时的时间
    private long mExitTime;
    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MessageActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
