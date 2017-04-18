package com.goodo.pdjfy.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Cui on 2016/3/2.
 * json处理工具，用于判断是json数组还是一条json数据
 */
public class JsonHandler {
    public String mResponse;
    public OnJsonHandler mOnJsonHandler;

    public JsonHandler(String response, OnJsonHandler onJsonHandler) {
        this.mResponse = response;
        this.mOnJsonHandler = onJsonHandler;
    }

    public void handler(){
        try {
            JSONObject jsonObject = new JSONObject(mResponse);
            JSONObject Goodo = jsonObject.getJSONObject("Goodo");
            if (!Goodo.isNull("R")) {
                String RStr = Goodo.getString("R");
                char[] chars = RStr.toCharArray();
                if(chars[0] == '['){
                    JSONArray RArray = Goodo.getJSONArray("R");
                    int iSize = RArray.length();
                    for(int i = 0;i < iSize;i ++){
                        JSONObject jo = RArray.getJSONObject(i);
                        mOnJsonHandler.handler(jo);
                    }
                }else{
                    JSONObject jo = Goodo.getJSONObject("R");
                    mOnJsonHandler.handler(jo);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public interface OnJsonHandler{
        void handler(JSONObject jsonObject) throws JSONException;
    }

}
