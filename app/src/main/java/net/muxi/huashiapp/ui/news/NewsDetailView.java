package net.muxi.huashiapp.ui.news;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.muxi.huashiapp.App;
import net.muxi.huashiapp.R;
import net.muxi.huashiapp.common.base.BaseActivity;
import net.muxi.huashiapp.common.data.News;
import net.muxi.huashiapp.ui.webview.WebViewActivity;
import net.muxi.huashiapp.util.Logger;
import net.muxi.huashiapp.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tencent.bugly.crashreport.inner.InnerAPI.context;

/**
 * Created by december on 16/7/29.
 */
public class NewsDetailView extends RelativeLayout {

    @BindView(R.id.news_float_btn)
    ImageView mNewsFloatBtn;
    @BindView(R.id.news_title)
    TextView mNewsTitle;
    @BindView(R.id.news_date)
    TextView mNewsDate;
    @BindView(R.id.link_layout)
    LinearLayout mLinkLayout;
    @BindView(R.id.tv_appendix)
    TextView mTvAppendix;
    @BindView(R.id.news_content)
    WebView mNewsContent;
    private Context mContext;
    private List<News> mNewsList;
    int mPosition;


    public NewsDetailView(Context context, List<News> news, int position) {
        super(context);
        mContext = context;
        mNewsList = news;
        mPosition = position;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_news_detail, this, true);
        ButterKnife.bind(this);
        mNewsTitle.setText(mNewsList.get(mPosition).getTitle());
//        String content = mNewsList.get(mPosition).getContent();
//        mNewsContent.setText(DimensUtil.toDBC(content));
        initWebView();

        mNewsDate.setText(mNewsList.get(mPosition).getDate());
        addAppendix();
//        mNewsLink.setText(mNewsList.get(mPosition).getAppendix_list().toString());

        mNewsFloatBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity) mContext).onBackPressed();
            }
        });

    }

    private void initWebView() {
        WebSettings webSettings = mNewsContent.getSettings();
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setLoadsImagesAutomatically(false);

        mNewsContent.loadData(mNewsList.get(mPosition).getContent(),"text/html; charset=UTF-8",null);


    }

    private void addAppendix() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        int appendixNum = mNewsList.get(mPosition).getAppendix_list().size();
        final List<String> appendix = mNewsList.get(mPosition).getAppendix_list();
        TextView[] textViews = new TextView[appendixNum];
        if (appendixNum == 0) {
            mTvAppendix.setVisibility(GONE);
        }
        Logger.d(appendixNum + "");
        for (int i = 0; i < appendixNum; i++) {
            final int j = i;
            textViews[i] = new TextView(mContext);
            textViews[i].setText(appendix.get(i).toString());
            textViews[i].getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            textViews[i].setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

//            textViews[i].setAutoLinkMask(Linkify.WEB_URLS);
            textViews[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(appendix.get(j).toString()));
//                        mContext.startActivity(browserIntent);
                        Intent intent = WebViewActivity.newIntent(mContext, appendix.get(j).toString());
                        mContext.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtil.showShort(App.sContext.getString(R.string.tip_link_unable_open));
                    }
                }
            });
//            textViews[i].setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, DownloadService.class);
//                    intent.putExtra("fileType", "file");
//                    intent.putExtra("url", appendix.get(j));
//                    intent.putExtra("fileName", appendix.get(j));
//                    mContext.startService(intent);
//                }
//            });

            mLinkLayout.addView(textViews[i], params);
        }
    }

}