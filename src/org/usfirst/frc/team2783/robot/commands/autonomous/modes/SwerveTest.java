package org.usfirst.frc.team2783.robot.commands.autonomous.modes;

import org.usfirst.frc.team2783.robot.Robot;
import org.usfirst.frc.team2783.robot.commands.autonomous.AutoGyroDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SwerveTest extends CommandGroup {

    public SwerveTest() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires (Robot.swerveBase);
    	
    	addSequential(new AutoGyroDrive(90, 0.5, true, 5));
    	
    }
}
