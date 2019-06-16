package com.example.semon.zhihuishu.Circle;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.example.semon.zhihuishu.Constant.CircleData;
import com.example.semon.zhihuishu.R;

import java.util.List;

/**
 * Des	      ${展开折叠文本适配器}
 */
public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.MyViewHolder> {
    private Activity mContent;

    private final int MAX_LINE_COUNT = 3;//最大显示行数

    private final int STATE_UNKNOW = -1;//未知状态

    private final int STATE_NOT_OVERFLOW = 1;//文本行数小于最大可显示行数

    private final int STATE_COLLAPSED = 2;//折叠状态

    private final int STATE_EXPANDED = 3;//展开状态

    private SharedPreferences userSession;
    private String user_id,user_name,password,user_type;

    /**
     * 注意：保存文本状态集合的key一定要是唯一的，如果用position。
     * 如果使用position作为key，则删除、增加条目的时候会出现显示错乱
     */
    private ArrayMap<String,Integer> mTextStateList;//保存文本状态集合

    List<CircleData> mCircleList;

    public CircleAdapter(List<CircleData> mCircleList, Activity context) {
        mContent = context;
        this.mCircleList = mCircleList;
        mTextStateList = new ArrayMap<>();


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mContent.getLayoutInflater().inflate(R.layout.item_circle, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        int state = -1;
        if (mTextStateList.get(mCircleList.get(position).getId())!=null){
            state = mTextStateList.get(mCircleList.get(position).getId());
        }

        //第一次初始化，未知状态
        if (state == STATE_UNKNOW) {
            holder.content.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    //这个回掉会调用多次，获取完行数后记得注销监听
                    holder.content.getViewTreeObserver().removeOnPreDrawListener(this);
                    //holder.content.getViewTreeObserver().addOnPreDrawListener(null);
                    //如果内容显示的行数大于最大显示行数
                    if (holder.content.getLineCount() > MAX_LINE_COUNT) {
                        holder.content.setMaxLines(MAX_LINE_COUNT);//设置最大显示行数
                        holder.expandOrFold.setVisibility(View.VISIBLE);//显示“全文”
                        holder.expandOrFold.setText("全文");
                        mTextStateList.put(mCircleList.get(position).getId(), STATE_COLLAPSED);//保存状态
                    } else {
                        holder.expandOrFold.setVisibility(View.GONE);
                        mTextStateList.put(mCircleList.get(position).getId(), STATE_NOT_OVERFLOW);
                    }
                    return true;
                }
            });

            holder.content.setMaxLines(Integer.MAX_VALUE);//设置文本的最大行数，为整数的最大数值
            holder.content.setText(mCircleList.get(position).getContent());
            holder.nickname.setText(mCircleList.get(position).getCreater());
        } else {
            //如果之前已经初始化过了，则使用保存的状态。
            switch (state) {
                case STATE_NOT_OVERFLOW:
                    holder.expandOrFold.setVisibility(View.GONE);
                    break;
                case STATE_COLLAPSED:
                    holder.content.setMaxLines(MAX_LINE_COUNT);
                    holder.expandOrFold.setVisibility(View.VISIBLE);
                    holder.expandOrFold.setText("全文");
                    break;
                case STATE_EXPANDED:
                    holder.content.setMaxLines(Integer.MAX_VALUE);
                    holder.expandOrFold.setVisibility(View.VISIBLE);
                    holder.expandOrFold.setText("收起");
                    break;
            }
            holder.content.setText(mCircleList.get(position).getContent());
            holder.nickname.setText(mCircleList.get(position).getCreater());

        }

        //全文和收起的点击事件
        holder.expandOrFold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int state = mTextStateList.put(mCircleList.get(position).getId(), STATE_UNKNOW);
                if (state == STATE_COLLAPSED) {
                    holder.content.setMaxLines(Integer.MAX_VALUE);
                    holder.expandOrFold.setText("收起");
                    mTextStateList.put(mCircleList.get(position).getId(), STATE_EXPANDED);
                } else if (state == STATE_EXPANDED) {
                    holder.content.setMaxLines(MAX_LINE_COUNT);
                    holder.expandOrFold.setText("全文");
                    mTextStateList.put(mCircleList.get(position).getId(), STATE_COLLAPSED);
                }
            }
        });

        //删除点击事件
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCircleList.remove(position);
                notifyDataSetChanged();//更新listview组件
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCircleList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nickname;
        public TextView content;
        public TextView delete;
        public TextView expandOrFold;

        public MyViewHolder(View itemView) {
            super(itemView);



            nickname = itemView.findViewById(R.id.tv_nickname);
            content = itemView.findViewById(R.id.tv_content);
            delete = itemView.findViewById(R.id.tv_delete);
            expandOrFold = itemView.findViewById(R.id.tv_expand_or_fold);

        }
    }
}
