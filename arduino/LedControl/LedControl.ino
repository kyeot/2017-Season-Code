#include<Adafruit_NeoPixel.h>
#include<Wire.h>
#ifdef __AVR__
  #include <avr/power.h>
#endif

#define PIN            6
#define NUMPIXELS      60

Adafruit_NeoPixel pixels = Adafruit_NeoPixel(NUMPIXELS, PIN, NEO_GRB + NEO_KHZ800);

#if defined (__AVR_ATtiny85__)
  if (F_CPU == 16000000) clock_prescale_set(clock_div_1);
#endif

int delayval = 1;
int r = 0;
int g = 0;
int b = 0;

int c[3];

double brightness = 0.5;

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
    updateLeds(r, g, b, false, 0);

}

void onReceive(int howMany) {
  for(int i = 0; Wire.available(); i++) {
    c[i] = Wire.read();
  }

   r = c[0];
   g = c[1];
   b = c[2];
}

void updateLeds(int setR, int setG, int setB, boolean fadeTo, int time) {
  setR = brightness*setR;
  setG = brightness*setG;
  setB = brightness*setB;
  if(fadeTo) {
    for(int t = 0; t < time; t++) {
      for(int i=0;i<NUMPIXELS;i++){
         pixels.setPixelColor(i, pixels.Color((((setR-r)/time)*t)+r, 
                                              (((setG-g)/time)*t)+g,
                                              (((setB-b)/time)*t)+b));
         pixels.show();
      }
    }
  } else {
    for(int i=0;i<NUMPIXELS;i++){
         pixels.setPixelColor(i, pixels.Color(setR, setG, setB));
         pixels.show();
    }
  }
}



