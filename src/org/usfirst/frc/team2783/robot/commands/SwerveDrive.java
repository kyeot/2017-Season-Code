package org.usfirst.frc.team2783.robot.commands;

import org.usfirst.frc.team2783.robot.OI;
import org.usfirst.frc.team2783.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SwerveDrive extends Command {

	//Makes SwerveDrive require the subsystem swerveBase
    public SwerveDrive() {
    	requires(Robot.swerveBase);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	//Sets input for swerveDrive method as input from controller stick axes. Note: FBValue is negative as required by doc linked to in swerveDrive method
    	Double fbValue = (OI.xBoxController.getRawAxis(1));
    	Double rlValue = -(OI.xBoxController.getRawAxis(0));
    	Double rotValue = OI.xBoxController.getRawAxis(4);
    	
    	//Makes it so if the left stick is barely moved at all it doesn't move at all
    	if ((fbValue > -.2 && fbValue < .2) && (rlValue > -.2 && rlValue < .2)){
    		fbValue = 0.0;
    		rlValue = 0.0;
    	}
    	
    	//Makes it so if the right stick is barely moved at all it doesn't move at all
    	if (rotValue > -.2 && rotValue < .2){
    		rotValue = 0.0;
    	}
    	
    	//While the left bumper is held goes half speed
    	if(OI.xBoxController.getRawButton(5)) {
    		fbValue *= 0.5;
    		rlValue *= 0.5;
    		rotValue *= 0.5;
    	}
    	
    	//If the X button is pressed resets the Swerve Modules
    	if(OI.xBoxController.getRawButton(3)) {
    		Robot.swerveBase.setZero();
    	}
    	
    	//If Y is pressed resets the field orientation
    	if(OI.xBoxController.getRawButton(4)) {
    		Robot.swerveBase.getNavSensor().reset();
    	}
    	
    	Robot.swerveBase.swerveDrive(fbValue, rlValue, rotValue);
    	
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
