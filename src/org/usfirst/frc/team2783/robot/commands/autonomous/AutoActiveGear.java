package org.usfirst.frc.team2783.robot.commands.autonomous;

import org.usfirst.frc.team2783.robot.Robot;
import org.usfirst.frc.team2783.robot.RobotMap;
import org.usfirst.frc.team2783.robot.subsystems.RetrieverClimberBase.GearHolderLift;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoActiveGear extends Command {
	
	boolean end = false;
	
	double speed;
	double runTime;
	
	CANTalon gearLift = new CANTalon(RobotMap.GEAR_HOLDER_ID);
	
	private long commandStartedAt;
	
	private double direction;
	
	public AutoActiveGear(double speed, double runTime) {
		requires(Robot.retriever);
		
		this.speed = speed;
		this.runTime = runTime;		
	}
	
    public AutoActiveGear(int direction) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	this.direction = direction;
    	requires(Robot.retriever);    	

    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	commandStartedAt = Utility.getFPGATime();
    	
   }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	gearLift.set(speed);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Utility.getFPGATime() > (runTime * 1000000 + commandStartedAt);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.retriever.rollRoller(0);
    	commandStartedAt = 0;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
