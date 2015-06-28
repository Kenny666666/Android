package com.kenny.demo;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kenny.demo.entity.Category;
import com.kenny.demo.entity.Comment;
import com.kenny.demo.entity.Introduction;
import com.kenny.demo.entity.News;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * LitePal数据库框架案例
 * LitePal采取的是对象关系映射(ORM)的模式
 * 温馨提示：传统数据库表升级详解：http://blog.csdn.net/guolin_blog/article/details/39151617
 * 温馨提示：数据库表关联关系也可以在这位大神的文章中看到http://blog.csdn.net/guolin_blog/article/details/39207945
 * 温馨提示：数据库关联关系口诀(即一对一关联的实现方式是用外键，多对一关联的实现方式也是用外键，多对多关联的实现方式是用中间表)
 * 数据库操作指令：
 * adb shell
 * cd /data/data
 * cd com.kenny.demo
 * cd databases
 * sqlite3 news.db          -->打开数据库
 * .table                   -->查看数据库有哪些表
 * pragma table_info(news)  -->查看表有哪些列，或者使用.mode line  出可查看
 * select * from news;      --查看表数据
 * select * from sqlite_master where name = 'news';  -->查看建表语句
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final static String TAG = "MainActivity";
    private Context mContext;
    private Button btnSave1;//保存一条数据
    private Button btnSave2;//保存一条数据，保存过程中出现异常会抛出异常
    private Button btnSave3;//数据存储，存储后会将返回的id设置给实体类的id
    private Button btnSave4;//数据存储：一对多数据存储
    private Button btnSave5;//多条数据一次存储
    private Button btnSave6;//数据存储：一对一
    private Button btnSave7;//数据存储：多对多

    private Button btnSave8;//跳转至 删除与更新
    private Button btnSave9;//跳转至 查询
    private Button btnSave10;//跳转至 聚合数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        //初始化数据库
        SQLiteDatabase db = Connector.getDatabase();
        initView();
    }

    void initView(){
        btnSave1 = (Button)this.findViewById(R.id.btn_save1);
        btnSave1.setOnClickListener(this);
        btnSave2 = (Button)this.findViewById(R.id.btn_save2);
        btnSave2.setOnClickListener(this);
        btnSave3 = (Button)this.findViewById(R.id.btn_save3);
        btnSave3.setOnClickListener(this);
        btnSave4 = (Button)this.findViewById(R.id.btn_save4);
        btnSave4.setOnClickListener(this);
        btnSave5 = (Button)this.findViewById(R.id.btn_save5);
        btnSave5.setOnClickListener(this);
        btnSave6 = (Button)this.findViewById(R.id.btn_save6);
        btnSave6.setOnClickListener(this);
        btnSave7 = (Button)this.findViewById(R.id.btn_save7);
        btnSave7.setOnClickListener(this);
        btnSave8 = (Button)this.findViewById(R.id.btn_save8);
        btnSave8.setOnClickListener(this);
        btnSave9 = (Button)this.findViewById(R.id.btn_save9);
        btnSave9.setOnClickListener(this);
        btnSave10 = (Button)this.findViewById(R.id.btn_save10);
        btnSave10.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        News news = new News();
        switch (v.getId()){
            case R.id.btn_save1:
                news.setTitle("这是一条新闻标题");
                news.setContent("这是一条新闻内容");
                news.setPublishData(new Date());
                boolean result = news.save();
                if (result){
                    Toast.makeText(mContext, "存储成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(mContext, "存储失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_save2:
                news.setTitle("这是一条新闻标题");
                news.setContent("这是一条新闻内容");
                news.setPublishData(new Date());
                news.saveThrows();
                break;
            case R.id.btn_save3:
                news.setTitle("这是一条新闻标题");
                news.setContent("这是一条新闻内容");
                news.setPublishData(new Date());
                Log.d("TAG", "news id is " + news.getId());
                news.save();
                Log.d("TAG", "news id is " + news.getId());
                break;
            case R.id.btn_save4:
                Comment comment1 = new Comment();
                comment1.setContent("好评！");
                comment1.setPublishDate(new Date());
                comment1.save();
                Comment comment2 = new Comment();
                comment2.setContent("赞一个");
                comment2.setPublishDate(new Date());
                comment2.save();
                News news1 = new News();
                news1.getCommentList().add(comment1);
                news1.getCommentList().add(comment2);
                news1.setTitle("第二条新闻标题");
                news1.setContent("第二条新闻内容");
                news1.setPublishData(new Date());
                news1.setCommentCount(news1.getCommentList().size());
                boolean result1 = news1.save();
                if (result1){
                    Toast.makeText(mContext, "存储成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(mContext, "存储失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_save5:
                List<News> newsList = new ArrayList<News>();
                for (int i =0; i< 600; i++){
                    News n = new News();
                    n.setTitle("这是一条新闻标题");
                    n.setContent("这是一条新闻内容");
                    n.setPublishData(new Date());
                    newsList.add(n);
                }
                DataSupport.saveAll(newsList);
                break;
            case R.id.btn_save6:
                Introduction introduction = new Introduction();
                introduction.setGuide("这是一条新闻导语");
                introduction.setDigest("这是一条新闻摘要");
                introduction.save();

                news.setTitle("这是一条新闻标题");
                news.setContent("这是一条新闻内容");
                news.setPublishData(new Date());
                news.setIntroduction(introduction);
                boolean result2 = news.save();
                if (result2){
                    Toast.makeText(mContext, "存储成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(mContext, "存储失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_save7:
                News news3 = new News();
                news3.setTitle("这是一条新闻标题");
                news3.setContent("这是一条新闻内容");
                news3.setPublishData(new Date());
                News news4 = new News();
                news4.setTitle("这是一条新闻标题");
                news4.setContent("这是一条新闻内容");
                news4.setPublishData(new Date());
                List<News> newsList1 = new ArrayList<News>();
                newsList1.add(news3);
                newsList1.add(news4);

                Category category = new Category();
                category.setName("体育");
                Category category1 = new Category();
                category1.setName("科技");
                List<Category> categoryList = new ArrayList<Category>();
                categoryList.add(category);
                categoryList.add(category1);
                //相互设置
                category.setNewsList(newsList1);
                category1.setNewsList(newsList1);
                news3.setCategoryList(categoryList);
                news4.setCategoryList(categoryList);

                category.save();
                category1.save();
                news3.save();
                news4.save();
                break;
            case R.id.btn_save8:
                Intent intent = new Intent(MainActivity.this,DeleteAndUpdateActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_save9:
                Intent intent1 = new Intent(MainActivity.this,SelectActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_save10:
                Intent intent2 = new Intent(MainActivity.this,JuHeActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
