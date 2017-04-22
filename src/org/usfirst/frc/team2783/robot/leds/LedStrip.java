package org.usfirst.frc.team2783.robot.leds;

import edu.wpi.first.wpilibj.I2C;

public class LedStrip {
	
	public enum Color {
		RED(255, 0, 0),
		GREEN(0, 255, 0),
		BLUE(0, 0, 255),
		YELLOW(255, 255, 0),
		ORANGE(255, 69, 0),
		PURPLE(255, 0, 255);
		
		int r;
		int g;
		int b;
		
		Color(int r, int g, int b) {
			this.r = r;
			this.g = g;
			this.b = b;
		}
		
		public int getR() {
			return r;
		}

		public int getG() {
			return g;
		}

		public int getB() {
			return b;
		}
		
		public int[] getRGB() {
			return new int[]{r, g, b};
		}
		
		public byte[] getRGBByte() {
			return new byte[] {
								(byte) (r & 0xFF),
								(byte) (g & 0xFF),
								(byte) (b & 0xFF)
							};
			}
		}
	
	
	I2C i2c;
	byte[] toSend;
	
	public LedStrip() {
		i2c = new I2C(I2C.Port.kOnboard, 1);
	}
	
	public void solid(Color color) {
		toSend = color.getRGBByte();
		i2cSend(toSend);
	}
	
	public void fadeWith(Color color1, Color color2, double value, int min, int max) {
		double r1 = (double) color1.getR(), g1 = (double) color1.getG(), b1 = (double) color1.getB();
		double r2 = (double) color2.getR(), g2 = (double) color2.getG(), b2 = (double) color2.getB();
		
		int intervals = Math.abs(max-min);
		
		if(Math.floor(value) < min) {
			toSend = color1.getRGBByte();
		} else if(Math.floor(value) > max) {
			toSend = color2.getRGBByte();
		} else {
			toSend = new byte[] {
									((byte) ((int) ((((r2-r1)/intervals)*Math.floor(value))+r1) & 0xFF)),
									((byte) ((int) ((((g2-g1)/intervals)*Math.floor(value))+g1) & 0xFF)),
									((byte) ((int) ((((b2-b1)/intervals)*Math.floor(value))+b1) & 0xFF))
								};
		}
		
		//System.out.println("r: " + ((((r2-r1)/intervals)*Math.floor(value))+r1) + " g: " + ((((g2-g1)/intervals)*Math.floor(value))+g1) + " b: " + toSend[2] + " value: " + value + " intervals: " + intervals);
		//System.out.println(value);
		i2cSend(toSend);
	}
	
	public void i2cSend(byte[] data) {
		i2c.transaction(data, 3, null, 0);
		
	}

}
