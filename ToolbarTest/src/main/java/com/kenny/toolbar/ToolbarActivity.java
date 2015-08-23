package com.kenny.toolbar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


/**
 * 将Toolbar作为独立控件使用示例
 *
 * @author AigeStudio 2015-07-17
 */
public class ToolbarActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.ac_toolbar_toolbar);

        // 设置主标题及其颜�?
        toolbar.setTitle("Title");
        toolbar.setTitleTextColor(Color.WHITE);

        // 设置次标题及其颜�?
        toolbar.setSubtitle("SubTitle");
        toolbar.setSubtitleTextColor(Color.LTGRAY);

        // 设置导航按钮
        toolbar.setNavigationIcon(R.drawable.menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ToolbarActivity.this, "Navigation", Toast.LENGTH_SHORT).show();
            }
        });
        // 设置Logo图标
        toolbar.setLogo(R.mipmap.ic_launcher);

        // 设置菜单及其点击监听
        toolbar.inflateMenu(R.menu.ac_toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String result = "";
                switch (item.getItemId()) {
                    case R.id.ac_toolbar_copy:
                        result = "Copy";
                        break;
                    case R.id.ac_toolbar_cut:
                        result = "Cut";
                        break;
                    case R.id.ac_toolbar_del:
                        result = "Del";
                        break;
                    case R.id.ac_toolbar_edit:
                        result = "Edit";
                        break;
                    case R.id.ac_toolbar_email:
                        result = "Email";
                        break;
                }
                Toast.makeText(ToolbarActivity.this, result, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
