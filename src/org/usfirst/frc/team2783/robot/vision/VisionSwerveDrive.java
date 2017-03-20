package org.usfirst.frc.team2783.robot.vision;

import org.usfirst.frc.team2783.robot.Robot;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 *
 */
public class VisionSwerveDrive extends PIDCommand {

    private static double p = 0.01;
	private static double i = 0.0;
	private static double d = 0.0;
	
	private double speed;
	
	private double centerX;
	
	private long commandStartedAt;
	private double runTime;
	private boolean timed;

	public VisionSwerveDrive(double speed, double runTime) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super(p, i, d);
    	
    	requires(Robot.swerveBase);
    	
    	this.speed = speed;
    	this.runTime = runTime;
    	timed = true;
    }
	
	public VisionSwerveDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super(p, i, d);
    	
    	requires(Robot.swerveBase);
    	
    	this.runTime = runTime;
    	timed = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	getPIDController().setSetpoint(0.5);
    	
    	commandStartedAt = Utility.getFPGATime();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	synchronized(Robot.imgLock) {
    		this.centerX = Robot.centerX;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(timed) {
    		return Utility.getFPGATime() > (runTime * 1000000 + commandStartedAt);
    	} else {
    		return false;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return centerX/Robot.IMG_WIDTH;
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		Robot.swerveBase.swerveDrive(output, speed, 0.0, false);
		
	}
}
