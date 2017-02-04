package org.usfirst.frc.team2783.robot.commands.autonomous.modes;

import org.usfirst.frc.team2783.robot.commands.autonomous.AutoDrive;
import org.usfirst.frc.team2783.robot.commands.autonomous.AutoShoot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GetGearAndShoot extends CommandGroup {

    public GetGearAndShoot() {
    	
    	addSequential(new AutoDrive(1, 0, 0.25, true, 2));
    	addSequential(new AutoDrive(0.5, 0, 0, true, 1));
    	addSequential(new AutoDrive(-0.5, 0, 0.25, true, 2));
    	addSequential(new AutoShoot(1, 0, 5));
    	
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
