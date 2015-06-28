package com.kenny.demo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.kenny.demo.entity.News;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

/**
 * 聚合函数使用
 * Created by kenny on 2015/6/28.
 */
public class JuHeActivity  extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private Button btnJuhe1;//聚合函数：count()
    private Button btnJuhe2;//聚合函数：支持连缀
    private Button btnJuhe3;//聚合函数：sum()
    private Button btnJuhe4;//聚合函数：average()
    private Button btnJuhe5;//聚合函数：max()
    private Button btnJuhe6;//聚合函数：min()
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juhe);
        mContext = this;
        //初始化数据库
        SQLiteDatabase db = Connector.getDatabase();
        initView();
    }
    void initView(){
        btnJuhe1 = (Button)this.findViewById(R.id.btn_juhe1);
        btnJuhe1.setOnClickListener(this);
        btnJuhe2 = (Button)this.findViewById(R.id.btn_juhe2);
        btnJuhe2.setOnClickListener(this);
        btnJuhe3 = (Button)this.findViewById(R.id.btn_juhe3);
        btnJuhe3.setOnClickListener(this);
        btnJuhe4 = (Button)this.findViewById(R.id.btn_juhe4);
        btnJuhe4.setOnClickListener(this);
        btnJuhe5 = (Button)this.findViewById(R.id.btn_juhe5);
        btnJuhe5.setOnClickListener(this);
        btnJuhe6 = (Button)this.findViewById(R.id.btn_juhe6);
        btnJuhe6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int result = 0;
        switch (v.getId()){
            case R.id.btn_juhe1:
                result = DataSupport.count(News.class);
                Log.e("TAG","count="+result);
                break;
            case R.id.btn_juhe2:
                //LitePal中所有的聚合函数都是支持连缀的，也就是说我们可以在统计的时候加入条件语句。比如说想要统计一共有多少条新闻是零评论的，就可以这样写
                result = DataSupport.where("commentcount = ?", "0").count(News.class);
                Log.e("TAG","count="+result);
                break;
            case R.id.btn_juhe3:
                //sum()方法主要是用于对结果进行求合的，比如说我们想要统计news表中评论的总数量
                //sum()方法的参数要稍微多一点，我们来一一看下。第一个参数很简单，还是传入的Class，用于指定去统计哪张表当中的数据。第二个参数是列名，表示我们希望对哪一个列中的数据进行求合。第三个参数用于指定结果的类型，这里我们指定成int型，因此返回结果也是int型。
                //需要注意的是，sum()方法只能对具有运算能力的列进行求合，比如说整型列或者浮点型列，如果你传入一个字符串类型的列去求合，肯定是得不到任何结果的，这时只会返回一个0作为结果。
                result = DataSupport.sum(News.class, "commentcount", int.class);
                Log.e("TAG","sum="+result);
                break;
            case R.id.btn_juhe4:
                //average()方法主要是用于统计平均数的，比如说我们想要统计news表中平均每条新闻有多少评论
                //其中average()方法接收两个参数，第一个参数不用说，仍然是Class。第二个参数用于指定列名的，表示我们想要统计哪一列的平均数。需要注意的是，这里返回值的类型是double型，因为平均数基本上都是会带有小数的，用double类型可以最大程序保留小数位的精度。
                //同样地，average()方法也只能对具有运算能力的列进行求平均值，如果你传入了一个字符串类型的列，也是无法得到任何结果的，这时同样只会返回一个0作为结果。
                double result1 = DataSupport.average(News.class, "commentcount");
                Log.e("TAG","average="+result1);
                break;
            case R.id.btn_juhe5:
                //max()方法主要用于求出某个列中最大的数值，比如我们想要知道news表中所有新闻里面最高的评论数是多少
                //那么不用多说，max()方法也只能对具有运算能力的列进行求最大值的，希望你在使用的时候能够谨记这一点
                result = DataSupport.max(News.class, "commentcount", int.class);
                Log.e("TAG","max="+result);
                break;
            case R.id.btn_juhe6:
                //min()方法主要用于求出某个列中最小的数值，比如我们想要知道news表中所有新闻里面最少的评论数是多少
                result = DataSupport.min(News.class, "commentcount", int.class);
                Log.e("TAG","min="+result);
                break;
        }
    }
}
