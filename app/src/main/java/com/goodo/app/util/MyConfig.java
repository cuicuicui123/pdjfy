package com.goodo.app.util;

import android.webkit.MimeTypeMap;

import com.goodo.app.R;

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
    public static String KEY_LIST = "list";
    public static String KEY_SCHEDULE_BEAN = "scheduleBean";
    public static final String KEY_SCHEDULE_DETAIL_BEAN = "scheduleDetailBean";
    public static final String KEY_DIALOG_TITLE = "title";
    public static final String KEY_DIALOG_CONTENT = "content";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";
    public static final String KEY_EMAIL_LIST_BEAN = "emailListBean";
    public static final String KEY_IS_INBOX = "isInBox";
    public static final String KEY_SEND_LIST = "sendList";
    public static final String KEY_NAME = "name";
    public static final String KEY_MAP = "map";
    public static final String KEY_FILE_NAME = "fileName";
    public static final String KEY_BASE64DATA = "base64Data";
    public static final String KEY_OUTER_MAIL_ID = "outerMailId";
    public static final String KEY_EMAIL_DETAIL_BEAN = "emailDetailBean";
    public static final String KEY_EMAIL_ATTACH_LIST = "emailAttachList";
    public static final String KEY_IS_DEL = "isDel";
    public static final int DELETE = 2;
    public static final int DRAFT = 1;
    public static final int IS_READ = 1;
    public static final int UNREAD = 0;


    public static final int SCHEDULE_TYPE_PERSON = 3;
    public static final int SCHEDULE_TYPE_DEPART = 2;
    public static final int SCHEDULE_TYPE_COLLEGE = 1;
    public static final int ADD_SCHEDULE_CODE = 0;
    public static final int EDIT_SCHEDULE_CODE = 1;
    public static final int SCHEDULE_DETAIL_CODE = 2;
    public static final int IS_ALL_DAY = 1;
    public static final int NOT_ALL_DAY = 0;
    public static final int ALL_DAY = 0;
    public static final int MORNING = 1;
    public static final int AFTERNOON = 2;


    public static final int SEL_RECEIVER_CODE = 0;
    public static final int SEL_CC_CODE = 1;
    public static final int SEL_BCC_CODE = 2;
    public static final int KEY_SEL_FILE = 3;
    public static final int DETAIL_CODE = 4;


    public static final String BEGIN_TIME_HOLE_DAY = "06:00";
    public static final String BEGIN_TIME_MORNING = "06:00";
    public static final String BEGIN_TIME_AFTERNOON = "12:00";
    public static final String END_TIME_HOLE_DAY = "18:00";
    public static final String END_TIME_MORNING = "12:00";
    public static final String END_TIME_AFTERNOON = "18:00";

    public static final int IS_INBOX = 1;
    public static final int NOT_INBOX = 0;

    public static int USER_ID;
    public static int UNIT_ID;
    public static String USERNAME;
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

    public static void setUserName(String userName){
        USERNAME = userName;
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
