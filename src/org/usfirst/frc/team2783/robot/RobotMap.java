package org.usfirst.frc.team2783.robot;

import com.ctre.CANTalon;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	
	final public static int XBOX_CONTROLLER_ID = 0;
	final public static int MANIPULATOR_CONTROLLER_ID = 1;
	

	final public static int FRONT_LEFT_WHEEL = 0;
	final public static int FRONT_RIGHT_WHEEL = 1;
	final public static int REAR_LEFT_WHEEL = 2;
	final public static int REAR_RIGHT_WHEEL = 3;
	
	final public static int FRONT_LEFT_SWIVEL = 0;
	final public static int FRONT_RIGHT_SWIVEL = 1;
	final public static int REAR_LEFT_SWIVEL = 2;
	final public static int REAR_RIGHT_SWIVEL = 3;
	
}
