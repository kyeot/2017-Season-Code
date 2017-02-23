package org.usfirst.frc.team2783.robot.commands;

import org.usfirst.frc.team2783.robot.OI;
import org.usfirst.frc.team2783.robot.Robot;
import org.usfirst.frc.team2783.robot.vision.AdjustRotationToTarget;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearAuto extends Command {
	
	double position;
	double initUltra;
	boolean travelLeft;
	double initArclength;
	double verticalTargetDisplacement;
	double horizontalTargetDisplacement;
	double curArclength;
	double horizontalSpeed;
	double verticalSpeed;
	double horizontalControllerAxis;
	double verticalControllerAxis;
	double angleChangeRate;
	
    public GearAuto(double position) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.swerveBase);
    	
    	
    	this.position = position;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	AdjustRotationToTarget VisionAdjustRotation = new AdjustRotationToTarget();
    	VisionAdjustRotation.start();
    	double initGyro = Robot.swerveBase.getGyroAngle(false);
    	double initUltra1 = Robot.usSensor.getValue();
    	double initUltra2 = Robot.usSensor2.getValue();
    	initUltra = (initUltra1 + initUltra2) / 2;
    	if(position == 0){
    		Robot.swerveBase.resetGyroNorth(initGyro, 210);
    	} else if(position == 1){
    		Robot.swerveBase.resetGyroNorth(initGyro, 330);
    	} else if(position == 2){
    		Robot.swerveBase.resetGyroNorth(initGyro, 270);
    	}
    	initGyro = Robot.swerveBase.getGyroAngle(false);
    	if(initGyro >= 90) {
    			travelLeft = false;
    		} else {
    			travelLeft = true;
    		}
    	
    	initArclength = Math.abs(initGyro)*initUltra;
    	
    }
    

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	verticalTargetDisplacement = -1 * (initUltra * Math.sin(Robot.swerveBase.getGyroAngle(false)));
    	horizontalTargetDisplacement = initUltra * Math.cos(Robot.swerveBase.getGyroAngle(false));
    	horizontalSpeed = Math.sqrt(Math.abs(((48 * Math.PI) * (48 * Math.PI) * ((initUltra * initUltra) - (horizontalTargetDisplacement * horizontalTargetDisplacement))) / (initUltra * initUltra)));
    	verticalSpeed = (horizontalTargetDisplacement * horizontalSpeed) / Math.sqrt(Math.abs((initUltra * initUltra) - (horizontalTargetDisplacement * horizontalTargetDisplacement)));
    	curArclength = initUltra * Robot.swerveBase.getGyroAngle(false);
    	if(travelLeft = true){
    		horizontalControllerAxis = (-curArclength * horizontalSpeed) / (48 * Math.PI * initArclength);
    	} else {
    		horizontalControllerAxis = (curArclength * horizontalSpeed) / (48 * Math.PI * initArclength);
    	}
    	verticalControllerAxis = (-curArclength * verticalSpeed) / (48 * Math.PI * initArclength);
    	angleChangeRate = curArclength / initArclength;
    	Robot.swerveBase.swerveDrive(verticalControllerAxis, horizontalControllerAxis, angleChangeRate, true);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() { 
        if(horizontalTargetDisplacement != 0){
        	return false;
        } else {
        	return true;
        }
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
