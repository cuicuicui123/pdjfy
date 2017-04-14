package com.goodo.pdjfy.util;

import android.webkit.MimeTypeMap;

import com.goodo.pdjfy.R;

import java.text.SimpleDateFormat;

/**
 * Created by Cui on 2017/4/12.
 *
 * @Description 用于存储公共变量和提供公共方法
 */

public class MyConfig {
    public static String KEY_TITLE = "title";
    public static String KEY_POSITION = "position";
    public static String KEY_CONTENT_ID = "contentId";
    public static String TOP_LIST_DETAIL_TITLE = "首页新闻";
    public static String KEY_ID = "id";
    public static String NOON_TIME = "12:00";
    public static final int SCHEDULE_TYPE_PERSON = 3;
    public static final int SCHEDULE_TYPE_DEPART = 2;
    public static final int SCHEDULE_TYPE_COLLEGE = 1;
    public static String KEY_LIST = "list";


    public static int USER_ID;
    public static int UNIT_ID;
    public static String SESSION_ID;

    public static int READ_STORAGE_CODE = 1;

    public static int[] FILE_PICTURE = new int[]{R.drawable.pic_file_word, R.drawable.pic_file_txt,
            R.drawable.pic_file_excel, R.drawable.pic_file_ppt, R.drawable.pic_file_rar, R.drawable.pic_file_video,
            R.drawable.pic_file_pic, R.drawable.pic_file_no};

    public static void setUserId(int userId) {
        USER_ID = userId;
    }

    public static void setUnitId(int unitId) {
        UNIT_ID = unitId;
    }

    public static void setSessionId(String sessionId) {
        SESSION_ID = sessionId;
    }

    /**
     * 根据文件名获取对应的图片
     * @param fileName
     * @return
     */
    public static int getFilePictureByName(String fileName) {
        String extension = getFileExtension(fileName);
        if (extension != null) {
            if (extension.contains("word")) {
                return FILE_PICTURE[0];
            } else if (extension.contains("image")) {
                return FILE_PICTURE[6];
            } else if (extension.contains("video")) {
                return FILE_PICTURE[5];
            } else if (extension.contains("excel")) {
                return FILE_PICTURE[2];
            } else if (extension.contains("powerpoint")) {
                return FILE_PICTURE[3];
            } else if (extension.contains("text")) {
                return FILE_PICTURE[1];
            } else if (extension.contains("rar") || extension.contains("zip")) {
                return FILE_PICTURE[4];
            } else {
                return FILE_PICTURE[7];
            }
        }
        return FILE_PICTURE[7];
    }

    /**
     * 获取文件类型
     * @return
     */
    public static String getFileExtension(String fileName) {
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String postfix = fileName.substring(fileName.indexOf(".") + 1).toLowerCase();
        return mimeTypeMap.getMimeTypeFromExtension(postfix);
    }

    /**
     * 处理网页中的图片
     * @param html
     * @param width
     * @return
     */
    public static String handleImage(String html, int width){
        return "<script type=\"text/javascript\">\n" +
                "    window.onload = function ResizeImages(){\n" +
                "\tvar myimg, oldwidth;\n" +
                "\tvar maxwidth = " + width +";" +
                "        for(i = 0;i < document.images.length;i ++){\n" +
                "\t    myimg = document.images[i];\n" +
                "\t    if(myimg.width > maxwidth){\n" +
                "                oldwidth = myimg.width;\n" +
                "\t\tmyimg.width = maxwidth;\n" +
                "\t    }\n" +
                "\t}\n" +
                "    }\n" +
                "</script>"  + html;
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }

    /**
     * 获得DateFormat
     */
    public static SimpleDateFormat getDateFormat(){
        return new SimpleDateFormat("yyyy-MM-dd");
    }

}
