package org.usfirst.frc.team2783.robot.commands.autonomous;

import org.usfirst.frc.team2783.robot.Robot;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoDrive extends Command {

	private double angle;
	private double speed;
	private double rotMot;
	private boolean fieldOriented;
	private long commandStartedAt;
	private double runTime;
	
    public AutoDrive(double angle, double speed, double rotMot, boolean fieldOriented, double runTime) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.swerveBase);
    	this.angle = angle;
    	this.speed = speed;
    	this.rotMot = rotMot;
    	this.fieldOriented = fieldOriented;
    	
    	//Run Time is in Seconds
    	this.runTime = runTime;  	
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	commandStartedAt = Utility.getFPGATime();
       }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.swerveBase.polarSwerveDrive(angle, speed, rotMot, fieldOriented);
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//Run command for 6 seconds
        return Utility.getFPGATime() > (runTime * 1000000 + commandStartedAt);
    }

    // Called once after isFinished returns true
    protected void end() {
    	commandStartedAt = 0;
    	Robot.swerveBase.swerveDrive(0, 0, 0, false);
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
