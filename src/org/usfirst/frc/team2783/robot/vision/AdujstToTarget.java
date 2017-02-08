package org.usfirst.frc.team2783.robot.vision;

import org.usfirst.frc.team2783.robot.Robot;
import org.usfirst.frc.team2783.tools.MovingAverage;

import edu.wpi.first.wpilibj.command.PIDCommand;


public class AdujstToTarget extends PIDCommand {

	final public static double kp = 0.1;
	final public static double ki = 0.01;
	final public static double kd = 0.0;
	
	private GripPipeline pipeline;
	private Double centerX;
	MovingAverage rotationTarget;
	MovingAverage error;
	
    public AdujstToTarget() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super(kp, ki, kd);
    	
    	requires(Robot.swerveBase);
    	this.pipeline = new GripPipeline();
    	this.rotationTarget = new MovingAverage(3);
    	this.error = new MovingAverage(5);
    	
    	setSetpoint(0.5);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	rotationTarget.clearValues();
    	//Add setZero stuff
    	
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
    	Robot.swerveBase.swerveDrive(0.0, 0.0, 0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return rotationTarget.getAverage() / pipeline.IMG_WIDTH;
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		if (Math.abs(output) > 0.4) {
			Robot.swerveBase.tankDrive(-output, output);
		} else {
			Robot.swerveBase.tankDrive(-0.65, 0.65);
		}
		
	}
	
	
}





