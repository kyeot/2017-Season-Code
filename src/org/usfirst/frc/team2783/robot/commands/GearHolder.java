package org.usfirst.frc.team2783.robot.commands;

import org.usfirst.frc.team2783.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearHolder extends Command {

	int direction;
	
    public GearHolder(int direction) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	direction = this.direction;
    	
    	requires(Robot.retriever);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(direction == 0){
    		Robot.retriever.liftGear(0);
    		
    	}
    	
    	else if(direction == 1){
    		Robot.retriever.liftGear(90);
    		
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
