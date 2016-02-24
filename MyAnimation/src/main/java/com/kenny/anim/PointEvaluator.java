package com.kenny.anim;

import android.animation.TypeEvaluator;

/**
 * 自定
 */
public class PointEvaluator implements TypeEvaluator{

	/**
	 * 可以看到，FloatEvaluator实现了TypeEvaluator接口，然后重写evaluate()方法。evaluate()方法当中传入了三个参数，第一个参数fraction非常重要，这个参数用于表示动画的完成度的，我们应该根据它来计算当前动画的值应该是多少，第二第三个参数分别表示动画的初始值和结束值。那么上述代码的逻辑就比较清晰了，用结束值减去初始值，算出它们之间的差值，然后乘以fraction这个系数，再加上初始值，那么就得到当前动画的值了。
	 好的，那FloatEvaluator是系统内置好的功能，并不需要我们自己去编写，但介绍它的实现方法是要为我们后面的功能铺路的。前面我们使用过了ValueAnimator的ofFloat()和ofInt()方法，分别用于对浮点型和整型的数据进行动画操作的，但实际上ValueAnimator中还有一个ofObject()方法，是用于对任意对象进行动画操作的。但是相比于浮点型或整型数据，对象的动画操作明显要更复杂一些，因为系统将完全无法知道如何从初始对象过度到结束对象，因此这个时候我们就需要实现一个自己的TypeEvaluator来告知系统如何进行过度。
	 可以看到，PointEvaluator同样实现了TypeEvaluator接口并重写了evaluate()方法。其实evaluate()方法中的逻辑还是非常简单的，先是将startValue和endValue强转成Point对象，然后同样根据fraction来计算当前动画的x和y的值，最后组装到一个新的Point对象当中并返回。
	 基本上还是很简单的，总共也没几行代码。首先在自定义View的构造方法当中初始化了一个Paint对象作为画笔，并将画笔颜色设置为蓝色，接着在onDraw()方法当中进行绘制。这里我们绘制的逻辑是由currentPoint这个对象控制的，如果currentPoint对象不等于空，那么就调用drawCircle()方法在currentPoint的坐标位置画出一个半径为50的圆，如果currentPoint对象是空，那么就调用startAnimation()方法来启动动画。
	 那么我们来观察一下startAnimation()方法中的代码，其实大家应该很熟悉了，就是对Point对象进行了一个动画操作而已。这里我们定义了一个startPoint和一个endPoint，坐标分别是View的左上角和右下角，并将动画的时长设为5秒。然后有一点需要大家注意的，就是我们通过监听器对动画的过程进行了监听，每当Point值有改变的时候都会回调onAnimationUpdate()方法。在这个方法当中，我们对currentPoint对象进行了重新赋值，并调用了invalidate()方法，这样的话onDraw()方法就会重新调用，并且由于currentPoint对象的坐标已经改变了，那么绘制的位置也会改变，于是一个平移的动画效果也就实现了。

	 * @param fraction
	 * @param startValue
	 * @param endValue
	 * @return
	 */
	@Override
	public Object evaluate(float fraction, Object startValue, Object endValue) {
		Point startPoint = (Point) startValue;
		Point endPoint = (Point) endValue;
		float x = startPoint.getX()+fraction*(endPoint.getX()-startPoint.getX());
		float y = startPoint.getY()+fraction*(endPoint.getY()-startPoint.getY());
		Point point = new Point(x, y);
		return point;
	}
}
