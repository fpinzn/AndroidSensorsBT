package me.pacho.android.PathDrawer2D;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class Panel extends SurfaceView implements SurfaceHolder.Callback {
	private CanvasThread canvasthread;
	private SensorGuy sensorGuy;
	private int count=0;
	private int[] screenSize=new int[2]; 
	
	public Panel(Context context, AttributeSet attrs) {
          super(context, attrs);
      getHolder().addCallback(this);
      canvasthread = new CanvasThread(getHolder(), this);
      setFocusable(true);
      

    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                    int height) {
            // TODO Auto-generated method stub
           
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
            // TODO Auto-generated method stub
        canvasthread.setRunning(true);
        canvasthread.start();
        sensorGuy=Drawer.getSensorGuy();

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
            // TODO Auto-generated method stub
            boolean retry = true;
            canvasthread.setRunning(false);
            while (retry) {
                    try {
                            canvasthread.join();
                            retry = false;
                    } catch (InterruptedException e) {
                            // we will try it again and again...
                    }
            }

    }
    
    @Override
    public void onDraw(Canvas canvas) {
        screenSize[0]=canvas.getWidth();
        screenSize[1]=canvas.getHeight();
        
        float[] acceleration=sensorGuy.getAcceleration();        
        float scale_accel=screenSize[1]/45;
        int zero_accel=(int)(screenSize[1]*0.25);
        
        
        float[] orientation=sensorGuy.getGyroscope();
        float scale_orientation=screenSize[1]/720;
        int zero_orientation=(int)(screenSize[1]*0.75);
        
        //BG Operations
	        Paint paint = new Paint();
	        paint.setColor(Color.BLACK);
	
	    	if(count==0){
	    		canvas.drawRect(0,0,screenSize[0],screenSize[1],paint);
	
	    	}
	    	paint.setColor(Color.WHITE);
	    	canvas.drawLine(0, screenSize[1]/2, screenSize[0], screenSize[1]/2, paint);
	        canvas.drawText("Accelerometer(m/s^2)", 5,screenSize[1]/2-5, paint);
	        canvas.drawText("Gyroscope(deg/s)",screenSize[0]-90 ,screenSize[1]/2+15, paint);
	
	    	canvas.drawLine(0, zero_accel, screenSize[0], zero_accel, paint);
	
	        canvas.drawRect(0,0,screenSize[0],25, paint);
	        paint.setColor(Color.BLACK);
	        canvas.drawText("Count: "+count, 5,20, paint);
       
      //Accelerometer  
	      //x
	        paint.setColor(Color.RED);
	        canvas.drawCircle(count, acceleration[0]*scale_accel+zero_accel,2f, paint);
	      //y
	        paint.setColor(Color.BLUE);
	        canvas.drawCircle(count, acceleration[1]*scale_accel+zero_accel,2f, paint);
	      //z
	        paint.setColor(Color.GREEN);
	        canvas.drawCircle(count, acceleration[2]*scale_accel+zero_accel,2f, paint);
	        
	 //Orientation
		  //x
	        paint.setColor(Color.MAGENTA);
	        canvas.drawCircle(count, -(orientation[0]-180)*scale_orientation+zero_orientation,2f, paint);
	      //y
	        paint.setColor(Color.CYAN);
	        canvas.drawCircle(count, -(orientation[1]-180)*scale_orientation+zero_orientation,2f, paint);
	      //z
	        paint.setColor(Color.YELLOW);
	        canvas.drawCircle(count,- (orientation[2]-180)*scale_orientation+zero_orientation,2f, paint);
         
        
        
        count++;
        if(count>screenSize[0]){
        	count=0;
        }
   }
     
}