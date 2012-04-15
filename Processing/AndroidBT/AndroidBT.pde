
import bluetoothDesktop.*;

PFont font;
Bluetooth bt;
ArrayList clients = new ArrayList();
String msg="";

void setup() {
  font = createFont("Courier", 15);
  textFont(font);
  try {
    bt = new Bluetooth(this,"ac3a2ba0858a11e1b0c40800200c9a66"); // we go with the standard uuid, so mobile processing sketches can easily connect
    bt.start("Sensor Data Server");  // Start the service
  } 
  catch (RuntimeException e) {
    println("bluetooth device is not available. (or license problem if using avetana)"); 
    println(e);
  }
}



// callback for the bluetooth library
// gets called when a new client connects
void clientConnectEvent(Client c) {
  clients.add(c);
  println("new client: " + c.device.name);
  int cha=c.read();
  while(cha!='\\'){  
    msg=msg+char(cha);
    cha=c.read();
  }
  println(msg);
  c.writeUTF("Hola lola");
}

