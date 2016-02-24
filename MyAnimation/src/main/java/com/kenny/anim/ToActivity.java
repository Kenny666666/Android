package com.kenny.anim;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 我们学习了Android属性动画的基本用法，当然也是最常用的一些用法，这些用法足以覆盖我们平时大多情况下的动画需求了。但是，正如上篇文章当中所说到的，属性动画对补间动画进行了很大幅度的改进，之前补间动画可以做到的属性动画也能做到，补间动画做不到的现在属性动画也可以做到了。因此，今天我们就来学习一下属性动画的高级用法，看看如何实现一些补间动画所无法实现的功能。
 * 补间动画是只能对View对象进行动画操作的。而属性动画就不再受这个限制，它可以对任意对象进行动画操作。那么大家应该还记得在上篇文章当中我举的一个例子，比如说我们有一个自定义的View，在这个View当中有一个Point对象用于管理坐标，然后在onDraw()方法当中就是根据这个Point对象的坐标值来进行绘制的。也就是说，如果我们可以对Point对象进行动画操作，那么整个自定义View的动画效果就有了。OK，下面我们就来学习一下如何实现这样的效果。

 在开始动手之前，我们还需要掌握另外一个知识点，就是TypeEvaluator的用法。可能在大多数情况下我们使用属性动画的时候都不会用到TypeEvaluator，但是大家还是应该了解一下它的用法，以防止当我们遇到一些解决不掉的问题时能够想起来还有这样的一种解决方案。

 */
public class ToActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toactivity);

        MyAnimView myAnimView = (MyAnimView) this.findViewById(R.id.myview);
    }
}
