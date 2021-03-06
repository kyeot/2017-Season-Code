package org.usfirst.frc.team2783.robot.commands.autonomous;

import org.usfirst.frc.team2783.robot.Robot;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoShoot extends Command {

	private double shooterSpeed;
	
	private long commandStartedAt;
	private double runTime;
	
    public AutoShoot(double shooterSpeed, double agitatorSpeed, double runTime) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.shooterBase);
    	
    	//Run Time is in Seconds
    	this.runTime = runTime;
    	
    	this.shooterSpeed = shooterSpeed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	commandStartedAt = Utility.getFPGATime();
       }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shooterBase.setShooterSpeedVbus(shooterSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Utility.getFPGATime() > (runTime * 1000000 + commandStartedAt);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooterBase.setShooterSpeedVbus(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
