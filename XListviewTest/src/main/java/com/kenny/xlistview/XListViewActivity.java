package com.kenny.xlistview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;

import com.kenny.xlistview.view.XListView;

import java.util.ArrayList;


public class XListViewActivity extends Activity implements XListView.IXListViewListener {
    private XListView mListView;
    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> items = new ArrayList<String>();
    private Handler mHandler;
    private int start = 0;
    private static int refreshCnt = 0;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        geneItems();
        mListView = (XListView) findViewById(R.id.xListView);
        mListView.setPullLoadEnable(true);
        mAdapter = new ArrayAdapter<String>(this, R.layout.list_item, items);
        mListView.setAdapter(mAdapter);
//		mListView.setPullLoadEnable(false);//不允许上拉刷新
//		mListView.setPullRefreshEnable(false);//不允许下拉刷新
        mListView.setXListViewListener(this);
        mHandler = new Handler();
    }

    private void geneItems() {
        for (int i = 0; i != 20; ++i) {
            items.add("refresh cnt " + (++start));
        }
    }

    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("刚刚");
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                start = ++refreshCnt;
                items.clear();
                geneItems();
                // mAdapter.notifyDataSetChanged();
                mAdapter = new ArrayAdapter<String>(XListViewActivity.this, R.layout.list_item, items);
                mListView.setAdapter(mAdapter);
                onLoad();
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                geneItems();
                mAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }

}