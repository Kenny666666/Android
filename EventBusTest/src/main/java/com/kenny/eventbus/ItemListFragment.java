package com.kenny.eventbus;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kenny.eventbus.event.Event;
import com.kenny.eventbus.event.EventBus;

/**
 * description
 * Created by hugs on 2015/8/2.
 * version
 */
public class ItemListFragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //订阅事件(将当前类中所有方法总线中的map中)
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消事件(将当前类中所有方法在总线中的map中移除)
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //开启线程加载列表
        new Thread(){
            @Override
            public void run() {

                try {
                    Thread.sleep(2000);//模拟耗时
                    //发布事件 ，在后台线程发的事件，会调用本类中的onEventMainThread(ItemListEvent event)方法
                    EventBus.getDefault().post(new Event.ItemListEvent(Item.ITEMS));
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }.start();
    }

    public void onEventMainThread(Event.ItemListEvent event){

        setListAdapter(new ArrayAdapter<Item>(getActivity(),android.R.layout.simple_list_item_activated_1,android.R.id.text1,event.getItems()));
    }

    /**单击列表子项事件*/
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        EventBus.getDefault().post(getListView().getItemAtPosition(position));
    }
}
