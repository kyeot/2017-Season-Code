package org.usfirst.frc.team2783.robot.commands.autonomous.modes;

import org.usfirst.frc.team2783.robot.Robot;
import org.usfirst.frc.team2783.robot.commands.GyroSwerveDrive;
import org.usfirst.frc.team2783.robot.commands.autonomous.AutoShoot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShootHigh extends CommandGroup {

	public enum Alliance {
		RED(true),
		BLUE(false);
		
		boolean red;
		
		Alliance(boolean red) {
			this.red = red;
		}
		
		public boolean getRed() {
			return this.red;
		}
	}
	
    public ShootHigh(Alliance alliance) {
    	
    	requires(Robot.shooterBase);
    	
    	addSequential(new AutoShoot(0.792783, 0.95, 11));
    	addSequential(new GyroSwerveDrive(180, 0.5, true, 3, true));
    }
}
