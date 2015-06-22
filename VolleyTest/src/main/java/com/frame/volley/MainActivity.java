package com.frame.volley;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.frame.volley.com.frame.volley.entity.Weather;
import com.frame.volley.com.frame.volley.entity.WeatherInfo;
import com.frame.volley.util.BitmapCache;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * volley测试
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final static String TAG = "MainActivity";
    private RequestQueue mQueue;
    /**获取字符串get*/
    private Button btnStrRequestGet;
    /**获取字符串post*/
    private Button btnStrRequestPost;
    /**获取json*/
    private Button btnJsonRequestGet;
    /**获取xml*/
    private Button btnXmlRequest;
    /**获取gson*/
    private Button btnGsonRequest;
    /**imageRequest加载图片*/
    private Button btnImageRequest;
    /**imageLoader加载图片*/
    private Button btnImageLoader;
    /**NetworkImageView加载图片*/
    private Button btnImageNetwork;
    private NetworkImageView networkImageView;
    /**显示的图片*/
    private ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化请求队列
        mQueue = Volley.newRequestQueue(this);
        //初始化view
        initView();
        //初始化事件监听
        initListener();
    }

    private void initView() {
        btnStrRequestGet = (Button)this.findViewById(R.id.btn_str_request_get);
        btnStrRequestPost  = (Button)this.findViewById(R.id.btn_str_request_post);
        btnJsonRequestGet  = (Button)this.findViewById(R.id.btn_json_request_get);
        btnImageRequest  = (Button)this.findViewById(R.id.btn_image_request);
        btnImageLoader  = (Button)this.findViewById(R.id.btn_image_loader);
        btnImageNetwork  = (Button)this.findViewById(R.id.btn_image_network);
        btnXmlRequest  = (Button)this.findViewById(R.id.btn_xml_request);
        btnGsonRequest = (Button)this.findViewById(R.id.btn_gson_request);
        ivImage  = (ImageView)this.findViewById(R.id.iv_image);
        networkImageView = (NetworkImageView)this.findViewById(R.id.network_image_view);
    }

    private void initListener() {
        btnStrRequestGet.setOnClickListener(this);
        btnStrRequestPost.setOnClickListener(this);
        btnJsonRequestGet.setOnClickListener(this);
        btnImageRequest.setOnClickListener(this);
        btnImageLoader.setOnClickListener(this);
        btnImageNetwork.setOnClickListener(this);
        btnXmlRequest.setOnClickListener(this);
        btnGsonRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_str_request_get:
                StringRequest stringRequestGet = strRequestGet("http://www.baidu.com");
                mQueue.add(stringRequestGet);
                break;
            case R.id.btn_str_request_post:
                StringRequest stringRequestPost = strRequestPost("http://www.baidu.com");
                mQueue.add(stringRequestPost);
                break;
            case R.id.btn_json_request_get:
                JsonObjectRequest jsonObjectRequest = jsonObjectRequest("http://m.weather.com.cn/data/101010100.html");
                mQueue.add(jsonObjectRequest);
                break;
            case R.id.btn_image_request:
                ImageRequest imageRequest = imageRequest("http://developer.android.com/images/home/aw_dac.png");
                mQueue.add(imageRequest);
                break;
            case R.id.btn_image_loader:
                ImageLoader.ImageListener listener = ImageLoader.getImageListener(ivImage,R.drawable.default_image,R.drawable.failed_image);
                ImageLoader imageLoader = imageLoader();
                imageLoader.get("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg",listener);
                //如果你想对图片的大小进行限制，也可以使用get()方法的重载，指定图片允许的最大宽度和高度,来压缩
//                imageLoader.get("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg",listener, 200, 200);
                break;
            case R.id.btn_image_network:
                //调用它的setDefaultImageResId()方法、setErrorImageResId()方法和setImageUrl()方法来分别设置加载中显示的图片，加载失败时显示的图片，以及目标图片的URL地址
                networkImageView.setDefaultImageResId(R.drawable.default_image);
                networkImageView.setErrorImageResId(R.drawable.failed_image);
                networkImageView.setImageUrl("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg",imageLoader());
                //NetworkImageView是一个控件，在加载图片的时候它会自动获取自身的宽高，然后对比网络图片的宽度，再决定是否需要对图片进行压缩。也就是说，压缩过程是在内部完全自动化的，并不需要我们关心，NetworkImageView会始终呈现给我们一张大小刚刚好的网络图片，不会多占用任何一点内存，这也是NetworkImageView最简单好用的一点吧
                break;
            case R.id.btn_xml_request:
                XMLRequest xmlRequest = xmlRequest("http://flash.weather.com.cn/wmaps/xml/china.xml");
                mQueue.add(xmlRequest);
                break;
            case R.id.btn_gson_request:
                GsonRequest<Weather> gsonRequest = gsonRequest("http://www.weather.com.cn/data/sk/101010100.html");
                mQueue.add(gsonRequest);
                break;
        }
    }

    /**
     * 字符串请求，get方式
     * @param url
     * @return
     */
    private StringRequest strRequestGet(String url){
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                //返回的数据在此
                Log.e(TAG,s + "");
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                //请求出错
                Log.e(TAG, error.getMessage(),error);
            }
        });
        return stringRequest;
    }

    /**
     * 字符串请求，post方式
     * @param url
     * @return
     */
    private StringRequest strRequestPost(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                //返回的数据在此
                Log.e(TAG,s + "");
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                //请求出错
                Log.e(TAG, error.getMessage(),error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //请求参数
                Map<String,String > map = new HashMap<String,String>();
                map.put("params1","value1");
                map.put("params2","value2");
                return map;
            }
        };
        return stringRequest;
    }

    /**
     * 请求json数据
     * @param url
     * @return
     */
    private JsonObjectRequest jsonObjectRequest(String url){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                //返回的json数据
                Log.e(TAG,jsonObject.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //请求出错
                Log.e(TAG, error.getMessage(),error);
            }
        }
        );
        return jsonObjectRequest;

    }

    /**
     * 请求xml数据
     * @param url
     * @return
     */
    private XMLRequest xmlRequest(String url){
        XMLRequest xmlRequest = new XMLRequest(url,new Response.Listener<XmlPullParser>(){
            @Override
            public void onResponse(XmlPullParser response) {
                try {
                    int eventType = response.getEventType();
                    while (eventType != XmlPullParser.END_DOCUMENT){
                        switch (eventType){
                            case XmlPullParser.START_TAG:
                                String nodeName = response.getName();
                                if ("city".equals(nodeName)){
                                    String pName = response.getAttributeValue(0);
                                    Log.e(TAG,"pName is" + pName);
                                }
                                break;
                        }
                        eventType = response.next();
                    }
                }catch (XmlPullParserException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG,volleyError.getMessage(),volleyError);
            }
        });
        return xmlRequest;
    }

    /**
     * gson数据请求
     * @param url
     * @return
     */
    private GsonRequest gsonRequest(String url){
        GsonRequest<Weather> gsonRequest = new GsonRequest<Weather>(url,Weather.class,new Response.Listener<Weather>(){
            @Override
            public void onResponse(Weather weather) {
                WeatherInfo weatherInfo = weather.getWeatherinfo();
                Log.e(TAG,"city is " + weatherInfo.getCity());
                Log.e(TAG,"temp is " + weatherInfo.getTemp());
                Log.e(TAG,"time is " + weatherInfo.getTime());
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG,volleyError.getMessage(),volleyError);
            }
        });
        return gsonRequest;
    }

    /**
     *请求图片
     * @return
     */
    private ImageRequest imageRequest(String url){

        //ImageRequest的构造函数接收六个参数，
        // 第一个参数就是图片的URL地址，这个没什么需要解释的。
        // 第二个参数是图片请求成功的回调，这里我们把返回的Bitmap参数设置到ImageView中。
        // 第三第四个参数分别用于指定允许图片最大的宽度和高度，如果指定的网络图片的宽度或高度大于这里的最大值，则会对图片进行压缩，指定成0的话就表示不管图片有多大，都不会进行压缩。
        // 第五个参数用于指定图片的颜色属性，Bitmap.Config下的几个常量都可以在这里使用，其中ARGB_8888可以展示最好的颜色属性，每个图片像素占据4个字节的大小，而RGB_565则表示每个图片像素占据2个字节大小。
        // 第六个参数是图片请求失败的回调，这里我们当请求失败时在ImageView中显示一张默认图片。
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                Log.e(TAG,"bitmapSize="+bitmap.getByteCount()+"");
                ivImage.setImageBitmap(bitmap);
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ivImage.setImageResource(R.drawable.default_image);
            }
        });
        return imageRequest;
    }

    /**
     * 使用imageLoader加载图片
     * ImageLoader也可以用于加载网络上的图片，并且它的内部也是使用ImageRequest来实现的，不过ImageLoader明显要比ImageRequest更加高效，因为它不仅可以帮我们对图片进行缓存，还可以过滤掉重复的链接，避免重复发送请求。
     * @return
     */
    private ImageLoader imageLoader(){
        ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
        return imageLoader;
    }
}
