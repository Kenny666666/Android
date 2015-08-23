package com.kenny.eventbus.event;

import com.kenny.eventbus.Item;

import java.util.List;

/**
 * description
 * Created by hugs on 2015/8/2.
 * version
 */
public class Event {
    /**列表加载事件*/
    public static class ItemListEvent{

        private List<Item> items;


        public ItemListEvent(List<Item> items) {
            this.items = items;
        }

        public List<Item> getItems(){
            return items;
        }
    }
}
