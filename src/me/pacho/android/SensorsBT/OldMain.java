package me.pacho.android.SensorsBT;

import me.pacho.android.SensorsBT.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class OldMain extends Activity {
	private static Context CONTEXT;
	private static SensorGuy sensorGuy;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
      //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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