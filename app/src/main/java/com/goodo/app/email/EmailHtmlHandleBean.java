package com.goodo.app.email;

import android.webkit.WebView;

import com.goodo.app.email.model.EmailDetailBean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Cui on 2017/1/13.
 * 邮件内容html转换，主要是在html中添加contentEditable标签让html可编辑
 */

public class EmailHtmlHandleBean {
    //转发外部邮件添加contentEditable属性
    public String outEmailTransformHtmlToEditable(String html){
        Pattern pattern = Pattern.compile("<body ");
        Matcher matcher = pattern.matcher(html);
        String newHtml;
        if (matcher.find()) {
            newHtml = html.replace("<body ", "<body contenteditable=\"true\"");
        } else {
            newHtml = innerEmailTransformHtmlToEditable(html);
        }
        return newHtml;
    }

    public String outEmailRemoveContentEditable(String html){
        Pattern pattern = Pattern.compile("<body contenteditable=\"true\"");
        Matcher matcher = pattern.matcher(html);
        String newHtml;
        if (matcher.find()) {
            newHtml = html.replace("<body contenteditable=\"true\"", "<body");
        } else {
            Pattern pattern1 = Pattern.compile(".*<div contenteditable=\"true\">.*");
            matcher = pattern1.matcher(html);
            if (matcher.find()) {
                newHtml = html.replaceFirst("<div contenteditable=\"true\">", "");
                newHtml = newHtml.replaceFirst("</div>", "");
            } else {
                newHtml = html;
            }
        }
        return newHtml;
    }

    public String innerEmailTransformHtmlToEditable(String html){
        return "<div contenteditable=\"true\">" + html + "</div>";
    }

    public String answerAndTransmitHtml(EmailDetailBean emailDetailBean){
        return answerAndTransmitHtml(emailDetailBean.getSendUserName(), emailDetailBean.getDate(), emailDetailBean.getTo(), emailDetailBean.getSubject(), emailDetailBean.getBody());
    }

    public void pushWebViewHtmlToObject(WebView webView){
        webView.loadUrl("javascript:window.java_obj.getSource(" +
                "document.documentElement.innerHTML);");
    }

    public String answerAndTransmitHtml(String sender, String date, String to, String subject, String body){
        String newHtml ="</br>" +
                "</br>" +
                "</br>" +
                "<div style=\"font:Verdana normal 14px;color:#000;\">" +
                "<div style=\"FONT-SIZE: 12px;FONT-FAMILY: Arial Narrow;padding:2px 0 2px 0;\">" +
                "------------------&nbsp;Original&nbsp;------------------" +
                "</div>" +
                "<div style=\"FONT-SIZE: 12px;background:#efefef;padding:8px;\">" +
                "<div id=\"menu_sender\">" +
                "<b>From: </b>" +
                "&nbsp; " + sender +
                "</div>" +
                "<div>" +
                "<b>Date: </b>" +
                "&nbsp;" + date +
                "</div>" +
                "<div>" +
                "<b>To: </b>" +
                "&nbsp; " + to +
                "<wbr>" +
                "</div>" +
                "<div></div>" +
                "<div>" +
                "<b>Subject: </b>" +
                "&nbsp;" + subject +
                "</div>" +
                "</div>" +
                "<div>&nbsp;</div>\n" +
                "&nbsp; " + body;
        newHtml = outEmailTransformHtmlToEditable(newHtml);
        return newHtml;
    }


}
