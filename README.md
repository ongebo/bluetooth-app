# Bluetooth App
This app is part of a hobby electronics project on Bluetooth comms. Its purpose is to send a bunch of four text messages to a microcontroller via an [HC-05/HC-06](http://www.martyncurrey.com/hc-05-and-hc-06-zs-040-bluetooth-modules-first-look/) module. The microcontroller stores these messages and outputs one of them depending on the state of its analog voltage inputs.

Other features include:
* Local authentication (signup/login)
* Display of the four most recently sent messages

## Project Setup
### Electronics Setup
**NB:** Familiarity with the [Arduino](https://www.arduino.cc/) platform is assumed.

Follow these steps to set up the microcontroller:
* Load the program below onto an Arduino board:
```C
#define BUFFER_SIZE 1000

void setStrings(char *);

const int xpin = A1;
const int ypin = A2;
const int zpin = A3;

String xmsge;
String xnegmsge;
String ymsge;
String ynegmsge;

void setup()
{
  Serial.begin(9600);
  char userInput[BUFFER_SIZE];
  while (Serial.available() <= 0) {} // Wait until serial data is available.
  byte bytes = Serial.readBytes(userInput, BUFFER_SIZE); // Read the bytes from the serial port.
  userInput[bytes] = '\0'; // Mark the end of C string with the null character.
  setStrings(userInput); // Extract the values of xmsge, xnegmsge, ymsge, and ynegmsge from userInput.
}

void setStrings(char *userInput) {
  char *message = strtok(userInput, "&");
  xmsge = message;
  unsigned int c;
  for (c = 0; c < 3; c++) {
    message = strtok(NULL, "&");
    switch (c) {
      case 0:
        xnegmsge = message;
        break;
      case 1:
        ymsge = message;
        break;
      case 2:
        ynegmsge = message;
        break;
    }
  }
}

void loop() {
  Serial.println(xmsge);
  if (analogRead(xpin) >=268 && analogRead(xpin) <= 322) {
    Serial.println(xmsge);
  }
 
  if (analogRead(xpin) >=342 && analogRead(xpin) <= 403) {
    Serial.println(xnegmsge);
  }
 
  if (analogRead(ypin) >=267 && analogRead(ypin) <= 300) {
    Serial.println(ymsge);
  }
 
  if (analogRead(ypin) >=340 && analogRead(ypin) <= 404) {
    Serial.println(ynegmsge);
  }
  delay(1000);
}
```
* Connect an HC-05/HC-06 bluetooth module to the Arduino's serial port.
* Connect analog voltage sources (could be from sensors) to pins A1, A2, and A3 of the Arduino.
* Attach a digital LED output to the Arduino to display messages stored in the microcontroller according to the above program.

### App Setup
**NB:** This description assumes a basic knowledge of **git** and **Android Studio**. Ensure that both are installed on your local machine.

Follow these steps to run the app:
* Clone the repository:
```
git clone https://github.com/ongebo/bluetooth-app.git
```
* Open the cloned project in Android Studio and wait for the Gradle build to complete successfully.
* Run the app on a real device (most emulators do not support Bluetooth comms).
* Pair the device with the installed app to the HC-05/HC-06 module above.
* Login to the app, enter the four required messages, and send them to the paired Bluetooth module.
* Verify from the microcontroller output that the entered messages are displayed based on the analog voltage inputs.
