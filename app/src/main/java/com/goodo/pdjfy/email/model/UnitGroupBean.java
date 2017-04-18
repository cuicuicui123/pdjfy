package com.goodo.pdjfy.email.model;

import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cui on 2016/3/18.
 * 单位群组信息bean，
 */
public class UnitGroupBean {
    private int mID;
    private String mName;
    private List<UnitGroupBean> mList;//存储下一级的信息
    private int mLayer;//代表当前层数，设置偏移量
    private List<ImageView> mOpenIvList;//存储当前代表是否展开的imageView以及下级的imageView
    private boolean mIsSelect;//是否选择
    private UnitGroupBean mParentObject;
    private ImageView mAddIv;

    public UnitGroupBean() {
        mList = new ArrayList<>();
        mOpenIvList = new ArrayList<>();
    }

    public ImageView getAddIv() {
        return mAddIv;
    }

    public void setAddIv(ImageView addIv) {
        this.mAddIv = addIv;
    }

    public UnitGroupBean getParentObject() {
        return mParentObject;
    }

    public void setParentObject(UnitGroupBean parentObject) {
        this.mParentObject = parentObject;
    }

    public List<ImageView> getOpenIvList() {
        return mOpenIvList;
    }

    public void setOpenIvList(List<ImageView> openIvList) {
        this.mOpenIvList = openIvList;
    }

    public boolean isSelect() {
        return mIsSelect;
    }

    public void setSelect(boolean select) {
        if(this.mIsSelect != select){
            for(int i = 0; i < mList.size(); i ++){
                UnitGroupBean object = mList.get(i);
                object.setSelect(select);
            }
            for(int i = 0; i < mOpenIvList.size(); i ++){
                ImageView iv = mOpenIvList.get(i);
                if(select){
                    iv.setVisibility(View.VISIBLE);
                }else{
                    iv.setVisibility(View.GONE);
                }
            }
        }
        this.mIsSelect = select;
    }

    public void setStateSingle(boolean state){
        if(mAddIv != null){
            if(this.mIsSelect != state){
                if(state){
                    mAddIv.setVisibility(View.VISIBLE);
                }else{
                    mAddIv.setVisibility(View.GONE);
                }
            }
            if(mParentObject != null){
                mParentObject.setStateSingle(false);
            }
            this.mIsSelect = state;
        }
    }

    public int getLayer() {
        return mLayer;
    }

    public void setLayer(int layer) {
        this.mLayer = layer;
    }

    public List<UnitGroupBean> getList() {
        return mList;
    }

    public void setList(List<UnitGroupBean> list) {
        this.mList = list;
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        this.mID = ID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
