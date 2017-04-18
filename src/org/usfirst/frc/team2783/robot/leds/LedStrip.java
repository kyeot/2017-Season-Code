package org.usfirst.frc.team2783.robot.leds;

import edu.wpi.first.wpilibj.I2C;

public class LedStrip {
	
	public enum Color {
		RED((byte) 1),
		GREEN((byte) 2),
		BLUE((byte) 3),
		YELLOW((byte) 4),
		ORANGE((byte) 5),
		PURPLE((byte) 6);
		
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
	
	public void solid(Color color, boolean fadeTo) {
		toSend = new byte[3];
		toSend[0] = (byte) 0;
		toSend[1] = color.getByte();
		toSend[2] = (byte) (fadeTo ? 1 : 0);
		i2cSend(toSend);
	}
	
	public void i2cSend(byte[] data) {
		i2c.transaction(data, 3, null, 0);
		
	}

}
