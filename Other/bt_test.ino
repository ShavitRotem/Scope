#include <SoftwareSerial.h>  //Software Serial Port
#define RxD 1
#define TxD 0

SoftwareSerial blueToothSerial(RxD,TxD);



void setup() {
  // put your setup code here, to run once:
//  blueToothSerial.begin(9600);
  
  setupBlueToothConnection();

}

void loop() {
  // put your main code here, to run repeatedly:
  delay(1000);
  blueToothSerial.print("The time passed is ");
  blueToothSerial.print(millis());
  blueToothSerial.println("ms");
}

void setupBlueToothConnection()
{
  blueToothSerial.begin(9600); //Set BluetoothBee BaudRate to default baud rate 38400
//  blueToothSerial.print("\r\n+STWMOD=0\r\n"); //set the bluetooth work in slave mode
  blueToothSerial.print("\r\n+STNA=HC-05\r\n"); //set the bluetooth name as "HC-05"
//  blueToothSerial.print("\r\n+STOAUT=1\r\n"); // Permit Paired device to connect me
//  blueToothSerial.print("\r\n+STAUTO=0\r\n"); // Auto-connection should be forbidden here
  
  delay(2000); // This delay is required.
  //blueToothSerial.print("\r\n+INQ=1\r\n"); //make the slave bluetooth inquirable 
  blueToothSerial.print("bluetooth connected!\n");
  
  delay(2000); // This delay is required.
  blueToothSerial.flush();
}
