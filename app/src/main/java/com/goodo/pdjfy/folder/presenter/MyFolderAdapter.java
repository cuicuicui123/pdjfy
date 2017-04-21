package com.goodo.pdjfy.folder.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.AppContext;
import com.goodo.pdjfy.folder.model.MyFolderBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Cui on 2016/10/19.
 * 文件夹adapter
 */

public class MyFolderAdapter extends BaseAdapter {
    private List<MyFolderBean> mList;
    private Context mContext;
    private DisplayImageOptions mOptions;

    public MyFolderAdapter(List<MyFolderBean> list, Context context) {
        mList = list;
        mContext = context;
        mOptions  = AppContext.getInstance().getImageOptions();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_item_my_folder, parent, false);
            viewHolder.mIv = (ImageView) convertView.findViewById(R.id.iv);
            viewHolder.mTitleTv = (TextView) convertView.findViewById(R.id.tv_file_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mList.get(position).isPicture()) {
            ImageLoader.getInstance().displayImage("file://" + mList.get(position).getFilePath(),
                    viewHolder.mIv, mOptions);
        } else {
            viewHolder.mIv.setImageResource(mList.get(position).getIconResId());
        }
        viewHolder.mTitleTv.setText(mList.get(position).getFileName());
        return convertView;
    }

    static class ViewHolder{
        TextView mTitleTv;
        ImageView mIv;
    }
}
