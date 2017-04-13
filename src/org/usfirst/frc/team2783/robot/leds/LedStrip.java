package org.usfirst.frc.team2783.robot.leds;

import edu.wpi.first.wpilibj.I2C;

public class LedStrip {
	
	public enum Color {
		RED((byte) 1),
		GREEN((byte) 2),
		BLUE((byte) 3);
		
		byte data;
		
		Color(byte data) {
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
	
	public void solid(Color color) {
		toSend = new byte[1];
		toSend[0] = color.getByte();
		i2cSend(toSend, 1);
	}
	
	public void i2cSend(byte[] data, int size) {
		i2c.transaction(data, size, null, 0);
		
	}

}
