package org.usfirst.frc.team2783.robot.commands.autonomous;

import org.usfirst.frc.team2783.robot.Robot;
import org.usfirst.frc.team2783.robot.subsystems.RetrieverClimberBase.RetrieverDirection;
import org.usfirst.frc.team2783.robot.subsystems.SwerveDriveBase.SwerveModule;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoShoot extends Command {

	private double shooterSpeed;
	private double agitatorSpeed;
	
	private long commandStartedAt;
	
	private double runTime;
	
    public AutoShoot(double shooterSpeed, double agitatorSpeed, double runTime) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.shooterBase);
    	
    	this.runTime = runTime;
    	
    	this.shooterSpeed = shooterSpeed;
    	this.agitatorSpeed = agitatorSpeed;
    	
    	//Run Time is in Seconds
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
       }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shooterBase.setShooterSpeedVbus(shooterSpeed);
    	Robot.shooterBase.setAgitatorSpeedVbus(agitatorSpeed);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Utility.getFPGATime() > (runTime * 1000000 + commandStartedAt);
    	//Run command for 6 seconds
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
