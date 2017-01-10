package org.usfirst.frc.team2783.robot.commands;

import org.usfirst.frc.team2783.robot.OI;
import org.usfirst.frc.team2783.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SwerveDrive extends Command {

    public SwerveDrive() {
    	requires(Robot.swerveBase);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	//Sets input for swerveDrive method as input from controller stick axes. Note: FBValue is negative as required by doc linked to in swerveDrive method
    	Double FBValue = -(OI.xBoxController.getRawAxis(1));
    	Double RLValue = -(OI.xBoxController.getRawAxis(0));
    	Double rotValue = OI.xBoxController.getRawAxis(4);
    	
    	if (FBValue > -.2 && FBValue < .2){
    		FBValue = 0.0;
    	}
    	if (RLValue > -.2 && RLValue < .2){
    		RLValue = 0.0;
    	}
    	if (rotValue > -.2 && rotValue < .2){
    		rotValue = 0.0;
    	}
    	Robot.swerveBase.swerveDrive(FBValue, RLValue, rotValue);
    	
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
