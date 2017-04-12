package com.goodo.pdjfy.util;

import android.webkit.MimeTypeMap;

import com.goodo.pdjfy.R;

/**
 * Created by Cui on 2016/10/19.
 * 根据后缀名获取文件类型图片
 */

public class GetFilePicture {
    private String mFileName;
    public static int[] FILE_PICTURE = new int[]{R.drawable.pic_file_word, R.drawable.pic_file_txt,
            R.drawable.pic_file_excel, R.drawable.pic_file_ppt, R.drawable.pic_file_rar, R.drawable.pic_file_video,
            R.drawable.pic_file_pic, R.drawable.pic_file_no};

    public GetFilePicture(String fileName) {
        this.mFileName = fileName;
    }

    public int getFilePictureByName() {
        String extension = getFileExtension();
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
    public String getFileExtension() {
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String postfix = mFileName.substring(mFileName.indexOf(".") + 1).toLowerCase();
        return mimeTypeMap.getMimeTypeFromExtension(postfix);
    }
}
