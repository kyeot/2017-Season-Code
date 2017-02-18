package org.usfirst.frc.team2783.robot.vision;

import org.usfirst.frc.team2783.robot.Robot;
import org.usfirst.frc.team2783.tools.MovingAverage;

import edu.wpi.first.wpilibj.command.PIDCommand;


public class AdjustRotationToTarget extends PIDCommand {

	final public static double kp = 0.1;
	final public static double ki = 0.01;
	final public static double kd = 0.0;

	GripPipeline pipeline;
	
	private Double centerX;
	MovingAverage rotationTarget;
	MovingAverage error;
	
    public AdjustRotationToTarget() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super(kp, ki, kd);
    	
    	requires(Robot.swerveBase);
    	this.rotationTarget = new MovingAverage(3);
    	this.error = new MovingAverage(5);
    	this.pipeline = new GripPipeline();
    	
    	setSetpoint(0.5);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	rotationTarget.clearValues();
    	Robot.swerveBase.setZero();
    	pipeline.startThread();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	centerX = pipeline.getCenterX();
    	rotationTarget.addValue(centerX);
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
		//return rotationTarget.getAverage() / Robot.pipeline.IMG_WIDTH;
		return 0;
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
    	System.out.println(pipeline.getCenterX());
		if (Math.abs(output) < 0.4) {
			Robot.swerveBase.tankDrive(-output, output);
		} else {
			Robot.swerveBase.tankDrive(-0.65, 0.65);
		}
		
	}
	
	
}





