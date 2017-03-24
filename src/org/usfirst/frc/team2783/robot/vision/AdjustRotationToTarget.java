package org.usfirst.frc.team2783.robot.vision;

import org.usfirst.frc.team2783.robot.Robot;
import org.usfirst.frc.team2783.tools.MovingAverage;

import edu.wpi.first.wpilibj.command.PIDCommand;


public class AdjustRotationToTarget extends PIDCommand {
	
	public enum Direction {
		LOOK_RIGHT(1),
		LOOK_LEFT(-1);
		
		int modifier;
		
		Direction(int modifier) {
			this.modifier = modifier;
		}
		
		public int getModifier() {
			return modifier;
		}
		
	}

	public static double kp = 0.5;
	public static double ki = 0.01;
	public static double kd = 0.0;

//	public static double kp = Robot.visionControl.getNumber("pVal", 0.5);
//	public static double ki = Robot.visionControl.getNumber("iVal", 0.01);
//	public static double kd = Robot.visionControl.getNumber("dVal", 0.0);
	
	private MovingAverage error;
	private MovingAverage rotationTarget;	
	
	public static final int IMG_WIDTH = 320;
	public static final int IMG_HEIGHT = 240;
	
	private double centerX = 0.0;
	
	private Direction direction;
	
    public AdjustRotationToTarget(Direction direction) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super(kp, ki, kd);
    	
    	requires(Robot.swerveBase);
    	
    	this.direction = direction;
    	
    	this.error = new MovingAverage(3);
    	this.rotationTarget = new MovingAverage(5);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	this.error.clearValues();
    	this.rotationTarget.clearValues();
    	Robot.swerveBase.setZero();
    	
    	setSetpoint(0.5);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	synchronized(Robot.imgLock){
    		this.centerX = Robot.centerX;
    	}
    	
    	this.error.addValue(getPIDController().getError());
    	this.rotationTarget.addValue(this.centerX);
    	
    	System.out.println("p: " + kp + "; i: " + ki + "; d: " + kd + "; input: " + 
    					   IMG_WIDTH/centerX + "; error: " + error.getAverage()); 
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

    	//return false;
    	System.out.println(error.getAverage());
        return Math.abs(error.getAverage()) < 0.03 && Math.abs(getPIDController().getError()) < 0.03;
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
		return rotationTarget.getAverage()/IMG_WIDTH;
		
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		Robot.swerveBase.swerveDrive(0, 0, direction.getModifier()*output, false);
	}
}





