package org.usfirst.frc.team2783.robot.vision;

import org.usfirst.frc.team2783.robot.Robot;
import org.usfirst.frc.team2783.tools.MovingAverage;

import edu.wpi.first.wpilibj.command.PIDCommand;


public class AdjustRotationToTarget extends PIDCommand {

	public static double kp = 0.666;
	public static double ki = 0.0296;
	public static double kd = 0.00025;
	
	private boolean buttonLastPressed = false;

	public static final int IMG_WIDTH = 320;
	public static final int IMG_HEIGHT = 240;
	
	private double centerX = 0.0;
	
    public AdjustRotationToTarget() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super(kp, ki, kd);
    	
    	requires(Robot.swerveBase);
    	
    	getPIDController().setContinuous(true);
    	
    	setSetpoint(0.5);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//rotationTarget.clearValues();
    	Robot.swerveBase.setZero();
    	
    	setSetpoint(0.5);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	synchronized(Robot.imgLock){
    		this.centerX = Robot.centerX;
    	}
    	
    	System.out.println("p: " + kp + "; i: " + ki + "; d: " + kd + "; input: " + centerX/IMG_WIDTH); 
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

    	return false;
        //return Math.abs(error.addValue(getPIDController().getError())) < 0.10;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.swerveBase.setZero();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return centerX/IMG_WIDTH;
		
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		Robot.swerveBase.swerveDrive(0, 0, -output, false);
	}
}





