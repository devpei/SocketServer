#include <SoftwareSerial.h>
SoftwareSerial mySerial(2, 3);

//当前连接状态
bool connect = false;

//当前输出行
char mySerialData[64];

//当前输入行标记，WIFi串口每读取一个字节数据自增，当遇到换行重置
int i = 0;

//当前读取到的字节
int currentRead = -1;

void setup()
{
  Serial.begin(9600);
  while (!Serial) {
  }

  mySerial.begin(115200);
  
  //发送连接指令
  mySerial.println("AT+CWJAP=\"rockemb\",\"rocktech\"");
}

void loop()
{
  //读取WIFI输出信息
  if (mySerial.available() > 0) {
    //Serial.write(mySerial.read());
    currentRead = mySerial.read();
    mySerialData[i] = currentRead;
    i = i + 1;
    //换行
    if (currentRead == 10) {
      //按行分析数据
      analyseData(mySerialData, i );
      i = 0;
      return;
    }
  }
  if (Serial.available() > 0) {
    mySerial.write(Serial.read());
  }
}

bool connectWifi() {
  //发送连接指令
  mySerial.println("AT+CWJAP=\"rockemb\",\"rocktech\"");
}

//接收到的数据分析
//data接收的数据，len实际有效数据长度
void analyseData(char data[], int len) {
  for (int i = 0; i < len; i = i + 1) {
    Serial.print(data[i]);
  }
}
