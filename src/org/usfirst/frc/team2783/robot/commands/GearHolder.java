package org.usfirst.frc.team2783.robot.commands;

import org.usfirst.frc.team2783.robot.OI;
import org.usfirst.frc.team2783.robot.Robot;
import org.usfirst.frc.team2783.robot.commands.autonomous.AutoActiveGear;
import org.usfirst.frc.team2783.robot.triggers.LimitSwitch;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearHolder extends Command {

    public GearHolder() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.retriever);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	double liftValue = -OI.manipulator.getRawAxis(1);
    	
    	if(liftValue > -.2 && liftValue < .2){
    		Robot.retriever.moveGearHolder(0);
    	}
    	
    	else{
    		Robot.retriever.moveGearHolder(-liftValue);
    	}
    	
//    	if(){
//    		Robot.retriever.moveGearHolder(0);
//    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	double liftValue = -OI.manipulator.getRawAxis(1);
    	
    	if(liftValue > -.2 && liftValue < .2){
    		Robot.retriever.moveGearHolder(0);
    	}
    	
    	else{
    		Robot.retriever.moveGearHolder(-liftValue);
    		
    	}
    	
    	if(Robot.limitSwitches[0].get()){
    		new AutoActiveGear(1, 0.25);
    	}

    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.rollerBase.rollRoller(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
