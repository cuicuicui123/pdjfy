package com.goodo.app.document.presenter;

import com.goodo.app.base.BaseFragment;
import com.goodo.app.document.model.ReceiveDocumentListBean;
import com.goodo.app.document.model.SendDocumentListBean;
import com.goodo.app.document.view.DocumentView;
import com.goodo.app.rxjava.CacheSubscriber;
import com.goodo.app.rxjava.HttpMethods;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cui on 2017/4/21.
 *
 * @Description
 */

public class DocumentPresenterImpl implements DocumentPresenter {
    private DocumentView mDocumentView;
    private BaseFragment mFragment;
    private HttpMethods mHttpMethods;

    private String KEY_RECEIVE_DOCUMENT_LIST = "getReceiveDocumentList";
    private String KEY_SEND_DOCUMENT_LIST = "";

    private List<ReceiveDocumentListBean> mReceiveBeanList;
    private List<SendDocumentListBean> mSendBeanList;

    public DocumentPresenterImpl(DocumentView documentView, BaseFragment fragment) {
        mDocumentView = documentView;
        mFragment = fragment;
        mHttpMethods = HttpMethods.getInstance();
        mReceiveBeanList = new ArrayList<>();
        mSendBeanList = new ArrayList<>();
    }


    @Override
    public void getReceiveDocumentList() {
        CacheSubscriber cacheSubscriber = new CacheSubscriber(KEY_RECEIVE_DOCUMENT_LIST) {
            @Override
            protected void getCache(String cacheData) {
                handleReceiveResponse(cacheData);
            }

            @Override
            protected void onResponse(String response) {
                handleReceiveResponse(response);
            }
        };
        mHttpMethods.getReceiveDocumentList(cacheSubscriber);
    }

    private void handleReceiveResponse(String response){
        mReceiveBeanList.clear();
        StringReader reader = new StringReader(response);
        InputSource source = new InputSource(reader);
        SAXBuilder builder = new SAXBuilder();
        try {
            Document doc = builder.build(source);
            Element root = doc.getRootElement();
            List<Element> list = root.getChildren();

            for (Element element : list) {
                ReceiveDocumentListBean bean = new ReceiveDocumentListBean();
                bean.setTitle(element.getAttributeValue("Title"));
                bean.setReceiveDate(element.getAttributeValue("ReceiveDate"));
                bean.setUserName(element.getAttributeValue("UserName"));
                bean.setReceiveId(Integer.parseInt(element.getAttributeValue("Receive_ID")));
                mReceiveBeanList.add(bean);
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getSendDocumentList();

    }

    @Override
    public void getSendDocumentList() {
        CacheSubscriber cacheSubscriber = new CacheSubscriber(KEY_SEND_DOCUMENT_LIST) {
            @Override
            protected void getCache(String cacheData) {
                handleSendResponse(cacheData);
            }

            @Override
            protected void onResponse(String response) {
                handleSendResponse(response);
            }
        };
        mHttpMethods.getSendDocumentList(cacheSubscriber);
    }

    private void handleSendResponse(String response){
        mSendBeanList.clear();
        StringReader reader = new StringReader(response);
        InputSource source = new InputSource(reader);
        SAXBuilder builder = new SAXBuilder();
        try {
            Document doc = builder.build(source);
            Element root = doc.getRootElement();
            List<Element> list = root.getChildren();

            for (Element element : list) {
                SendDocumentListBean bean = new SendDocumentListBean();
                bean.setTitle(element.getAttributeValue("Title"));
                bean.setSendDate(element.getAttributeValue("SendDate"));
                bean.setSendId(Integer.parseInt(element.getAttributeValue("Send_ID")));
                mSendBeanList.add(bean);
            }
            mDocumentView.getDocumentList(mReceiveBeanList, mSendBeanList);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
