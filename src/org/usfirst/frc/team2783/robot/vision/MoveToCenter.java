package org.usfirst.frc.team2783.robot.vision;

import org.usfirst.frc.team2783.robot.Robot;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 *
 */
public class MoveToCenter extends PIDCommand {

	final private static double p = 0.02; //0.01
	final private static double i = 0.0; //0.0
	final private static double d = 0.0; //0.0
	
	private double angle;
	private double speed;
	
	private boolean fieldOriented;
	private boolean usStop = false;
	
	private double runTime;
	
	private long commandStartedAt;
	private boolean timed = false; 
	
	
	private double pidOutput;
	private double angleOffset = 0;
	private double centerX;
	
    public MoveToCenter(double angle, double speed, boolean fieldOriented, double runTime, boolean usStop) {
    	super(p, i, d);
    	
    	this.angle = angle;
    	this.speed = speed;
    	this.fieldOriented = fieldOriented;
    	this.usStop = usStop;
    	this.runTime = runTime;
    	
    	timed = true;
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.swerveBase);
    }
    
    public MoveToCenter(double angle, double speed, boolean fieldOriented) {
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
    	
    	angleOffset = angle - Robot.swerveBase.getGyroAngle(false);
    	
    	setSetpoint(angle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.swerveBase.polarSwerveDrive(angle, speed, pidOutput, fieldOriented);
    	synchronized(Robot.imgLock) {
    		this.centerX = Robot.centerX;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	System.out.println(Math.abs((centerX/Robot.IMG_WIDTH)-0.5));
    	//System.out.println(Robot.usSensor1.getValue());
    	return Math.abs((centerX/Robot.IMG_WIDTH)-0.5) < 0.1;
//    	return timed &&  Utility.getFPGATime() > (runTime * 1000000 + commandStartedAt);
    	//return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("hey");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return Robot.swerveBase.getGyroAngle(false) + angleOffset;
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		this.pidOutput = output;
		
	}
}