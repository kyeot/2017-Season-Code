package org.usfirst.frc.team2783.robot.commands;

import org.usfirst.frc.team2783.robot.Robot;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 *
 */
public class GyroSwerveDrive extends PIDCommand {

	final private static double p = 0.01;
	final private static double i = 0.0;
	final private static double d = 0.0;
	
	private double angle;
	private double speed;
	private boolean fieldOriented;
	private double runTime;
	
	private long commandStartedAt;
	private boolean timed = false; 
	
	private double pidOutput;
	
    public GyroSwerveDrive(double angle, double speed, boolean fieldOriented, double runTime) {
    	super(p, i, d);
    	
    	this.angle = angle;
    	this.speed = speed;
    	this.fieldOriented = fieldOriented;
    	this.runTime = runTime;
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.swerveBase);
    }
    
    public GyroSwerveDrive(double angle, double speed, boolean fieldOriented) {
    	super(p, i, d);
    	
    	this.angle = angle;
    	this.speed = speed;
    	this.fieldOriented = fieldOriented;
    	this.runTime = runTime;
    	
    	timed = false;
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.swerveBase);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	commandStartedAt = Utility.getFPGATime();
    	
    	setSetpoint(angle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.swerveBase.polarSwerveDrive(angle, speed, pidOutput, fieldOriented);
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
		return Robot.swerveBase.getGyroAngle(true);
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		this.pidOutput = output;
		
	}
}
