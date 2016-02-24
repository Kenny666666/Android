package com.kenny.anim;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Android系统在一开始的时候就给我们提供了两种实现动画效果的方式，逐帧动画(frame-by-frame animation)和补间动画(tweened animation)。逐帧动画的工作原理很简单，其实就是将一个完整的动画拆分成一张张单独的图片，然后再将它们连贯起来进行播放，类似于动画片的工作原理。补间动画则是可以对View进行一系列的动画操作，包括淡入淡出、缩放、平移、旋转四种。
 * 然而自Android 3.0版本开始，系统给我们提供了一种全新的动画模式，属性动画(property animation)，它的功能非常强大，弥补了之前补间动画的一些缺陷，几乎是可以完全替代掉补间动画了。
 * 如果你的需求中只需要对View进行移动、缩放、旋转和淡入淡出操作，那么补间动画确实已经足够健全了。但是很显然，这些功能是不足以覆盖所有的场景的，一旦我们的需求超出了移动、缩放、旋转和淡入淡出这四种对View的操作，那么补间动画就不能再帮我们忙了，也就是说它在功能和可扩展方面都有相当大的局限性，那么下面我们就来看看补间动画所不能胜任的场景。
 * 注意上面我在介绍补间动画的时候都有使用“对View进行操作”这样的描述，没错，补间动画是只能够作用在View上的。也就是说，我们可以对一个Button、TextView、甚至是LinearLayout、或者其它任何继承自View的组件进行动画操作，但是如果我们想要对一个非View的对象进行动画操作，抱歉，补间动画就帮不上忙了。可能有的朋友会感到不能理解，我怎么会需要对一个非View的对象进行动画操作呢？这里我举一个简单的例子，比如说我们有一个自定义的View，在这个View当中有一个Point对象用于管理坐标，然后在onDraw()方法当中就是根据这个Point对象的坐标值来进行绘制的。也就是说，如果我们可以对Point对象进行动画操作，那么整个自定义View的动画效果就有了。显然，补间动画是不具备这个功能的，这是它的第一个缺陷。
 * 然后补间动画还有一个缺陷，就是它只能够实现移动、缩放、旋转和淡入淡出这四种动画操作，那如果我们希望可以对View的背景色进行动态地改变呢？很遗憾，我们只能靠自己去实现了。说白了，之前的补间动画机制就是使用硬编码的方式来完成的，功能限定死就是这些，基本上没有任何扩展性可言。
 * 最后，补间动画还有一个致命的缺陷，就是它只是改变了View的显示效果而已，而不会真正去改变View的属性。什么意思呢？比如说，现在屏幕的左上角有一个按钮，然后我们通过补间动画将它移动到了屏幕的右下角，现在你可以去尝试点击一下这个按钮，点击事件是绝对不会触发的，因为实际上这个按钮还是停留在屏幕的左上角，只不过补间动画将这个按钮绘制到了屏幕的右下角而已。
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnAdvancedAnim;//属性动画高级用法
    private Button btnValueAnim;//value动画演示，看后台打印
    private Button btnObjectAnim_alpha;//对象动画  之透明
    private Button btnObjectAnim_rotation;//对象动画  之旋转
    private Button btnObjectAnim_translation;//对象动画  之移动
    private Button btnObjectAnim_scale;//对象动画  之绽放
    private Button btnObjectAnim_team;//对象动画  之组合动画
    private Button btnAnimListener;//动画监听
    private Button btnAnimXml;//动画xml文件实现 value过度改变
    private Button btnAnimXml1;//动画xml文件实现 透明
    private Button btnAnimXml2;//动画xml文件实现 组合

    private TextView tvObjectAnim;//对象动画测试


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdvancedAnim = (Button) findViewById(R.id.btn_anim_advanced);
        btnAdvancedAnim.setOnClickListener(this);
        btnValueAnim = (Button) findViewById(R.id.btn_value_anim);
        btnValueAnim.setOnClickListener(this);
        btnObjectAnim_alpha = (Button) findViewById(R.id.btn_object_anim_alpha);
        btnObjectAnim_alpha.setOnClickListener(this);
        btnObjectAnim_rotation = (Button) findViewById(R.id.btn_object_anim_rotation);
        btnObjectAnim_rotation.setOnClickListener(this);
        btnObjectAnim_translation = (Button) findViewById(R.id.btn_object_anim_translation);
        btnObjectAnim_translation.setOnClickListener(this);
        btnObjectAnim_scale = (Button) findViewById(R.id.btn_object_anim_scale);
        btnObjectAnim_scale.setOnClickListener(this);
        btnObjectAnim_team = (Button) findViewById(R.id.btn_object_anim_team);
        btnObjectAnim_team.setOnClickListener(this);
        btnAnimListener = (Button) findViewById(R.id.btn_anim_listener);
        btnAnimListener.setOnClickListener(this);
        btnAnimXml = (Button) findViewById(R.id.btn_anim_xml);
        btnAnimXml.setOnClickListener(this);
        btnAnimXml1 = (Button) findViewById(R.id.btn_anim_xml1);
        btnAnimXml1.setOnClickListener(this);
        btnAnimXml2 = (Button) findViewById(R.id.btn_anim_xml2);
        btnAnimXml2.setOnClickListener(this);

        tvObjectAnim = (TextView) findViewById(R.id.tv_object_anim);

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_anim_advanced://进入属性动画高级用法

                startActivity(new Intent(MainActivity.this,ToActivity.class));

                break;
            case R.id.btn_value_anim://value动画演示，看后台打印

                valueAnimatorTest();

                break;
            case R.id.btn_object_anim_alpha://透明

                objectAnimatorTest1();

                break;
            case R.id.btn_object_anim_rotation://旋转

                objectAnimatorTest2();

                break;
            case R.id.btn_object_anim_translation://移动

                objectAnimatorTest3();

                break;
            case R.id.btn_object_anim_scale://绽放

                objectAnimatorTest4();

                break;
            case R.id.btn_object_anim_team://组合动画

                objectAnimatorTest5();

                break;
            case R.id.btn_anim_listener://动画监听

                animatorListener();

                break;
            case R.id.btn_anim_xml://动画xml文件实现 value过度改变

                animatorXml();

                break;
            case R.id.btn_anim_xml1://动画xml文件实现 透明

                animatorXml1();

                break;
            case R.id.btn_anim_xml2://动画xml文件实现 组合

                animatorXml2();

                break;
        }
    }

    private void valueAnimatorTest(){
        //ValueAnimator是整个属性动画机制当中最核心的一个类，前面我们已经提到了，属性动画的运行机制是通过不断地对值进行操作来实现的，而初始值和结束值之间的动画过渡就是由ValueAnimator这个类来负责计算的。它的内部使用一种时间循环的机制来计算值与值之间的动画过渡，我们只需要将初始值和结束值提供给ValueAnimator，并且告诉它动画所需运行的时长，那么ValueAnimator就会自动帮我们完成从初始值平滑地过渡到结束值这样的效果。除此之外，ValueAnimator还负责管理动画的播放次数、播放模式、以及对动画设置监听器等，确实是一个非常重要的类。
        ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
//      ValueAnimator anim = ValueAnimator.ofInt(0, 100);
        anim.setDuration(300);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                Log.e("TAG", "cuurent value is " + currentValue);
            }
        });
        anim.start();
        //从打印日志的值我们就可以看出，ValueAnimator确实已经在正常工作了，值在300毫秒的时间内从0平滑过渡到了1，而这个计算工作就是由ValueAnimator帮助我们完成的。另外ofFloat()方法当中是可以传入任意多个参数的，因此我们还可以构建出更加复杂的动画逻辑，比如说将一个值在5秒内从0过渡到5，再过渡到3，再过渡到10，就可以这样写：
        //那么除此之外，我们还可以调用setStartDelay()方法来设置动画延迟播放的时间，调用setRepeatCount()和setRepeatMode()方法来设置动画循环播放的次数以及循环播放的模式，循环模式包括RESTART和REVERSE两种，分别表示重新播放和倒序播放的意思。这些方法都很简单，我就不再进行详细讲解了。
//                ValueAnimator anim = ValueAnimator.ofFloat(0f, 5f, 3f, 10f);
//                anim.setDuration(5000);
//                anim.start();
    }

    /**透明*/
    private void objectAnimatorTest1(){
        //ObjectAnimator
        //相比于ValueAnimator，ObjectAnimator可能才是我们最常接触到的类，因为ValueAnimator只不过是对值进行了一个平滑的动画过渡，但我们实际使用到这种功能的场景好像并不多。而ObjectAnimator则就不同了，它是可以直接对任意对象的任意属性进行动画操作的，比如说View的alpha属性。
        //不过虽说ObjectAnimator会更加常用一些，但是它其实是继承自ValueAnimator的，底层的动画实现机制也是基于ValueAnimator来完成的，因此ValueAnimator仍然是整个属性动画当中最核心的一个类。那么既然是继承关系，说明ValueAnimator中可以使用的方法在ObjectAnimator中也是可以正常使用的，它们的用法也非常类似，这里如果我们想要将一个TextView在5秒中内从常规变换成全透明，再从全透明变换成常规，就可以这样写：
        ObjectAnimator animator = ObjectAnimator.ofFloat(tvObjectAnim, "alpha", 1f, 0f, 1f);
        animator.setDuration(5000);
        animator.start();
        //可以看到，我们还是调用了ofFloat()方法来去创建一个ObjectAnimator的实例，只不过ofFloat()方法当中接收的参数有点变化了。这里第一个参数要求传入一个object对象，我们想要对哪个对象进行动画操作就传入什么，这里我传入了一个textview。第二个参数是想要对该对象的哪个属性进行动画操作，由于我们想要改变TextView的不透明度，因此这里传入"alpha"。后面的参数就是不固定长度了，想要完成什么样的动画就传入什么值，这里传入的值就表示将TextView从常规变换成全透明，再从全透明变换成常规。之后调用setDuration()方法来设置动画的时长，然后调用start()方法启动动画
    }
    /**旋转*/
    private void objectAnimatorTest2(){

        //可以看到，这里我们将第二个参数改成了"rotation"，然后将动画的初始值和结束值分别设置成0和360
        ObjectAnimator animator = ObjectAnimator.ofFloat(tvObjectAnim, "rotation", 0f, 360f);
        animator.setDuration(5000);
        animator.start();
    }
    /**平移*/
    private void objectAnimatorTest3(){

        //那么如果想要将TextView先向左移出屏幕，然后再移动回来
        float curTranslationX = tvObjectAnim.getTranslationX();
        ObjectAnimator animator = ObjectAnimator.ofFloat(tvObjectAnim, "translationX", curTranslationX, -500f, curTranslationX);
        animator.setDuration(5000);
        animator.start();
    }
    /**绽放*/
    private void objectAnimatorTest4(){

        //然后我们还可以TextView进行缩放操作，比如说将TextView在垂直方向上放大3倍再还原
        ObjectAnimator animator = ObjectAnimator.ofFloat(tvObjectAnim, "scaleY", 1f, 3f, 1f);
        animator.setDuration(5000);
        animator.start();

        //到目前为止，ObjectAnimator的用法还算是相当简单吧，但是我相信肯定会有不少朋友现在心里都有同样一个疑问，就是ofFloat()方法的第二个参数到底可以传哪些值呢？目前我们使用过了alpha、rotation、translationX和scaleY这几个值，分别可以完成淡入淡出、旋转、水平移动、垂直缩放这几种动画，那么还有哪些值是可以使用的呢？其实这个问题的答案非常玄乎，就是我们可以传入任意的值到ofFloat()方法的第二个参数当中。任意的值？相信这很出乎大家的意料吧，但事实就是如此。因为ObjectAnimator在设计的时候就没有针对于View来进行设计，而是针对于任意对象的，它所负责的工作就是不断地向某个对象中的某个属性进行赋值，然后对象根据属性值的改变再来决定如何展现出来。
        // 那么比如说我们调用下面这样一段代码：
        //ObjectAnimator.ofFloat(textview, "alpha", 1f, 0f);

        //其实这段代码的意思就是ObjectAnimator会帮我们不断地改变textview对象中alpha属性的值，从1f变化到0f。然后textview对象需要根据alpha属性值的改变来不断刷新界面的显示，从而让用户可以看出淡入淡出的动画效果。
        //那么textview对象中是不是有alpha属性这个值呢？没有，不仅textview没有这个属性，连它所有的父类也是没有这个属性的！这就奇怪了，textview当中并没有alpha这个属性，ObjectAnimator是如何进行操作的呢？其实ObjectAnimator内部的工作机制并不是直接对我们传入的属性名进行操作的，而是会去寻找这个属性名对应的get和set方法，因此alpha属性所对应的get和set方法应该就是：
        //public void setAlpha(float value);
        //public float getAlpha();

        //那么textview对象中是否有这两个方法呢？确实有，并且这两个方法是由View对象提供的，也就是说不仅TextView可以使用这个属性来进行淡入淡出动画操作，任何继承自View的对象都可以的。
        //既然alpha是这个样子，相信大家一定已经明白了，前面我们所用的所有属性都是这个工作原理，那么View当中一定也存在着setRotation()、getRotation()、setTranslationX()、getTranslationX()、setScaleY()、getScaleY()这些方法，不信的话你可以到View当中去找一下。

    }
    /**组合动画*/
    private void objectAnimatorTest5(){
        ObjectAnimator moveIn = ObjectAnimator.ofFloat(tvObjectAnim, "translationX", -500f, 0f);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(tvObjectAnim, "rotation", 0f, 360f);
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(tvObjectAnim, "alpha", 1f, 0f, 1f);

        //实现组合动画功能主要需要借助AnimatorSet这个类，这个类提供了一个play()方法，如果我们向这个方法中传入一个Animator对象(ValueAnimator或ObjectAnimator)将会返回一个AnimatorSet.Builder的实例，AnimatorSet.Builder中包括以下四个方法：
        //after(Animator anim)   将现有动画插入到传入的动画之后执行
        //after(long delay)   将现有动画延迟指定毫秒后执行
        //before(Animator anim)   将现有动画插入到传入的动画之前执行
        //with(Animator anim)   将现有动画和传入的动画同时执行
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(rotate).with(fadeInOut).after(moveIn);
        animSet.setDuration(5000);
        animSet.start();
    }
    /**Animator监听器*/
    private void animatorListener(){
        //Animator监听器
        //在很多时候，我们希望可以监听到动画的各种事件，比如动画何时开始，何时结束，然后在开始或者结束的时候去执行一些逻辑处理。这个功能是完全可以实现的，Animator类当中提供了一个addListener()方法，这个方法接收一个AnimatorListener，我们只需要去实现这个AnimatorListener就可以监听动画的各种事件了。
        //大家已经知道，ObjectAnimator是继承自ValueAnimator的，而ValueAnimator又是继承自Animator的，因此不管是ValueAnimator还是ObjectAnimator都是可以使用addListener()这个方法的。另外AnimatorSet也是继承自Animator的，因此addListener()这个方法算是个通用的方法。
        ObjectAnimator animator = ObjectAnimator.ofFloat(tvObjectAnim, "scaleY", 1f, 3f, 1f);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Toast.makeText(getApplicationContext(),"动画开始",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Toast.makeText(getApplicationContext(),"动画重播",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Toast.makeText(getApplicationContext(),"动画结束",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Toast.makeText(getApplicationContext(),"动画取消",Toast.LENGTH_SHORT).show();
            }
        });
        animator.setDuration(5000);
        animator.start();

        //但是也许很多时候我们并不想要监听那么多个事件，可能我只想要监听动画结束这一个事件，那么每次都要将四个接口全部实现一遍就显得非常繁琐。没关系，为此Android提供了一个适配器类，叫作AnimatorListenerAdapter，使用这个类就可以解决掉实现接口繁琐的问题了，如下所示：
        //这里我们向addListener()方法中传入这个适配器对象，由于AnimatorListenerAdapter中已经将每个接口都实现好了，所以这里不用实现任何一个方法也不会报错。那么如果我想监听动画结束这个事件，就只需要单独重写这一个方法就可以了，如下所示：
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Toast.makeText(getApplicationContext(), "动画结束", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**动画xml文件实现*/
    private void animatorXml(){
        //如果想要使用XML来编写动画，首先要在res目录下面新建一个animator文件夹，所有属性动画的XML文件都应该存放在这个文件夹当中。然后在XML文件中我们一共可以使用如下三种标签：
        //<animator>  对应代码中的ValueAnimator
        //<objectAnimator>  对应代码中的ObjectAnimator
        //<set>  对应代码中的AnimatorSet
        Animator animator = AnimatorInflater.loadAnimator(MainActivity.this, R.animator.anim_value);
        animator.setTarget(tvObjectAnim);
        animator.start();
    }
    /**动画xml文件实现 透明*/
    private void animatorXml1(){

        Animator animator = AnimatorInflater.loadAnimator(MainActivity.this, R.animator.anim_alpha);
        animator.setTarget(tvObjectAnim);
        animator.start();
    }
    /**动画xml文件实现 组合*/
    private void animatorXml2(){

        Animator animator = AnimatorInflater.loadAnimator(MainActivity.this, R.animator.anim_team);
        animator.setTarget(tvObjectAnim);
        animator.start();
    }
}
