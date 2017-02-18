package org.usfirst.frc.team2783.robot.commands;

import org.usfirst.frc.team2783.robot.OI;
import org.usfirst.frc.team2783.robot.Robot;
import org.usfirst.frc.team2783.robot.subsystems.RetrieverClimberBase.RetrieverDirection;
import org.usfirst.frc.team2783.robot.util.DiscreteToggle;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleRetriever extends Command {
	
	private RetrieverDirection direction;
	
    public ToggleRetriever(RetrieverDirection direction) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.retriever);
    	
    	this.direction = direction;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.retriever.shiftGear(0);
    	
    	Robot.retriever.toggleRetriever(direction);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
