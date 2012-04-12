package me.pacho.android.SensorsBT;

import me.pacho.android.SensorsBT.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class SensorsBT extends Activity {
	private static Context CONTEXT;
	private static SensorGuy sensorGuy;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        CONTEXT=this;
    }
    
    @Override
    public void onResume(){
    	super.onResume();
        sensorGuy=new SensorGuy();
    }
    public static SensorGuy getSensorGuy(){
    	return sensorGuy;
    }

    
    public static Context getContext() {
		return CONTEXT;
	}
}