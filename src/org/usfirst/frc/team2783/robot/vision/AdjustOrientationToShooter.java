package org.usfirst.frc.team2783.robot.vision;

import org.usfirst.frc.team2783.robot.Robot;
import org.usfirst.frc.team2783.robot.util.MovingAverage;

import edu.wpi.first.wpilibj.command.PIDCommand;

public class AdjustOrientationToShooter extends PIDCommand {
	final public static double kp = 0.1;
	final public static double ki = 0.01;
	final public static double kd = 0.0;

	private GripPipeline pipeline;
	private double area;
	private double distance;
	private double areaToDistance = area;
	MovingAverage areaTarget;
	MovingAverage error;
    public AdjustOrientationToShooter() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super(kp, ki, kd);
    	
    	requires(Robot.swerveBase);
    	this.pipeline = new GripPipeline();
    	this.areaTarget = new MovingAverage(3);
    	this.error = new MovingAverage(5);
    	
    	setSetpoint(0.5);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	areaTarget.clearValues();
    	Robot.swerveBase.setZero();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	area = pipeline.getArea();
    	areaTarget.addValue(areaToDistance);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(error.addValue(getPIDController().getError())) < 0.20;
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
		return areaTarget.getAverage() / distance;
	}

	@Override
	protected void usePIDOutput(double output) {
		System.out.println(output);
		// TODO Auto-generated method stub
		
	}
}
