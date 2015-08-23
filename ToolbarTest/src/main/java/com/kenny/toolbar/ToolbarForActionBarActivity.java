package com.kenny.toolbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


/**
 * 将Toolbar作为ActionBar使用示例
 *
 * @author AigeStudio 2015-07-17
 */
public class ToolbarForActionBarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_toolbar_for_action_bar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.ac_toolbar_for_action_bar_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ac_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    public void actionMode(View view) {
        startSupportActionMode(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuItem saveItem = menu.add("add").setIcon(R.drawable.icon);
                MenuItemCompat.setShowAsAction(saveItem, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);

                MenuItem searchItem = menu.add("del").setIcon(R.drawable.icon);
                MenuItemCompat.setShowAsAction(searchItem, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);

                MenuItem refreshItem = menu.add("que").setIcon(R.drawable.icon);
                MenuItemCompat.setShowAsAction(refreshItem, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                Toast.makeText(ToolbarForActionBarActivity.this, item.getTitle(),Toast.LENGTH_SHORT).show();
                mode.finish();
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
    }
}
