package org.usfirst.frc.team2783.robot.commands.autonomous.modes;

import org.usfirst.frc.team2783.robot.Robot;
import org.usfirst.frc.team2783.robot.commands.GyroSwerveDrive;
import org.usfirst.frc.team2783.robot.commands.autonomous.AutoDrive;
import org.usfirst.frc.team2783.robot.commands.autonomous.AutoShoot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Shoot extends CommandGroup {

    public Shoot() {
    	
    	requires(Robot.shooterBase);
    	
    	addSequential(new AutoShoot(0.77278320176183, 0.95, 13));
    	addSequential(new GyroSwerveDrive(180, 0.5, true, 0.5));
    	addSequential(new AutoDrive(180, 0.5, 0.25, true, 1));
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
