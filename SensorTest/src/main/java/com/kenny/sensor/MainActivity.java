package com.kenny.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * 传感器（在使用传感器时，记住一定要先获取设备支持哪些传感器，以免没效果）
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView tvTitle;

    private SensorManager mSensorManager;
    //重力传感器获取的值
    private float[] gravity = new float[3];

    /**震动器*/
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = (TextView) findViewById(R.id.tv_title);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        vibrator = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
    }

    /**
     * 在界面能获取到焦点的时候才注册最好
     */
    @Override
    protected void onResume() {
        super.onResume();
        /**注册时根据不同的需求注册不同的类型及采样频率*/

        //注册了一个加速度传感器，1、this   2、给哪个传感器注册  3、传感器采样频率(这里可以使用UI的,相对比较慢(分很多种：游戏、UI、))
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
        //注册了一个重力传感器，1、this   2、给哪个传感器注册  3、传感器采样频率(这里可以使用最快的,相对比较快(分很多种：游戏、UI、))
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_FASTEST);
    }

    /**
     * 传感器的数据发生变化时的回调
     *
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER://加速度传感器
                //加速度传感器去杂音方法(利用重力传感器)，为什么要去杂音呢？因为在设备处于不动状态时他的三个值会不为0偏差比较大，为了达到值的精确度所以要去杂音
                final float alpha = (float) 0.8;
                gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
                gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
                gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

                //Z: 9.81m/s^2
                String accelerometer = "加速度\n" + "X:" + (event.values[0] - gravity[0]) + "\n" + "Y:" + (event.values[1] - gravity[1]) + "\n" + "Z:" + (event.values[2] - gravity[2]);

                Log.e("z", String.valueOf(event.values[2]-gravity[2]));
                tvTitle.setText(accelerometer);

                float x = event.values[0] - gravity[0];
                System.out.println(x);
                if (x > 15) {
                    System.out.println("摇晃速度已经大于15，让手机震动5秒");
                    vibrator.vibrate(5000);
                }

                break;
            case Sensor.TYPE_GRAVITY://重力传感器

                gravity[0] = event.values[0];
                gravity[1] = event.values[1];
                gravity[2] = event.values[2];

                break;
        }
    }

    /**
     * 传感器精度发生变化时的回调
     *
     * @param sensor
     * @param accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        //注销传感器
        mSensorManager.unregisterListener(this);
    }
}
