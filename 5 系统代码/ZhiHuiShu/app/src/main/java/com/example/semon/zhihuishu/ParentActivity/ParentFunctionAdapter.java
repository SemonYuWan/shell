package com.example.semon.zhihuishu.ParentActivity;


import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.semon.zhihuishu.Circle.CircleActivity;
import com.example.semon.zhihuishu.R;

import java.util.List;

public class ParentFunctionAdapter extends RecyclerView.Adapter {


    private static final String TAG = ParentFunctionAdapter.class.getSimpleName();
    private final Activity mContext;
    private List<ParentFunctionBean> parentFunctionList;


    public ParentFunctionAdapter(List<ParentFunctionBean> parentFunctionList, Activity context) {
        this.mContext = context;
        this.parentFunctionList = parentFunctionList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d(TAG, "onCreateViewHolder, viewType: " + viewType);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_function, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new FuncitonViewHolder(view);
    }


    @NonNull
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        FuncitonViewHolder functionHolder = (FuncitonViewHolder) holder;
        Log.d(TAG, "onBindViewHolder, position: " + position + ", viewHolder: " + functionHolder);
        functionHolder.lFunctionName.setText(parentFunctionList.get(position).getlFunctionName());
//        functionHolder.rFunctionName.setText(parentFunctionList.get(position).getrFunctionName());
        functionHolder.lFunctionIcon.setBackgroundResource(parentFunctionList.get(position).getlFunctionIcon());
//        functionHolder.rFunctionIcon.setBackgroundResource(parentFunctionList.get(position).getrFunctionIcon());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("按键信息", parentFunctionList.get(position).getlFunctionName() + "功能");
//                Toast.makeText(view.getContext(),"",Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 5:
                        Intent intent = new Intent(mContext, CircleActivity.class);
                        mContext.startActivity(intent);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return parentFunctionList.size();
    }


    public class FuncitonViewHolder extends RecyclerView.ViewHolder {
        public TextView lFunctionName;
        public ImageView lFunctionIcon;
//        public TextView rFunctionName;
//        public ImageView rFunctionIcon;

        public FuncitonViewHolder(View view) {
            super(view);
            lFunctionName = view.findViewById(R.id.left_textView);
//            rFunctionName = view.findViewById(R.id.right_textView);
            lFunctionIcon = view.findViewById(R.id.left_imageView);
//            rFunctionIcon = view.findViewById(R.id.right_imageView);
        }


    }


}
