package org.usfirst.frc.team2783.robot.commands;

import org.usfirst.frc.team2783.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CollectGear extends Command {

    public CollectGear() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	requires(Robot.activeGearBase);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.activeGearBase.setLifterSpeedVbus(1);
    	
    }


    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!Robot.limitSwitches[1].get()) {
    		Robot.activeGearBase.setLifterSpeedVbus(1);
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.limitSwitches[1].get();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.activeGearBase.setLifterSpeedVbus(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
