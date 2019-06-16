package com.example.semon.zhihuishu.TeacherActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.semon.zhihuishu.Constant.OpusData;
import com.example.semon.zhihuishu.Database.HeadmasterDataManager;
import com.example.semon.zhihuishu.Database.OpusDataManager;
import com.example.semon.zhihuishu.Database.ParentDataManager;
import com.example.semon.zhihuishu.Database.TeacherDataManager;
import com.example.semon.zhihuishu.R;

import java.io.File;
import java.util.List;

/**
 * 学生作品
 */
public class ChildOpusActivity extends AppCompatActivity {

    private SharedPreferences userSession;
    private String user_id, user_name, password, user_type;
    private RecyclerView mRecyclerView;
    private OpusDataManager mOpusDataManager;
    private ParentDataManager mParentDataManager;
    private TeacherDataManager mTeacherDataManager;
    private HeadmasterDataManager mHeadmasterDataManager;
    private FloatingActionButton addOpusBtn;
    private ListView opusListView;
    private List<OpusData> opusDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_opus);


        initData();

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

        userSession = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        user_id = userSession.getString("USER_ID", null);//(key,若无数据需要赋的值)
        user_name = userSession.getString("USER_NAME", null);
        password = userSession.getString("PASSWORD", null);
        user_type = userSession.getString("USER_TYPE", null);

        String classId = null;
        if (user_type.equals("parent")) {
            mParentDataManager = new ParentDataManager(this);
            mParentDataManager.openDataBase();
            classId = mParentDataManager.findChildClassIdByParentId(user_id);
            mParentDataManager.closeDataBase();
        } else if (user_type.equals("teacher")) {
            mTeacherDataManager = new TeacherDataManager(this);
            mTeacherDataManager.openDataBase();
            classId = mTeacherDataManager.findClassByTeacherId(user_id);
            mTeacherDataManager.closeDataBase();
        } else if (user_type.equals("headmaster")) {
            mHeadmasterDataManager = new HeadmasterDataManager(this);
            mHeadmasterDataManager.openDataBase();
            //            classId = mHeadmasterDataManager
            mHeadmasterDataManager.closeDataBase();
        }


        mOpusDataManager = new OpusDataManager(this);
        mOpusDataManager.openDataBase();
        opusDataList = mOpusDataManager.findAllOpusByClass(classId);
        mOpusDataManager.closeDataBase();

        opusListView = findViewById(R.id.opus_listview);
        MyBaseAdapter mAdapter = new MyBaseAdapter(opusDataList);
        opusListView.setAdapter(mAdapter);

        opusListView.setOnItemClickListener(itemOnClick);

        addOpusBtn = findViewById(R.id.add_opus_view_button);
        addOpusBtn.bringToFront();
        addOpusBtn.setOnClickListener(addDiscoveryOnClick);

        if(user_type.equals("parent")){
            addOpusBtn.setVisibility(View.GONE);
        }else {
            opusListView.setOnItemLongClickListener(itemOnLongClick);
        }
    }

    //点击事件
    View.OnClickListener addDiscoveryOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.add_opus_view_button:
                    Log.i("TAG", "添加发现文章");
                    Intent addIntent = new Intent(ChildOpusActivity.this, AddOpusActivity.class);
                    startActivity(addIntent);
                    break;
            }
        }
    };

    AdapterView.OnItemLongClickListener itemOnLongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long l) {
            //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
            AlertDialog.Builder builder = new AlertDialog.Builder(ChildOpusActivity.this);
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
                    OpusData mOpusData = (OpusData) adapterView.getItemAtPosition(position);
                    mOpusDataManager = new OpusDataManager(ChildOpusActivity.this);
                    mOpusDataManager.openDataBase();
                    mOpusDataManager.deleteOpusDataById(mOpusData.getId());
                    mOpusDataManager.closeDataBase();
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
        private List<OpusData> mOpusDataList;

        public MyBaseAdapter(List<OpusData> mOpusDataList) {
            this.mOpusDataList = mOpusDataList;
        }

        @Override
        public int getCount() {
            return mOpusDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mOpusDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {

            View view = View.inflate(ChildOpusActivity.this, R.layout.item_opus, null);
            TextView opusTitle = view.findViewById(R.id.opus_title_textview);
            ImageView opusImage = view.findViewById(R.id.opus_image);
            TextView opusCreateTime = view.findViewById(R.id.opus_createtime_textview);
            TextView opusLike = view.findViewById(R.id.opus_like_textview);

            opusTitle.setText(mOpusDataList.get(position).getOpus_title());
            opusImage.setImageURI(Uri.fromFile(new File(mOpusDataList.get(position).getOpusImage())));
            opusCreateTime = view.findViewById(R.id.opus_createtime_textview);
            opusLike = view.findViewById(R.id.opus_like_textview);
            return view;
        }
    }

    //item点击事件
    AdapterView.OnItemClickListener itemOnClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            OpusData mDiscovery = (OpusData) adapterView.getItemAtPosition(position);
            Log.i("ItemClick", "点击了" + mDiscovery.getId());
            Intent itemDiscoveryIntent = new Intent(ChildOpusActivity.this, ViewOpusActivity.class);
            itemDiscoveryIntent.putExtra("opusId", mDiscovery.getId());
            startActivity(itemDiscoveryIntent);
        }
    };

}
