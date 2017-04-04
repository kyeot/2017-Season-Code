package org.usfirst.frc.team2783.robot.commands;

import org.usfirst.frc.team2783.robot.Robot;
import org.usfirst.frc.team2783.robot.subsystems.RetrieverClimberBase.GearHolderLift;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearHolder extends Command {

	double angle;
	private GearHolderLift direction;
	
    public GearHolder(GearHolderLift direction) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	direction = this.direction;
    	
    	requires(Robot.retriever);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(direction == GearHolderLift.GEAR_DOWN){
    		double angle = 270;
    	}
    	
    	else if(direction == GearHolderLift.GEAR_UP){
    		double angle = 0;
    	}
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.retriever.liftGear(direction, angle);
    	
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
