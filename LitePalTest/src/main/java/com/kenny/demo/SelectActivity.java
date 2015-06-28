package com.kenny.demo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kenny.demo.entity.Comment;
import com.kenny.demo.entity.News;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询测试
 * Created by kenny on 2015/6/27.
 */
public class SelectActivity extends AppCompatActivity implements View.OnClickListener{
        private Context mContext;
        private Button btnSelect1;//1简单查询：一条
        private Button btnSelect2;//2简单查询：第一条数据
        private Button btnSelect3;//2简单查询：最后一条数据
        private Button btnSelect4;//4简单查询：查询多条数据
        private Button btnSelect5;//5简单查询：查询多条数据
        private Button btnSelect6;//6简单查询：查询所有
        private Button btnSelect7;//1连缀查询
        private Button btnSelect8;//2连缀查询：指定查询某些列
        private Button btnSelect9;//3连缀查询：时间排序
        private Button btnSelect10;//4连缀查询：限至一次性查询几条
        private Button btnSelect11;//5连缀查询：分页
        private Button btnSelect12;//1激进查询(多表联查)
        private Button btnSelect13;//2激进查询(多表联查)
        private Button btnSelect14;//原生查询
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_select);
            mContext = this;
            //初始化数据库
            SQLiteDatabase db = Connector.getDatabase();
            initView();
        }
        void initView(){
            btnSelect1 = (Button)this.findViewById(R.id.btn_select1);
            btnSelect1.setOnClickListener(this);
            btnSelect2 = (Button)this.findViewById(R.id.btn_select2);
            btnSelect2.setOnClickListener(this);
            btnSelect3 = (Button)this.findViewById(R.id.btn_select3);
            btnSelect3.setOnClickListener(this);
            btnSelect4 = (Button)this.findViewById(R.id.btn_select4);
            btnSelect4.setOnClickListener(this);
            btnSelect5 = (Button)this.findViewById(R.id.btn_select5);
            btnSelect5.setOnClickListener(this);
            btnSelect6 = (Button)this.findViewById(R.id.btn_select6);
            btnSelect6.setOnClickListener(this);
            btnSelect7 = (Button)this.findViewById(R.id.btn_select7);
            btnSelect7.setOnClickListener(this);
            btnSelect8 = (Button)this.findViewById(R.id.btn_select8);
            btnSelect8.setOnClickListener(this);
            btnSelect9 = (Button)this.findViewById(R.id.btn_select9);
            btnSelect9.setOnClickListener(this);
            btnSelect10 = (Button)this.findViewById(R.id.btn_select10);
            btnSelect10.setOnClickListener(this);
            btnSelect11 = (Button)this.findViewById(R.id.btn_select11);
            btnSelect11.setOnClickListener(this);
            btnSelect12 = (Button)this.findViewById(R.id.btn_select12);
            btnSelect12.setOnClickListener(this);
            btnSelect13 = (Button)this.findViewById(R.id.btn_select13);
            btnSelect13.setOnClickListener(this);
            btnSelect14 = (Button)this.findViewById(R.id.btn_select14);
            btnSelect14.setOnClickListener(this);
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_select1:
                //查询news表中id为5的这条记录
                News news = DataSupport.find(News.class, 5);
                Log.e("TAG",news.tostring(news));
                break;
            case R.id.btn_select2:
                //你也许遇到过以下场景，在某些情况下，你需要取出表中的第一条数据，那么传统的做法是怎么样的呢？在SQL语句中指定一个limit值，然后获取返回结果的第一条记录。但是在LitePal中不用这么麻烦，比如我们想要获取news表中的第一条数据，只需要这样写
                //只需调用findFirst()方法，然后传入News类，得到的就是news表中的第一条数据了
                News firstNews = DataSupport.findFirst(News.class);
                Log.e("TAG", firstNews.tostring(firstNews));
                break;
            case R.id.btn_select3:
                //获取news表中最后一条数据
                News lastNews = DataSupport.findLast(News.class);
                Log.e("TAG", lastNews.tostring(lastNews));
                break;
            case R.id.btn_select4:
                //目前都只是查询单条数据的功能，如果想要查询多条数据该怎么办呢？比如说，我们想把news表中id为4、5、6的数据都查出来，该怎么写呢？也许有的朋友会比较聪明，立马就想到可以一个个去查，就调用四次find()方法嘛，然后把1、3、5、7这四个id分别传进去不就可以了。没错，这样做完全是可以的，而且效率也并不低，但是LitePal给我们提供了一个更简便的方法——findAll()。这个方法的用法和find()方法是非常类似的，只不过它可以指定多个id，并且返回值也不再是一个泛型类对象，而是一个泛型类集合
                List<News> newsList = DataSupport.findAll(News.class, 4, 5, 6);
                for (int i = 0; i <newsList.size(); i++){
                    Log.e("TAG", newsList.get(i).tostring(newsList.get(i)));
                }
                break;
            case R.id.btn_select5:
                //虽说这个语法设计算是相当人性化，但是在有些场景或许不太适用，因为可能要你要查询的多个id已经封装到一个数组里了。那么没关系，findAll()方法也是接收数组参数的，所以说同样的功能你也可以这样写：
                long[] ids = new long[] { 4, 5, 6 };
                List<News> newsList1 = DataSupport.findAll(News.class, ids);
                for (int i = 0; i <newsList1.size(); i++){
                    Log.e("TAG", newsList1.get(i).tostring(newsList1.get(i)));
                }
                break;
            case R.id.btn_select6:
                //查询所有
                List<News> allNews = DataSupport.findAll(News.class);
                for (int i = 0; i <allNews.size(); i++){
                    Log.e("TAG", allNews.get(i).tostring(allNews.get(i)));
                }
                break;
            case R.id.btn_select7:
                //想查询news表中所有评论数大于零的新闻，就可以这样写
                List<News> newsList2 = DataSupport.where("commentcount > ?", "0").find(News.class);
                for (int i = 0; i <newsList2.size(); i++){
                    Log.e("TAG", newsList2.get(i).tostring(newsList2.get(i)));
                }
                break;
            case R.id.btn_select8:
                //也许你并不需要那么多的数据，而是只要title和content这两列数据。那么也很简单，我们只要再增加一个连缀就行了
                List<News> newsList3 = DataSupport.select("title", "content").where("commentcount > ?", "0").find(News.class);
                for (int i = 0; i <newsList3.size(); i++){
                    Log.e("TAG", "title="+newsList3.get(i).getTitle() + "content="+newsList3.get(i).getContent());
                }
                break;
            case R.id.btn_select9:
                //我希望将查询出的新闻按照发布的时间倒序排列，即最新发布的新闻放在最前面,order()方法中接收一个字符串参数，用于指定查询出的结果按照哪一列进行排序，asc表示正序排序，desc表示倒序排序
                List<News> newsList4 = DataSupport.select("title", "content").where("commentcount > ?", "0").order("publishdata desc").find(News.class);
                for (int i = 0; i <newsList4.size(); i++){
                    Log.e("TAG", "title="+newsList4.get(i).getTitle() + "content="+newsList4.get(i).getContent());
                }
                break;
            case R.id.btn_select10:
                //也许你并不希望将所有条件匹配的结果一次性全部查询出来，因为这样数据量可能会有点太大了，而是希望只查询出前10条数据
                List<News> newsList5 = DataSupport.select("title", "content").where("commentcount > ?", "0").order("publishdata desc").limit(10).find(News.class);
                for (int i = 0; i <newsList5.size(); i++){
                    Log.e("TAG", "title=" + newsList5.get(i).getTitle() + "content=" + newsList5.get(i).getContent());
                }
                break;
            case R.id.btn_select11:
                //刚才我们查询到的是所有匹配条件的前10条新闻，那么现在我想对新闻进行分页展示，翻到第二页时，展示第11到第20条新闻，这又该怎么实现呢？没关系，在LitePal的帮助下，这些功能都是十分简单的，只需要再连缀一个偏移量就可以了
                List<News> newsList6 = DataSupport.select("title", "content").where("commentcount = ?", "0").order("publishdata desc").limit(10).offset(10).find(News.class);
                for (int i = 0; i <newsList6.size(); i++){
                    Log.e("TAG", "title=" + newsList6.get(i).getTitle() + "content=" + newsList6.get(i).getContent());
                }
                //可以看到，这里我们又添加了一个offset()方法，用于指定查询结果的偏移量，这里指定成10，就表示偏移十个位置，那么原来是查询前10条新闻的，偏移了十个位置之后，就变成了查询第11到第20条新闻了，如果偏移量是20，那就表示查询第21到第30条新闻，以此类推。因此，limit()方法和order()方法共同对应了一条SQL语句中的limit部分。
                break;
            case R.id.btn_select12:
                //不过，上述我们的所有用法中，都只能是查询到指定表中的数据而已，关联表中数据是无法查到的，因为LitePal默认的模式就是懒查询，当然这也是推荐的查询方式。那么，如果你真的非常想要一次性将关联表中的数据也一起查询出来，当然也是可以的，LitePal中也支持激进查询的方式，下面我们就来一起看一下。
                //不知道你有没有发现，刚才我们所学的每一个类型的find()方法，都对应了一个带有isEager参数的方法重载，这个参数相信大家一看就明白是什么意思了，设置成true就表示激进查询，这样就会把关联表中的数据一起查询出来了。
                News n = DataSupport.find(News.class, 4, true);
                List<Comment> commentList = n.getCommentList();
                for (int i = 0; i <commentList.size(); i++){
                    Toast.makeText(getApplicationContext(),commentList.get(i).tostring(commentList.get(i)),Toast.LENGTH_SHORT).show();
                }
                //可以看到，这里并没有什么复杂的用法，也就是在find()方法的最后多加了一个true参数，就表示使用激进查询了。这会将和news表关联的所有表中的数据也一起查出来，那么comment表和news表是多对一的关联，所以使用激进查询一条新闻的时候，那么该新闻所对应的评论也就一起被查询出来了。
                break;
            case R.id.btn_select13:
                //激进查询的用法非常简单，就只有这么多，其它find()方法也都是同样的用法，就不再重复介绍了。但是这种查询方式LitePal并不推荐，因为如果一旦关联表中的数据很多，查询速度可能就会非常慢。而且激进查询只能查询出指定表的关联表数据，但是没法继续迭代查询关联表的关联表数据。因此，这里我建议大家还是使用默认的懒加载更加合适，至于如何查询出关联表中的数据，其实只需要在模型类中做一点小修改就可以了。修改News类中的代码，如下所示：
//                public class News extends DataSupport{
//
//                    ...
//
//                    public List<Comment> getComments() {
//                        return DataSupport.where("news_id = ?", String.valueOf(id)).find(Comment.class);
//                    }
//
//                }
                News n1 = DataSupport.find(News.class, 4);
                Toast.makeText(getApplicationContext(),n1.tostring(n1),Toast.LENGTH_SHORT).show();
                List<Comment> commentList1 = n1.getComments();
                for (int i = 0; i <commentList1.size(); i++){
                    Toast.makeText(getApplicationContext(),commentList1.get(i).tostring(commentList1.get(i)),Toast.LENGTH_SHORT).show();
                }
                //可以看到，我们在News类中添加了一个getComments()方法，而这个方法的内部就是使用了一句连缀查询，查出了当前这条新闻对应的所有评论。改成这种写法之后，我们就可以将关联表数据的查询延迟，当我们需要去获取新闻所对应的评论时，再去调用News的getComments()方法，这时才会去查询关联数据。这种写法会比激进查询更加高效也更加合理。
                break;
            case R.id.btn_select14:
                //相信你已经体会到，LitePal在查询方面提供的API已经相当丰富了。但是，也许你总会遇到一些千奇百怪的需求，可能使用LitePal提供的查询API无法完成这些需求。没有关系，因为即使使用了LitePal，你仍然可以使用原生的查询方式(SQL语句)来去查询数据。DataSuppport类中还提供了一个findBySQL()方法，使用这个方法就能通过原生的SQL语句方式来查询数据了
                Cursor cursor = DataSupport.findBySQL("select * from news where commentcount>?", "0");
                if (cursor!=null){
                    List<News> nList = new ArrayList<News>();
                    while (cursor.moveToNext()){
                        News n5 = new News();
                        n5.setId(cursor.getInt(cursor.getColumnIndex("id")));
                        n5.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                        n5.setContent(cursor.getString(cursor.getColumnIndex("content")));

                        nList.add(n5);
                        n5 = null;
                    }
                    cursor.close();
                    for (int i =0; i<nList.size(); i++){
                        Log.e("TAG", "id="+nList.get(i).getId() + "title=" + nList.get(i).getTitle() + "content=" + nList.get(i).getContent());
                    }
                }
                break;
        }
    }
}
