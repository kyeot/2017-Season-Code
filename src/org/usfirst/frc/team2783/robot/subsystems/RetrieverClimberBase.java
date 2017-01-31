package org.usfirst.frc.team2783.robot.subsystems;

import org.usfirst.frc.team2783.robot.RobotMap;
import org.usfirst.frc.team2783.robot.commands.ShooterDrive;
import org.usfirst.frc.team2783.robot.commands.UpdateRetriever;
import org.usfirst.frc.team2783.robot.util.DiscreteToggle;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RetrieverClimberBase extends Subsystem {
	
	private CANTalon gathererMotor = new CANTalon(RobotMap.GATHERER_WHEEL_ID);
	
	private Servo gearShifter = new Servo(RobotMap.GEAR_SHIFTER_ID);

	private DiscreteToggle retrieverInToggle;

	private DiscreteToggle retrieverOutToggle;
	
	public enum RetrieverDirection{
		RET_IN,
		RET_OUT;
		
	}
	
	public RetrieverClimberBase(){
		this.retrieverInToggle = new DiscreteToggle();
		this.retrieverOutToggle = new DiscreteToggle();
		
	}
	
	public void setShooterSpeedVbus(double vbusOutput) {
	}
	
	public void setGathererSpeedVbus(double vbusOutput) {
		gathererMotor.set(vbusOutput);
		
		System.out.println("kill yourself");
		
	}
	
	public void setBrake(boolean bool) {
		gathererMotor.enableBrakeMode(bool);
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
    	
    	if(this.retrieverInToggle.getValue()) {
    		gathererMotor.set(0.75);
        	SmartDashboard.putNumber("Ball Retriever Speed", 0.75);
    	} else if(this.retrieverOutToggle.getValue()) {
    		gathererMotor.set(-0.75);
        	SmartDashboard.putNumber("Ball Retriever Speed", -0.75);    		
    	} else {
    		gathererMotor.set(0);
    	}
    	}
	
	public void shiftGear(double Angle){
		gearShifter.setAngle(Angle);
		
		System.out.println("kill yourself 2");
		
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	setDefaultCommand(new UpdateRetriever());
    	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

