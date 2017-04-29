package org.usfirst.frc.team2783.robot.vision;

import org.usfirst.frc.team2783.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 *
 */
public class VisionSwerveDrive extends PIDCommand {

    private static double p = 1;
	private static double i = 0.0;
	private static double d = 0.0;
	
	private double speed;
	
	private double centerX;
	
	private long commandStartedAt;
	private double runTime;
	private boolean timed;
	private boolean usStop;
	
	private PIDController gyroPid;
	private PIDOutputClass gyroOut;
	
	private double gyroPidOutput;
	
	public class PIDOutputClass implements PIDOutput {
		
		@Override
		public void pidWrite(double output) {
			gyroPidOutput = output;
		}
	}

	public VisionSwerveDrive(double speed, double runTime, boolean usStop) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super(p, i, d);
    	
    	gyroOut = new PIDOutputClass();
    	gyroPid = new PIDController(0.02, 0, 0, Robot.swerveBase.getNavSensor(), gyroOut);
    	
    	requires(Robot.swerveBase);
    	
    	this.speed = speed;
    	this.runTime = runTime;
    	this.usStop = usStop;
    	timed = true;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	getPIDController().setSetpoint(0.5);
    	
    	gyroPid.setSetpoint(Robot.swerveBase.getGyroAngle(false));
    	gyroPid.enable();
    	
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
    	return	(timed && (Utility.getFPGATime() > (runTime * 1000000 + commandStartedAt)) || 
				((Robot.usSensor1.getValue() < 300) && usStop));
    }

    // Called once after isFinished returns true
    protected void end() {
    	gyroPid.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	gyroPid.disable();
    }

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return centerX/Robot.IMG_WIDTH;
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		System.out.println(output);
		if(this.centerX != 1.0) {
			Robot.swerveBase.swerveDrive(output, -speed, gyroPidOutput, false);
		} else {
			Robot.swerveBase.swerveDrive(0, 0, 0, false);
		}
		
		
	}
}
