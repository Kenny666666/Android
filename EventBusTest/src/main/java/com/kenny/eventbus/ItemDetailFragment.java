package com.kenny.eventbus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kenny.eventbus.event.EventBus;

/**
 * description 详情界面
 * Created by hugs on 2015/8/2.
 * version
 */
public class ItemDetailFragment extends Fragment{

    private TextView tvDetail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //订阅事件
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消订阅
        EventBus.getDefault().unregister(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_item_detail,container,false);

        tvDetail = (TextView) view.findViewById(R.id.item_detail);

        return view;
    }

    public void onEventMainThread(Item item){
        if (item !=null){
            tvDetail.setText(item.content);
        }
    }

}
