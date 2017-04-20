package com.goodo.pdjfy.util;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Cui on 2017/4/20.
 *
 * @Description
 */

public class FileUtil {
    public static Map<String, String> getFileBase64Map(List<String> fileList){
        String mFileName = "";
        String mBase64Data = "";
        for(int i = 0;i < fileList.size();i ++){
            String file = fileList.get(i);
            String[] files = file.split("/");
            int length = files.length;
            String fileName = files[length - 1];
            String base64str = null;
            try {
                File f = new File(file);
                FileInputStream fin = new FileInputStream(f);
                byte[] buff = new byte[(int) f.length()];
                fin.read(buff);
                fin.close();
                byte[] encode = Base64.encode(buff, Base64.DEFAULT);
                base64str = new String(encode, "utf-8");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(i == 0){
                mFileName = fileName;
                mBase64Data = base64str;
            }else{
                mFileName = mFileName + "," + fileName;
                mBase64Data = mBase64Data + "," + base64str;
            }
        }
        Map<String, String> map = new HashMap<>();
        map.put(MyConfig.KEY_FILE_NAME, mFileName);
        map.put(MyConfig.KEY_BASE64DATA, mBase64Data);
        return map;
    }
}
