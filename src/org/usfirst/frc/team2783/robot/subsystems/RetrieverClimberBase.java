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
	
	private VictorSP gathererMotor = new VictorSP(RobotMap.GATHERER_WHEEL_ID);
	
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

	public void setGathererSpeedVbus(double vbusOutput) {
		gathererMotor.set(vbusOutput);
		
		System.out.println("kill yourself");
		
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
    		gathererMotor.set(1);
        	SmartDashboard.putNumber("Ball Retriever Speed", 1);
    	} else if(this.retrieverOutToggle.getValue()) {
    		gathererMotor.set(-1);
        	SmartDashboard.putNumber("Ball Retriever Speed", -1);    		
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

