package org.usfirst.frc.team2783.robot.subsystems;

import org.usfirst.frc.team2783.robot.RobotMap;
import org.usfirst.frc.team2783.robot.commands.UpdateRetriever;
import org.usfirst.frc.team2783.robot.util.DiscreteToggle;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class RetrieverClimberBase extends Subsystem {
	
	private CANTalon gathererMotor;
	private Servo gearShifter;

	private DiscreteToggle retrieverInToggle;
	private DiscreteToggle retrieverOutToggle;
	
	public enum RetrieverDirection{
		RET_IN,
		RET_OUT;
		
	}
	
	public RetrieverClimberBase(){
		this.retrieverInToggle = new DiscreteToggle();
		this.retrieverOutToggle = new DiscreteToggle();
		
		gathererMotor = new CANTalon(RobotMap.GATHERER_WHEEL_ID);
		gearShifter = new Servo(RobotMap.GEAR_SHIFTER_ID);
	}

	public void setGathererSpeedVbus(double vbusOutput) {
		gathererMotor.set(vbusOutput);
	}
	
	public void toggleRetriever(RetrieverDirection direction) {
		if(direction == RetrieverDirection.RET_IN) {
			retrieverInToggle.toggle();
			retrieverOutToggle.setValue(false);
		} else if(direction == RetrieverDirection.RET_OUT) {
			retrieverOutToggle.toggle();
			retrieverInToggle.setValue(false);
		}
	}
	
	public void updateRetriever() {
    	if(retrieverInToggle.getValue()) {
    		gathererMotor.set(1);
    	} else if(retrieverOutToggle.getValue()) {
    		gathererMotor.set(-1);		
    	} else {
    		gathererMotor.set(0);
    	}
    }
	
	public void shiftGear(double angle){
		gearShifter.setAngle(angle);
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	setDefaultCommand(new UpdateRetriever());
    	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

