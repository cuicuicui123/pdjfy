package com.goodo.pdjfy.util;

/**
 * Created by Cui on 2017/4/12.
 *
 * @Description 处理网页中的图片
 */

public class JavaScriptUtil {
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
}
