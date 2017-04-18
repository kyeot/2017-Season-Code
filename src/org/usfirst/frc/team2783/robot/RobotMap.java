package org.usfirst.frc.team2783.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	//Controllers
	final public static int XBOX_CONTROLLER_ID = 0;
	final public static int MANIPULATOR_CONTROLLER_ID = 1;
	
	//Climber Base
	final public static int CLIMBER_WHEEL_ID = 6;
	
	//Shooter Base
	final public static int SHOOTER_WHEEL_ID = 5;
	
	//Swerve Base
	final public static int FRONT_LEFT_WHEEL = 2;
	final public static int FRONT_RIGHT_WHEEL = 4;
	final public static int REAR_LEFT_WHEEL = 3;
	final public static int REAR_RIGHT_WHEEL = 1;
	
	final public static int FRONT_LEFT_SWIVEL = 2;
	final public static int FRONT_RIGHT_SWIVEL = 0;
	final public static int REAR_LEFT_SWIVEL = 3;
	final public static int REAR_RIGHT_SWIVEL = 1;
	
	//Active Gear Base
	final public static int GEAR_ROLLER_ID = 4;
	final public static int GEAR_HOLDER_ID = 0;
	
	
}
