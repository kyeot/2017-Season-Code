package org.usfirst.frc.team2783.robot.commands.autonomous;

import org.usfirst.frc.team2783.robot.Robot;
import org.usfirst.frc.team2783.robot.subsystems.RetrieverClimberBase.GearHolderLift;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoActiveGear extends Command {
	
	boolean end = false;
	
	private int direction;
	
    public AutoActiveGear(int direction) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	this.direction = direction;
    	requires(Robot.retriever);    	

    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.retriever.rollRoller(1);

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return end;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.retriever.rollRoller(0);

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
