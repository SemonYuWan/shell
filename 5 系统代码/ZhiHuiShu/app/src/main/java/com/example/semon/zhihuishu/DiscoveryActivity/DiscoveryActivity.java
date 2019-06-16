package com.example.semon.zhihuishu.DiscoveryActivity;

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
import com.example.semon.zhihuishu.Constant.DiscoveryData;
import com.example.semon.zhihuishu.Database.DiscoveryDataManager;
import com.example.semon.zhihuishu.HeadmasterActivity.HeadmasterMainActivity;
import com.example.semon.zhihuishu.MessageActivity.MessageActivity;
import com.example.semon.zhihuishu.MineActivity.MineActivity;
import com.example.semon.zhihuishu.ParentActivity.ParentMainActivity;
import com.example.semon.zhihuishu.R;
import com.example.semon.zhihuishu.TeacherActivity.TeacherMainActivity;

import java.io.File;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS;

public class DiscoveryActivity extends AppCompatActivity {

    private Context context;
    private SharedPreferences userSession;
    private String user_id, user_name, password, user_type;
    private FloatingActionButton addDiscoveryBtn;
    private ListView discoveryListView;
    private List<DiscoveryData> discoveryDataList;
    private DiscoveryDataManager mDiscoveryDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);
        context = DiscoveryActivity.this;

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
        mDiscoveryDataManager = new DiscoveryDataManager(this);
        mDiscoveryDataManager.openDataBase();
        discoveryDataList = mDiscoveryDataManager.findAllDiscovery();
        discoveryListView = findViewById(R.id.discovery_listview);
        MyBaseAdapter mAdapter = new MyBaseAdapter(discoveryDataList);
        discoveryListView.setAdapter(mAdapter);

        discoveryListView.setOnItemClickListener(itemOnClick);
        discoveryListView.setOnItemLongClickListener(itemOnLongClick);

        addDiscoveryBtn = findViewById(R.id.add_discovery_view_button);
        addDiscoveryBtn.bringToFront();
        addDiscoveryBtn.setOnClickListener(addDiscoveryOnClick);

        if(user_type.equals("parent")){
            addDiscoveryBtn.setVisibility(View.GONE);
        }
        else{
            discoveryListView.setOnItemLongClickListener(itemOnLongClick);
        }

    }

    //点击事件
    View.OnClickListener addDiscoveryOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.add_discovery_view_button:
                    Log.i("TAG", "添加发现文章");
                    Intent addIntent = new Intent(DiscoveryActivity.this, AddDiscoveryActivity
                            .class);
                    startActivity(addIntent);
                    break;
            }
        }
    };

    private class MyBaseAdapter extends BaseAdapter {
        private List<DiscoveryData> mDiscoveryDataList;

        public MyBaseAdapter(List<DiscoveryData> mDiscoveryDataList) {
            this.mDiscoveryDataList = mDiscoveryDataList;
        }

        @Override
        public int getCount() {
            return mDiscoveryDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mDiscoveryDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            View view = View.inflate(DiscoveryActivity.this, R.layout.item_discovery, null);
            TextView discoveryTitle = view.findViewById(R.id.discovery_title_textview);
            ImageView discoveryImage = view.findViewById(R.id.discovery_image);
            TextView discoveryCreateTime = view.findViewById(R.id.discovery_createtime_textview);
            TextView discoveryLike = view.findViewById(R.id.discovery_like_textview);
            discoveryTitle.setText(mDiscoveryDataList.get(position).getTitle());
            discoveryImage.setImageURI(Uri.fromFile(new File(mDiscoveryDataList.get(position).getImage())));
            discoveryCreateTime = view.findViewById(R.id.discovery_createtime_textview);
            discoveryLike = view.findViewById(R.id.discovery_like_textview);
            return view;
        }
    }

    //item点击事件
    AdapterView.OnItemClickListener itemOnClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            DiscoveryData mDiscovery = (DiscoveryData) adapterView.getItemAtPosition(position);
            Log.i("ItemClick", "点击了" + mDiscovery.getId());
            Intent itemDiscoveryIntent = new Intent(DiscoveryActivity.this, ViewDiscoveryActivity
                    .class);
            itemDiscoveryIntent.putExtra("discoveryId", mDiscovery.getId());
            startActivity(itemDiscoveryIntent);
        }
    };

    AdapterView.OnItemLongClickListener itemOnLongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long l) {
            //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
            AlertDialog.Builder builder = new AlertDialog.Builder(DiscoveryActivity.this);
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
                    DiscoveryData mDiscoveryData = (DiscoveryData) adapterView.getItemAtPosition(position);
                    mDiscoveryDataManager = new DiscoveryDataManager(DiscoveryActivity.this);
                    mDiscoveryDataManager.openDataBase();
                    mDiscoveryDataManager.deleteDiscoveryDataById(mDiscoveryData.getId());
                    mDiscoveryDataManager.closeDataBase();
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

    private void initMenu() {
        LinearLayout menuFirst;//学生考勤
        LinearLayout menuDiscovery;//家长管理
        LinearLayout menuCircle;//学生作品
        LinearLayout menuMessage;//学生点评
        LinearLayout menuMine;//亲子任务

        TextView menu_textview;

        menuFirst = findViewById(R.id.menu_first);
        menuDiscovery = findViewById(R.id.menu_discovery);
        menuCircle = findViewById(R.id.menu_cicle);
        menuMessage = findViewById(R.id.menu_message);
        menuMine = findViewById(R.id.menu_mine);

        menu_textview = findViewById(R.id.menu_textView2);
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
                    if (user_type.equals("parent")) {
                        menuIntent = new Intent(DiscoveryActivity.this, ParentMainActivity.class);
                    } else if (user_type.equals("teacher")) {
                        menuIntent = new Intent(DiscoveryActivity.this, TeacherMainActivity.class);
                    } else if (user_type.equals("headmaster")) {
                        menuIntent = new Intent(DiscoveryActivity.this,HeadmasterMainActivity.class);
                    }
                    menuIntent.addFlags(FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(menuIntent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.menu_discovery:
                    onRestart();
                    break;
                case R.id.menu_cicle:
                    menuIntent = new Intent(context, CircleActivity.class);
                    menuIntent.addFlags(FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(menuIntent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.menu_message:
                    menuIntent = new Intent(context, MessageActivity.class);
                    menuIntent.addFlags(FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(menuIntent);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.menu_mine:
                    menuIntent = new Intent(context, MineActivity.class);
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
            Toast.makeText(DiscoveryActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
