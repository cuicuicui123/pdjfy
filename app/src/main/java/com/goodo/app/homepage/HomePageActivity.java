package com.goodo.app.homepage;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodo.app.R;
import com.goodo.app.base.AppContext;
import com.goodo.app.base.BaseActivity;
import com.goodo.app.homepage.model.HomePageTopListBean;
import com.goodo.app.homepage.presenter.HomePagePagerAdapter;
import com.goodo.app.homepage.presenter.HomePagePresenter;
import com.goodo.app.homepage.presenter.HomePagePresenterImpl;
import com.goodo.app.homepage.view.HomePageView;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页
 * 功能：展示轮播图，点击查看新闻资讯，点击进入系统按钮进行登录
 */

public class HomePageActivity extends BaseActivity implements HomePageView {
    @BindView(R.id.fl_viewPager)
    ShufflingFigureViewPagerFl mViewPagerFl;
    @BindView(R.id.ll_news)
    LinearLayout mNewsLl;
    @BindView(R.id.btn_enter)
    Button mEnterBtn;

    private HomePagePresenter mPresenter;
    private HomePagePagerAdapter mAdapter;
    private AppContext mAppContext;
    private Handler mHandler;
    private ScheduledExecutorService mScheduledExecutorService;

    private int mPosition;
    private List<HomePageTopListBean> mTopListBeanList;
    private static final int TOP_LIST_SIZE = 5;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mPresenter = new HomePagePresenterImpl(this, this);
        mPresenter.getHomePageTopList(TOP_LIST_SIZE);
        mAppContext = AppContext.getInstance();
        mViewPagerFl.setLayoutParams(new LinearLayout.LayoutParams(//viewPager宽高设置
                mAppContext.getWindowWidth(), mAppContext.getWindowWidth() * 9 / 16));
        getNewsLlContent();
    }

    @Override
    protected void initEvent() {
        mEnterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.startToLoginActivity();
            }
        });
    }

    @Override
    public void getTopListBeen(List<HomePageTopListBean> list) {
        mTopListBeanList = list;
        mAdapter = new HomePagePagerAdapter(mTopListBeanList);
        mViewPagerFl.setAdapter(mAdapter);
        setTimeTask();
        //点击事件
        mAdapter.setOnItemClickListener(new HomePagePagerAdapter.OnItemClick() {
            @Override
            public void onItemClick(String contentId) {
                mPresenter.startToPicDetailActivity(contentId);
            }
        });
    }

    /**
     * 获得新闻模块数据
     */
    private void getNewsLlContent(){
        String[] newsStrings = new String[]{"教学通知", "新闻动态", "成果展示", "党建工作"};
        int[] newsPicRes = new int[]{R.drawable.homepage_news1, R.drawable.homepage_news2,
                R.drawable.homepage_news3, R.drawable.homepage_news4};
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0;i < newsStrings.length; i ++) {
            LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.item_home_page_news, mNewsLl, false);
            mNewsLl.addView(ll);
            ((ImageView)ll.findViewById(R.id.iv)).setImageResource(newsPicRes[i]);
            ((TextView)ll.findViewById(R.id.tv)).setText(newsStrings[i]);
            final int position = i;
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.startToNewsListActivity(position);
                }
            });
        }
    }

    /**
     * 定时切换viewPager图片
     */
    private void setTimeTask(){
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mViewPagerFl.setCurrentItem(mPosition);
            }
        };
        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        mScheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2, TimeUnit.SECONDS);
    }

    /**
     * 定时任务
     */
    private class ScrollTask implements Runnable{

        @Override
        public void run() {
            synchronized (mViewPagerFl) {
                mPosition = (mPosition + 1) % mTopListBeanList.size();
                mHandler.obtainMessage().sendToTarget();
            }
        }

    }

    /**
     * onStop之后停止轮播
     */
    @Override
    public void onStop() {
        super.onStop();
        if (mScheduledExecutorService != null) {
            mScheduledExecutorService.shutdown();
        }
    }

    /**
     * 重新返回首页之后根据存储的单位信息更新首页，同时开启viewPager定时更新图片功能
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        if (mScheduledExecutorService != null && mScheduledExecutorService.isShutdown()) {
            mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            mScheduledExecutorService.scheduleWithFixedDelay(new ScrollTask(), 2, 2, TimeUnit.SECONDS);
        }
    }
}
