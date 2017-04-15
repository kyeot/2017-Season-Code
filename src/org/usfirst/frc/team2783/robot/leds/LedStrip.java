package org.usfirst.frc.team2783.robot.leds;

import edu.wpi.first.wpilibj.I2C;

public class LedStrip {
	
	public enum LedPattern {
		SOLID_RED((byte) 1),
		SOLID_GREEN((byte) 2),
		SOLID_BLUE((byte) 3),
		SOLID_YELLOW((byte) 4),
		SOLID_ORANGE((byte) 5),
		SOLID_PURPLE((byte) 6);
		
		byte data;
		
		LedPattern(byte data) {
			this.data = data;
		}
		
		public byte getByte() {
			return data;
		}
	}
	
	I2C i2c;
	byte[] toSend;
	
	public LedStrip() {
		i2c = new I2C(I2C.Port.kOnboard, 1);
	}
	
	public void ledMode(LedPattern pattern) {
		toSend = new byte[1];
		toSend[0] = pattern.getByte();
		i2cSend(toSend, 1);
	}
	
	public void i2cSend(byte[] data, int size) {
		i2c.transaction(data, size, null, 0);
		
	}

}
