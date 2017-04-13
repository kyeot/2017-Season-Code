package org.usfirst.frc.team2783.robot.commands;

import org.usfirst.frc.team2783.robot.OI;
import org.usfirst.frc.team2783.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearRoller extends Command {

	
    public GearRoller() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	requires(Robot.rollerBase);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
<<<<<<< HEAD
    	double rollValue = -OI.manipulator.getRawAxis(5);
    	System.out.println(rollValue);
=======
    	double rollValue = OI.manipulator.getRawAxis(5);
>>>>>>> origin/gear-lifter
    	if(rollValue > -.2 && rollValue < .2){
    		Robot.rollerBase.rollRoller(0);
    	}
    	
    	else{
    		Robot.rollerBase.rollRoller(rollValue);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	double rollValue = -OI.manipulator.getRawAxis(5);
    	if(rollValue > -.2 && rollValue < .2){
    		Robot.rollerBase.rollRoller(0);
    	}
    	
    	else{
    		Robot.rollerBase.rollRoller(rollValue);
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
