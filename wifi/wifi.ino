#include <SoftwareSerial.h>
#include <Servo.h>
SoftwareSerial mySerial(2, 3);

Servo myServo;

//舵机旋转角度
int pos = 0;

//WIFI连接状态
bool wifiConnectStatus = false;

//服务器连接状态
bool serverConnectStatus = false;

//缓存数组
char mySerialBuff[64];

void setup() {
  Serial.begin(9600);
  while (!Serial) {
  }
  mySerial.begin(115200);
  if (connectWifi("rockemb", "rocktech")) {
    delay(100);
    //WIFI连接成功，开始连接服务器
    Serial.println("==>Start Connect Server.");
    connectServer("192.168.1.202", 7800);
  }

  myServo.attach(9);
}

void loop() {
  analyseData(readData());
  if (Serial.available() > 0) {
    mySerial.write(Serial.read());
  }
}

//连接WiFi
bool connectWifi(String ssid, String password) {
  //发送连接指令
  mySerial.println("AT+CWJAP=\"" + ssid + "\",\"" + password + "\"");
  int currentTime = millis();
  while (true) {
    if (readData().startsWith("OK")) {
      wifiConnectStatus = true;
      Serial.println("==>Connected");
      return true;
    }
    //如果10s内没有连接上跳出
    if ((currentTime + 10000) < millis()) {
      break;
    }
  }
  //重新连接
  if (!wifiConnectStatus) {
    Serial.println("==>ReConnected");
    connectWifi(ssid, password);
  }
}

//连接服务器
bool connectServer(String ip, int port) {
  if (sendInstruction("AT+CIPSTART=\"TCP\",\"" + ip + "\"," + port + "")) {
    serverConnectStatus = true;
    Serial.println("==>Server Connected.");
  }
  return serverConnectStatus;
}
//#{"content":{"clientType":"Devices"},"destinationAddress":"0.0.0.0"}$
//向服务器发送消息
void sendMessage(String msg) {
  if (sendInstruction("AT+CIPSENDEX=80", ">")) {
    mySerial.println(msg + "\0");
  }
}

//发送指令，返回指令效果
bool sendInstruction(String instruction) {
  return sendInstruction(instruction, "");
}

//发送具有特殊返回标识的指令，返回指令效果
bool sendInstruction(String instruction, String okMark) {
  mySerial.println(instruction);
  int currentTime = millis();
  while (true) {
    if (okMark.length() < 1 && readData().startsWith("OK")) {
      return true;
    } else if (okMark.length() > 1 && readData().startsWith(okMark)) {
      return true;
    }
    //最多读取3s内数据
    if ((currentTime + 3000) < millis()) {
      break;
    }
  }
  return false;
}

//按行读取WIFI返回信息
String readData() {
  //当前读取到的数据
  int currentRead = -1;
  //当前行标记
  int i = 0;
  //读取到的字符串
  String lineData = "";
  while (true) {
    if (mySerial.available() > 0) {
      //Serial.write(mySerial.read());
      currentRead = mySerial.read();
      mySerialBuff[i] = currentRead;
      i = i + 1;
      //表明某行数据结束
      if (currentRead == 10) {
        for (int j = 0; j < i; j = j + 1) {
          lineData = lineData + mySerialBuff[j];
        }
        break;
      }
    }
  }
  return lineData;
}

//接收到的数据分析
void analyseData(String data) {
  if (data.startsWith("+IPD,")) {
    controlInstruct(int(data.charAt(data.indexOf(":") + 1)));
  }
}

//控制指令
void controlInstruct(int instruct) {
  switch (instruct) {
    case 49:
      Serial.println("<==up");
      if (pos < 0) {
        pos = 0;
      }
      pos = pos + 1;
      if (pos < 180) {
        myServo.write(pos);
      }
      break;
    case 50:
      Serial.println("<==down");
      if (pos > 180) {
        pos = 180;
      }
      pos = pos - 1;
      if (pos > 0) {
        myServo.write(pos);
      }
      break;
  }
}
