package org.usfirst.frc.team2783.robot.commands.autonomous.modes;

import org.usfirst.frc.team2783.robot.Robot;
import org.usfirst.frc.team2783.robot.vision.MoveToCenter;
import org.usfirst.frc.team2783.robot.vision.VisionSwerveDrive;

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
    	
//    	addSequential(new GyroSwerveDrive(180, 0.375, true, 3.95, false));
//    	addSequential(new AdjustRotationToTarget(side.getLeft() ? 
//    												AdjustRotationToTarget.Direction.LOOK_LEFT :
//    												AdjustRotationToTarget.Direction.LOOK_RIGHT));
//    	addSequential(new GyroSwerveDrive(270, 0.3, false, 4, false));
		
		addSequential(new MoveToCenter(180, 0.35, true, 5, false));
		addSequential(new VisionSwerveDrive(0.3, 10, true));
    }
}
