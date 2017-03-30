package org.usfirst.frc.team2783.robot.commands.autonomous.modes;

import org.usfirst.frc.team2783.robot.Robot;
import org.usfirst.frc.team2783.robot.commands.GyroSwerveDrive;
import org.usfirst.frc.team2783.robot.commands.autonomous.AutoActiveGear;
import org.usfirst.frc.team2783.robot.commands.autonomous.AutoDrive;
import org.usfirst.frc.team2783.robot.vision.AdjustRotationToTarget;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightSideGear extends CommandGroup {

    public RightSideGear() {
    	requires(Robot.swerveBase);
    	
    	addSequential(new GyroSwerveDrive(180, 0.42, true, 3, false));
    	addSequential(new AutoDrive(0, 0, -0.25, true, 1.5));
    	addSequential(new AdjustRotationToTarget(AdjustRotationToTarget.Direction.LOOK_RIGHT));
    	addSequential(new GyroSwerveDrive(180, 0.3, false, 4, true));
    	addSequential(new AutoActiveGear());
    	addSequential(new GyroSwerveDrive(0, 0.25, false, 1, false));
    	
    	
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
