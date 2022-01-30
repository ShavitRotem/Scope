#include <SoftwareSerial.h>  //Software Serial Port
#define RxD 1
#define TxD 0

SoftwareSerial blueToothSerial(RxD,TxD);
float voltage;


void initADC()
{
  ADMUX = (1 << ADLAR) |     // left shift result
          (0 << REFS1) |     // Sets ref. voltage to VCC, bit 1
          (0 << REFS0) |     // Sets ref. voltage to VCC, bit 0
          (0 << MUX3)  |     // use ADC2 for input (PB4), MUX bit 3
          (0 << MUX2)  |     // use ADC2 for input (PB4), MUX bit 2
          (1 << MUX1)  |     // use ADC2 for input (PB4), MUX bit 1
          (0 << MUX0);       // use ADC2 for input (PB4), MUX bit 0

  ADCSRA = 
            (1 << ADEN)  |     // Enable ADC 
            (1 << ADPS2) |     // set prescaler to 64, bit 2 
            (0 << ADPS1) |     // set prescaler to 64, bit 1 
            (0 << ADPS0);      // set prescaler to 64, bit 0  
  

}



void setup() {
  // put your setup code here, to run once:
  setupBlueToothConnection();
  initADC();

}

void loop() {
  // put your main code here, to run repeatedly:
  ADCSRA |= (1 << ADSC);         // start ADC measurement
  while (ADCSRA & (1 << ADSC) ); // wait till conversion complete

  voltage = (float(ADCH) / 255.0) * 3.3; // Based on 3.3V VCC
  
  blueToothSerial.print("Cur voltage is: ");
  blueToothSerial.print(voltage);
  blueToothSerial.println("V");

  delay(200);
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
