package me.pacho.android.SensorsBT;








import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import me.pacho.android.SensorsBT.R;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class SensorsBT extends Activity {

	// Debugging
    private static final String TAG = "SensorsBT//";
    private static final boolean D = true;
    
	private static final int REQUEST_ENABLE_BT = 2;
	private static final int REQUEST_CONNECT_DEVICE = 3;


	public static final String MY_UUID="ac3a2ba0-858a-11e1-b0c4-0800200c9a66";
    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Name of the connected device
    private String mConnectedDeviceName = null;
    
    // Member object for the chat services
	private static SensorGuy sensorGuy=null;

	private static Context CONTEXT;
	private Set<String> mArrayAdapter=new HashSet<String>();
	
	//UI
	private TextView tv_Title=null;
	private TextView tv_Msg=null;
	
	private DeviceListActivity deviceList;
	private BluetoothAdapter mBluetoothAdapter;
   private BTGuy btguy;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.main);
        tv_Title=(TextView) findViewById(R.id.tv_Title);
        tv_Title=(TextView) findViewById(R.id.tv_Msg);
        CONTEXT=this;
        
        
        //Set up BT
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
        	killApp("Bluetooth not avalaible in device.");
        }
        
        
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            Log.i(TAG, "BT is not up bitches");

        }
        Log.i(TAG, "BT is UP:"+String.valueOf(mBluetoothAdapter.isEnabled()));
        //BT exists and it's up
        Intent serverIntent = new Intent(this, DeviceListActivity.class);
        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
        
        //Show Option to select from devices
        
	 	}
    


    public void onActivityResult (int requestCode, int resultCode, Intent data){
    	switch(requestCode){
    		case REQUEST_ENABLE_BT:
    			if(resultCode==RESULT_OK){
    				if(D)Log.i(TAG+"OnActivityResult","REQUEST_ENABLE_BT"+":"+"OK");
    			}
    			else killApp("Bluetooth access denied by user.");
    			break;
    		case REQUEST_CONNECT_DEVICE:
    			if(resultCode==RESULT_OK){
    				if(D)Log.i(TAG+"OnActivityResult","REQUEST_CONNECT_DEVICE"+":"+"OK");
    				discoverServices(data);


    			}
    			else killApp("Bluetooth permission to connect to device denied by user.");
    			break;
    	}
    }
    
    private void discoverServices(Intent data) {
        // Get the device MAC address
        String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if(!device.fetchUuidsWithSdp())
        	killApp("Could not start service search.");
      
        connectDevice(data);
       
    }



	public static void killApp(String msg) {
    	if(D)Log.e(TAG+"KillApp",msg);
    	//Matar app por que no hay bt o no esta activado.
	}

    
 // The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_STATE_CHANGE:
                if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                switch (msg.arg1) {
                case BTGuy.STATE_CONNECTED:
                    setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                    
                    break;
                case BTGuy.STATE_CONNECTING:
                    setStatus("Connecting");
                    break;
                case BTGuy.STATE_LISTEN:
                case BTGuy.STATE_NONE:
                    setStatus("Not connected");
                    break;
                }
                break;
            case MESSAGE_WRITE:
                byte[] writeBuf = (byte[]) msg.obj;
                // construct a string from the buffer
                String writeMessage = new String(writeBuf);
                break;
            case MESSAGE_READ:
                byte[] readBuf = (byte[]) msg.obj;
                // construct a string from the valid bytes in the buffer
                String readMessage = new String(readBuf, 0, msg.arg1);
                break;
            case MESSAGE_DEVICE_NAME:
                // save the connected device's name
                mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                Toast.makeText(getApplicationContext(), "Connected to " + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_TOAST:
                Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                               Toast.LENGTH_SHORT).show();
                break;
            }
        }
    };
    
    private void connectDevice(Intent data) {
        // Get the device MAC address
        String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        ParcelUuid[] u=device.getUuids();
        Log.i(TAG, "C :"+device.describeContents());
        for(int i=0;i<u.length;i++){
        	Log.i(TAG, "U :"+u[i].toString());
        }
        // Attempt to connect to the device
        //btguy=new BTGuy(this, mHandler);
        //btguy.connect(device);
        try {
			BluetoothSocket tmp=device.createInsecureRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
			tmp.connect();
			tmp.getOutputStream().write("hola babylin\\".getBytes("UTF-8"));
			byte[] buffer=new byte[256];
			tmp.getInputStream().read(buffer);
			Log.i(TAG, "MSG RECEIVED:"+new String(buffer));
			tmp.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			killApp(e.getMessage());
		}
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
    
    
    
 
    private final void setStatus(CharSequence subTitle) {
    	tv_Title.setText(subTitle);
    }
    
    

}