/*
  Arduino Uno软串口通信
*/

#include <SoftwareSerial.h>
SoftwareSerial mySerial(2, 3);

void setup()
{
  Serial.begin(9600);
  while (!Serial) {
  }

  mySerial.begin(115200);
  mySerial.println("AT+CWJAP=\"pei\",\"1138995353\"");
}

void loop()
{
  if (mySerial.available())
    Serial.write(mySerial.read());
  if (Serial.available())
    mySerial.write(Serial.read());
}
