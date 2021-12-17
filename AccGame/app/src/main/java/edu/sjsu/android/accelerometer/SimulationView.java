package edu.sjsu.android.accelerometer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;

public class SimulationView extends View implements SensorEventListener {

    //Define SensorManager, Sensor, and Display
    SensorManager mSensorManager;
    Sensor mSensor;
    Display mDisplay;

    private Bitmap mField;
    private Bitmap mBasket;
    private Bitmap mBitmap;
    private static final int BALL_SIZE = 100;
    private static final int BASKET_SIZE = 200;

    private float mXOrigin;
    private float mYOrigin;
    private float mHorizontalBound;
    private float mVerticalBound;

    float x;
    float y;
    float z;
    long time_stamp;

    Particle mBall = new Particle();

    public SimulationView(Context context) {
        super(context);
        Bitmap ball = BitmapFactory.decodeResource(getResources(), R.drawable.basketball);
        mBitmap = Bitmap.createScaledBitmap(ball, BALL_SIZE, BALL_SIZE, true);

        Bitmap basket = BitmapFactory.decodeResource(getResources(), R.drawable.hoop);
        mBasket = Bitmap.createScaledBitmap(basket, BASKET_SIZE, BASKET_SIZE, true);


        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inDither = true;
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap field = BitmapFactory.decodeResource(getResources(), R.drawable.court, opts);
        mField = Bitmap.createScaledBitmap(field, 1200, 1800, true);

        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mDisplay = mWindowManager.getDefaultDisplay();

        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }


    public void startSimulation()
    {
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopSimulation(){
        mSensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (mDisplay.getRotation() == Surface.ROTATION_0){
            x = event.values[0];
            y = event.values[1];
        }

        else if (mDisplay.getRotation() == Surface.ROTATION_90){
            x = -event.values[1];
            y = event.values[0];
        }
        else if(mDisplay.getRotation() == Surface.ROTATION_180){
            x = -event.values[0];
            y = -event.values[1];
        }
        else if(mDisplay.getRotation() == Surface.ROTATION_270){
            x = event.values[1];
            y = -event.values[0];
        }

        z = event.values[2];
        time_stamp = event.timestamp;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onSizeChanged (int w, int h, int oldw, int oldh){
        float change = .5f;

        mXOrigin = w * change;
        mYOrigin = h * change;

        mHorizontalBound = (w - BALL_SIZE) * change;
        mVerticalBound = (h - BALL_SIZE) * change;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mField, 0, 0, null);
        canvas.drawBitmap(mBasket, mXOrigin - BASKET_SIZE / 2, 0, null);
        mBall.updatePosition(x, y, z, time_stamp);
        mBall.resolveCollisionWithBounds(mHorizontalBound, mVerticalBound);
        canvas.drawBitmap(mBitmap,(mXOrigin - BALL_SIZE/2) + mBall.mPosX, (mYOrigin - BALL_SIZE/2) - mBall.mPosY,null);
        invalidate();
    }


}
