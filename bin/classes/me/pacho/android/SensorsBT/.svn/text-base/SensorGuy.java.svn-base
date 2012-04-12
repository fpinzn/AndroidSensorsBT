package me.pacho.android.PathDrawer2D;

import java.util.ArrayList;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;


public class SensorGuy implements SensorEventListener{

    private Sensor mAccelerometer;
    private Sensor mGyroscope;
    private Sensor mGravity;
	private SensorManager mSensorManager;


    
    private float[] lastGravity=new float[3];
    private float[] lastGyroscope=new float[3];
    private float[] lastAcceleration=new float[3];
    
    public SensorGuy(){
    	Drawer.getContext();
    	if(Drawer.getContext()==null){
    		Log.e("Context NULL","CONTEXT NULL");
    	}
        mSensorManager = (SensorManager) Drawer.getContext().getSystemService(Context.SENSOR_SERVICE);
    
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
       
        mGyroscope=mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_FASTEST);
        
        mGravity=mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mSensorManager.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_FASTEST);

    }
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	 public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
        	copyArray(lastAcceleration,event.values);
       	
        }
        else if(event.sensor.getType() ==Sensor.TYPE_GYROSCOPE){
        	copyArray(lastGyroscope,event.values);


        }
        else if (event.sensor.getType()== Sensor.TYPE_GRAVITY){
        	copyArray(lastGravity,event.values);
        }
     
        else return;
		
    }


	
	private void copyArray(float[] destination, float[] source) {
		System.arraycopy(source, 0, destination, 0, 3);
	}
	public float[] getAcceleration(){
		return lastAcceleration;
	}	

	public float[] getGravity(){
		return lastGravity;
	}
	public float[] getGyroscope(){
		return new float[]{((Math.round((Math.toDegrees(lastGyroscope[0])) *2)/2)+360)%360,
							((Math.round((Math.toDegrees(lastGyroscope[1])) *2)/2)+360)%360,
							((Math.round((Math.toDegrees(lastGyroscope[2])) *2)/2)+360)%360};
	}

}
