package org.usfirst.frc.team2783.robot.commands;

import org.usfirst.frc.team2783.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveGear extends Command {

    public MoveGear() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	requires(Robot.retriever);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.retriever.moveGearHolder(1);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println(Robot.limitSwitches[0].get());
    	System.out.println(Robot.limitSwitches[1].get());
    	if(!Robot.limitSwitches[1].get()) {
    		Robot.retriever.moveGearHolder(1);
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.limitSwitches[1].get();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.retriever.moveGearHolder(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
