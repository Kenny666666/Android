package com.kenny.demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kenny.demo.entity.News;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

/**
 * 删除与更新测试
 * Created by kenny on 2015/6/27.
 */
public class DeleteAndUpdateActivity extends AppCompatActivity implements View.OnClickListener{
    private Context mContext;

    private Button btnUpdate1;//更新数据
    private Button btnUpdate2;//更新数据：更新多条记录
    private Button btnUpdate3;//更新数据：更新多条记录
    private Button btnUpdate4;//更新数据：更新多条记录
    private Button btnUpdate5;//更新数据
    private Button btnUpdate6;//更新数据：更新多条记录
    private Button btnUpdate7;//更新数据：设置成默认值
    private Button btnDelete1;//删除数据：一条
    private Button btnDelete2;//删除数据：多条
    private Button btnDelete3;//删除数据：所有
    private Button btnDelete4;//删除数据：判断对象是否存持久化，有就删除
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_or_update);
        mContext = this;
        //初始化数据库
        SQLiteDatabase db = Connector.getDatabase();
        initView();
    }
    void initView(){
        btnUpdate1 = (Button)this.findViewById(R.id.btn_update1);
        btnUpdate1.setOnClickListener(this);
        btnUpdate2 = (Button)this.findViewById(R.id.btn_update2);
        btnUpdate2.setOnClickListener(this);
        btnUpdate3 = (Button)this.findViewById(R.id.btn_update3);
        btnUpdate3.setOnClickListener(this);
        btnUpdate4 = (Button)this.findViewById(R.id.btn_update4);
        btnUpdate4.setOnClickListener(this);
        btnUpdate5 = (Button)this.findViewById(R.id.btn_update5);
        btnUpdate5.setOnClickListener(this);
        btnUpdate6 = (Button)this.findViewById(R.id.btn_update6);
        btnUpdate6.setOnClickListener(this);
        btnUpdate7 = (Button)this.findViewById(R.id.btn_update7);
        btnUpdate7.setOnClickListener(this);
        btnDelete1 = (Button)this.findViewById(R.id.btn_delete1);
        btnDelete1.setOnClickListener(this);
        btnDelete2 = (Button)this.findViewById(R.id.btn_delete2);
        btnDelete2.setOnClickListener(this);
        btnDelete3 = (Button)this.findViewById(R.id.btn_delete3);
        btnDelete3.setOnClickListener(this);
        btnDelete4 = (Button)this.findViewById(R.id.btn_delete4);
        btnDelete4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int result = 0;
        switch (v.getId()){
            case R.id.btn_update1:
                ContentValues values = new ContentValues();
                values.put("title", "今日iPhone6发布");
                result = DataSupport.update(News.class, values, 2);//更新id为2的那个新闻数据

                Toast.makeText(getApplicationContext(),"更新结果：" + result,Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_update2:
                //重点我们看一下最后的这个conditions数组，由于它的类型是一个String数组，我们可以在这里填入任意多个String参数，其中最前面一个String参数用于指定约束条件，后面所有的String参数用于填充约束条件中的占位符(即?号)，比如约束条件中有一个占位符，那么后面就应该填写一个参数，如果有两个占位符，后面就应该填写两个参数，以此类推。
                ContentValues values1 = new ContentValues();
                values1.put("title", "今日iPhone6 Plus发布");
                result = DataSupport.updateAll(News.class, values1, "title = ?", "今日iPhone6发布");

                Toast.makeText(getApplicationContext(),"更新结果：" + result,Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_update3:
                //比如说我们想把news表中标题为“今日iPhone6发布”且评论数量大于0的所有新闻的标题改成“今日iPhone6 Plus发布”，
                ContentValues values2 = new ContentValues();
                values2.put("title", "今日iPhone6 Plus发布");
                result = DataSupport.updateAll(News.class, values2, "title = ? and commentcount > ?", "今日iPhone6发布", "0");

                Toast.makeText(getApplicationContext(),"更新结果：" + result,Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_update4:
                //那么如果我们想把news表中所有新闻的标题都改成“今日iPhone6发布”，该怎么写呢？其实这就更简单了，只需要把最后的约束条件去掉就行了
                ContentValues values3 = new ContentValues();
                values3.put("title", "今日iPhone6发布");
                result = DataSupport.updateAll(News.class, values3);

                Toast.makeText(getApplicationContext(),"更新结果：" + result,Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_update5:
                //当然有些朋友可能会觉得这样用起来还是有点复杂，因为这个ContentValues对象很烦人，每次创建它的时候都要写很多繁琐的代码。没关系，LitePal也充分考虑了这种情况，提供了一种不需要ContentValues就能修改数据的方法，下面我们尝试使用这种新方法来完成上述同样的功能。
                //比如把news表中id为2的记录的标题改成“今日iPhone6发布
                News updateNews = new News();
                updateNews.setTitle("今日iPhone6发布==========");
                result = updateNews.update(2);

                Toast.makeText(getApplicationContext(),"更新结果：" + result,Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_update6:
                //那么如果我们想把news表中标题为“今日iPhone6发布”且评论数量大于0的所有新闻的标题改成“今日iPhone6 Plus发布”，就可以这样写
                News updateNews1 = new News();
                updateNews1.setTitle("今日iPhone6 Plus");
                result =  updateNews1.updateAll("title = ? and commentcount > ?", "今日iPhone6发布", "0");

                Toast.makeText(getApplicationContext(),"更新结果：" + result,Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_update7:
                //但是这种用法有一点需要注意，就是如果我们想把某一条数据修改成默认值，比如说将评论数修改成0，只是调用updateNews.setCommentCount(0)这样是不能修改成功的，因为即使不调用这行代码，commentCount的值也默认是0。所以如果想要将某一列的数据修改成默认值的话，还需要借助setToDefault()方法。用法也很简单，在setToDefault()方法中传入要修改的字段名就可以了(类中的字段名)，比如说我们想要把news表中所有新闻的评论数清零，就可以这样写
                News updateNews2 = new News();
                updateNews2.setToDefault("commentCount");
                result = updateNews2.updateAll();

                Toast.makeText(getApplicationContext(),"更新结果：" + result,Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_delete1:
                //需要注意的是，这不仅仅会将news表中id为2的记录删除，同时还会将其它表中以news id为2的这条记录作为外键的数据一起删除掉，因为外键既然不存在了，那么这么数据也就没有保留的意义了
                result = DataSupport.delete(News.class, 2);

                Toast.makeText(getApplicationContext(),"删除结果：" + result,Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_delete2:
                //deleteAll()方法接收两个参数，第一个参数是Class，传入我们要删除的那个类的Class就好，第二个参数是一个conditions数组，用于指定删除哪些行的约束条件，返回值表示此次删除了多少行数据，用法和updateAll()方法是基本相同的。
                //那么比如说我们想把news表中标题为“今日iPhone6发布”且评论数等于0的所有新闻都删除掉
                result = DataSupport.deleteAll(News.class, "title = ? and commentcount = ?", "今日iPhone6发布", "0");

                Toast.makeText(getApplicationContext(),"删除结果：" + result,Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_delete3:
                //删除所有数据
                result = DataSupport.deleteAll(News.class);

                Toast.makeText(getApplicationContext(),"删除结果：" + result,Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_delete4:
                //一个对象如果save过了之后，那就是持久化的了。除了调用save()方法之外，通过DataSupport中提供的查询方法从数据库中查出来的对象也是经过持久化的
                //另外还有一个简单的办法可以帮助我们判断一个对象是否是持久化之后的，DataSupport类中提供了一个isSaved()方法，这个方法返回true就表示该对象是经过持久化的，返回false则表示该对象未经过持久化。那么删除一个对象对应的数据也就可以这样写了：
                News news = new News();
                news.setTitle("今日iPhone6 Plus");
                news.save();

                boolean isSave = news.isSaved();

                if (isSave) {
                    Toast.makeText(getApplicationContext(),"有持久化：" + isSave + result,Toast.LENGTH_SHORT).show();

                    result = news.delete();

                    Toast.makeText(getApplicationContext(),"删除结果：" + result,Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"没持久化：" + isSave + result,Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
