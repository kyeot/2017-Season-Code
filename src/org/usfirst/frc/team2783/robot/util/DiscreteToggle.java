package org.usfirst.frc.team2783.robot.util;

public class DiscreteToggle {
	
	private Boolean toggleValue;

	public DiscreteToggle() {
		toggleValue = false;
	}
		
	public Boolean toggle() {
		return toggle(true);
	}
	
	public Boolean toggle(Boolean toggle) {
		if (toggle) {
			this.toggleValue = !this.toggleValue;
		}
		return getValue();
	}
		
	public void setValue(Boolean value) {
		toggleValue = value;
	}
	
	public Boolean getValue() {
		return this.toggleValue;
	}
}
