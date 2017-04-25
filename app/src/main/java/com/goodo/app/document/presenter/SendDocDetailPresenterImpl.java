package com.goodo.app.document.presenter;

import com.goodo.app.base.BaseActivity;
import com.goodo.app.document.model.DocDetailBean;
import com.goodo.app.document.view.DetailView;
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

public class SendDocDetailPresenterImpl implements DetailPresenter {
    private DetailView mDetailView;
    private HttpMethods mHttpMethods;
    private BaseActivity mActivity;
    private int mId;

    private String KEY = "getSendDocDetail";

    public SendDocDetailPresenterImpl(DetailView detailView, BaseActivity activity, int id) {
        mDetailView = detailView;
        mActivity = activity;
        mHttpMethods = HttpMethods.getInstance();
        mId = id;
    }

    @Override
    public void getDetail() {
        CacheSubscriber cacheSubscriber = new CacheSubscriber(KEY + mId) {
            @Override
            protected void getCache(String cacheData) {
                handleResponse(cacheData);
            }

            @Override
            protected void onResponse(String response) {
                handleResponse(response);
            }
        };
        mHttpMethods.getSendDocDetail(mId, cacheSubscriber);
    }

    private void handleResponse(String response){
        DocDetailBean bean = new DocDetailBean();
        StringReader reader = new StringReader(response);
        InputSource source = new InputSource(reader);
        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = builder.build(source);
            Element root = document.getRootElement();
            bean.setTitle(root.getAttributeValue("Title"));
            bean.setUserName(root.getAttributeValue("UserName"));
            bean.setReceiveDate(root.getAttributeValue("ReceiveDate"));
            List<Element> list = root.getChildren();

            List<Element> attachElementList = list.get(0).getChildren();
            List<DocDetailBean.Attach> attachList = new ArrayList<>();
            for (Element element : attachElementList) {
                if (element.getAttributeValue("File_ID") != null) {
                    DocDetailBean.Attach attach = new DocDetailBean.Attach();
                    attach.setOriginalFile(element.getAttributeValue("OriginalFile"));
                    attach.setUrl(element.getAttributeValue("Url"));
                    attach.setFileId(Integer.parseInt(element.getAttributeValue("File_ID")));
                    attachList.add(attach);
                }
            }
            bean.setAttachList(attachList);

            List<Element> receiverElementList = list.get(1).getChildren();
            List<String> receiverList = new ArrayList<>();
            for (Element element : receiverElementList) {
                receiverList.add(element.getAttributeValue("UserInfo"));
            }
            bean.setReceiverList(receiverList);
            mDetailView.getDetailBean(bean);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
