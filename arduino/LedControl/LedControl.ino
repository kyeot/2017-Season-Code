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
int r = 255;
int g = 0;
int b = 0;

int c[2];

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
  if(c[0] == 0) {
    if(c[1] == 0) {
      updateLeds(0, 0, 0); //Off, 0
    } else if(c[1] == 1) {
      updateLeds(255, 0, 0); //RED, 1
    } else if(c[1] == 2) {
      updateLeds(0, 255, 0); //GREEN, 2
    } else if(c[1] == 3) {
      updateLeds(0, 0, 255); //BLUE, 3
    } else if(c[1] == 4) {
      updateLeds(255, 255, 0); //YELLOW, 4
    } else if(c[1] == 5) {
      updateLeds(255, 69, 0); //ORANGE, 5
    } else if(c[1] == 6) {
      updateLeds(255, 0, 255); //PURPLE, 6
    }
  } else {
    updateLeds(0, 0, 0);
  }

}

void onReceive(int howMany) {
  for(int i = 0; Wire.available(); i++) {
    c[i] = Wire.read();
  }
}

void updateLeds(int r, int g, int b, boolean fadeTo, int time) {
  r = brightness*r;
  g = brightness*g;
  b = brightness*b;
  if(fadeTo) {
    for(t = 0; t < time; t++) {
      for(int i=0;i<NUMPIXELS;i++){
         pixels.setPixelColor(i, pixels.Color((((r-this.r)/time)*t)+this.r, 
                                              (((g-this.g)/time)*t)+this.g,
                                              (((b-this.b)/time)*t)+this.b);
         pixels.show();
      }
    }
  } else {
    for(int i=0;i<NUMPIXELS;i++){
         pixels.setPixelColor(i, pixels.Color(r, g, b));
         pixels.show();
    }
  }
  this.r = r;
  this.g = g;
  this.b = b;
}



