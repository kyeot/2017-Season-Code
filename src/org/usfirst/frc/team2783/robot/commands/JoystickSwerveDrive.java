package org.usfirst.frc.team2783.robot.commands;

import org.usfirst.frc.team2783.robot.OI;
import org.usfirst.frc.team2783.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class JoystickSwerveDrive extends Command {

    public JoystickSwerveDrive() {
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
    	Double rotValue = -OI.xBoxController.getRawAxis(2);
    	
    	if ((fbValue > -.2 && fbValue < .2) && (rlValue > -.2 && rlValue < .2)){
    		fbValue = 0.0;
    		rlValue = 0.0;
    	}
    	
    	if (rotValue > -.2 && rotValue < .2){
    		rotValue = 0.0;
    	}
    	
    	if(OI.xBoxController.getRawButton(1)) {
    		System.out.println(1);
    		fbValue *= 0.5;
    		rlValue *= 0.5;
    		rotValue *= 0.5;
    	}
    	
    	if(OI.xBoxController.getRawButton(4)) {
    		System.out.println(4);
    		Robot.swerveBase.setZero();
    	}
    	
    	if(OI.xBoxController.getRawButton(2)) {
    		System.out.println(2);
    		Robot.swerveBase.getNavSensor().reset();
    	}
    	
    	Robot.swerveBase.swerveDrive(fbValue, rlValue, rotValue, true);
    	
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
