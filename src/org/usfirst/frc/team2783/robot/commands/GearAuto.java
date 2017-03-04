package org.usfirst.frc.team2783.robot.commands;

import org.usfirst.frc.team2783.robot.Robot;
import org.usfirst.frc.team2783.robot.vision.AdjustRotationToTarget;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearAuto extends Command {
	
	double position;
	double curAngle;
	double ultraSonic;
	double oldAngle;
	double newAngle;
	double verticalDistanceFromTarget;
	double horizontalDistanceFromTarget;
	AnalogInput usSensor1;
	AnalogInput usSensor2;
	
	
	
    public GearAuto(double position) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.swerveBase);
    	
    	
    	this.position = position;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	usSensor1 = Robot.usSensor1;
    	usSensor2 = Robot.usSensor2;
    	ultraSonic = (usSensor1.getValue() + usSensor2.getValue())/2;
    	
    	
     	AdjustRotationToTarget VisionAdjustRotation = new AdjustRotationToTarget();
    	VisionAdjustRotation.start();
    	
    	curAngle = Robot.swerveBase.getGyroAngle(false);
    	
    	if(position == 0){
    		Robot.swerveBase.resetGyroNorth(curAngle, 300);
    		
    	}
    	
    	else if(position == 1){
    		Robot.swerveBase.resetGyroNorth(curAngle, 60);
    		
    	}
    	
    	else if(position == 2){
    		Robot.swerveBase.resetGyroNorth(curAngle, 0);
    		
    	}
    	
    	double angle = Robot.swerveBase.getGyroAngle(false);
    	
    	if(angle > 180){
    		angle = (angle - 360);
    		
    	}
    	
    	verticalDistanceFromTarget = Math.abs(Robot.swerveBase.sinDeg((angle)*ultraSonic));
    	
    	horizontalDistanceFromTarget = Math.abs(ultraSonic*Robot.swerveBase.cosDeg(angle));
    	
    	
    	
    }
    

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	
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
