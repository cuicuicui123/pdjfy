package com.goodo.pdjfy.email.presenter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.AppContext;
import com.goodo.pdjfy.email.model.UnitBean;
import com.goodo.pdjfy.email.model.UnitUserBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
    private List<UnitBean> mUnitBeanList;
    private List<List<UnitUserBean>> mUnitUserBeanLists;
    private AppContext mAppContext;
    private LayoutInflater mInflater;

    public ExpandableListViewAdapter(List<UnitBean> unitBeanList, List<List<UnitUserBean>> unitUserBeanLists) {
        mUnitBeanList = unitBeanList;
        mUnitUserBeanLists = unitUserBeanLists;
        mAppContext = AppContext.getInstance();
        mInflater = LayoutInflater.from(mAppContext);
    }

    @Override
    public int getGroupCount() {
        return mUnitBeanList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mUnitUserBeanLists.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return mUnitBeanList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mUnitUserBeanLists.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int i, boolean b, View view, ViewGroup viewGroup) {
        ParentViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ParentViewHolder();
            view = mInflater.inflate(R.layout.expandable_select_parent, viewGroup, false);
            viewHolder.mTextView = (TextView) view.findViewById(R.id.tv);
            viewHolder.mSelectIv = (ImageView) view.findViewById(R.id.iv_select);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ParentViewHolder) view.getTag();
        }
        final UnitBean unitBean = mUnitBeanList.get(i);
        viewHolder.mTextView.setText(unitBean.getNAME());
        viewHolder.mSelectIv.setSelected(unitBean.isSelected());
        viewHolder.mSelectIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean state = !unitBean.isSelected();
                unitBean.setSelected(state);
                for (UnitUserBean unitUserBean : mUnitUserBeanLists.get(i)) {
                    unitUserBean.setSelected(state);
                }
                notifyDataSetChanged();
            }
        });
        return view;
    }

    class ParentViewHolder{
        TextView mTextView;
        ImageView mSelectIv;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ChildViewHolder();
            view = mInflater.inflate(R.layout.expandable_select_child, viewGroup, false);
            viewHolder.mTextView = (TextView) view.findViewById(R.id.tv);
            viewHolder.mSelectIv = (ImageView) view.findViewById(R.id.iv_select);
            viewHolder.mRl = (RelativeLayout) view.findViewById(R.id.rl);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ChildViewHolder) view.getTag();
        }
        final UnitBean unitBean = mUnitBeanList.get(i);
        final UnitUserBean unitUserBean = mUnitUserBeanLists.get(i).get(i1);
        viewHolder.mTextView.setText(unitUserBean.getUserName());
        viewHolder.mSelectIv.setSelected(unitUserBean.isSelected());
        viewHolder.mRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean state = !unitUserBean.isSelected();
                unitUserBean.setSelected(state);
                if (unitBean.isSelected() && !state) {
                    unitBean.setSelected(false);
                }
                notifyDataSetChanged();
            }
        });
        return view;
    }

    class ChildViewHolder{
        TextView mTextView;
        ImageView mSelectIv;
        RelativeLayout mRl;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
