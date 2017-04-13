#include<Adafruit_NeoPixel.h>
#include<Wire.h>
#ifdef __AVR__
  #include <avr/power.h>
#endif

#define PIN            6
#define NUMPIXELS      120

Adafruit_NeoPixel pixels = Adafruit_NeoPixel(NUMPIXELS, PIN, NEO_GRB + NEO_KHZ800);

#if defined (__AVR_ATtiny85__)
  if (F_CPU == 16000000) clock_prescale_set(clock_div_1);
#endif

int delayval = 1;
int r = 255;
int g = 0;
int b = 0;

int c = 0;

void setup() {
  // put your setup code here, to run once:
  #if defined (__AVR_ATtiny85__)
    if (F_CPU == 16000000) clock_prescale_set(clock_div_1);
  #endif

  Serial.begin(9600);
  pixels.begin();

  Wire.begin(1);
  Wire.onReceive(onReceive);

}

void loop() {
  // put your main code here, to run repeatedly:
  if(c != 0) {
    if(c == 1) {
      updateLeds(255, 0, 0);
    } else if(c == 2) {
      updateLeds(0, 255, 0);
    } else if(c == 3) {
      updateLeds(0, 0, 255);
    }
  } else {
    updateLeds(0, 0, 0);
  }

}

void onReceive(int howMany) {
  while(Wire.available()) {
    c = Wire.read();
  }
}

void updateLeds(int r, int g, int b) {
  for(int i=0;i<NUMPIXELS;i++){
         pixels.setPixelColor(i, pixels.Color(r, g, b));
         pixels.show();
      }
}

