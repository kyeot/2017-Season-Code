package org.usfirst.frc.team2783.robot.commands.autonomous.modes;

import org.usfirst.frc.team2783.robot.Robot;
import org.usfirst.frc.team2783.robot.commands.GyroSwerveDrive;
import org.usfirst.frc.team2783.robot.vision.AdjustRotationToTarget;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SideGear extends CommandGroup {
	
	public enum Side {
		LEFT(true),
		RIGHT(false);
		
		boolean left;
		
		Side(boolean left) {
			this.left = left;
		}
		
		public boolean getLeft() {
			return this.left;
		}
	}

    public SideGear(Side side) {
    	requires(Robot.swerveBase);
    	
    	addSequential(new GyroSwerveDrive(180, 0.375, true, 3.95, false));
    	System.out.print("i want you to die");
    	addSequential(new AdjustRotationToTarget(side.getLeft() ? 
    												AdjustRotationToTarget.Direction.LOOK_LEFT :
    												AdjustRotationToTarget.Direction.LOOK_RIGHT));
    	addSequential(new GyroSwerveDrive(270, 0.3, false, 4, false));
    }
}
