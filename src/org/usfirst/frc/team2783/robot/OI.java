package org.usfirst.frc.team2783.robot;

import org.usfirst.frc.team2783.robot.commands.Climb;
import org.usfirst.frc.team2783.robot.commands.CollectGear;
import org.usfirst.frc.team2783.robot.commands.GyroSwerveDrive;
import org.usfirst.frc.team2783.robot.commands.ShooterDrive;
import org.usfirst.frc.team2783.robot.commands.ToggleLimits;
import org.usfirst.frc.team2783.robot.triggers.AxisButton;
import org.usfirst.frc.team2783.robot.triggers.Dpad;
import org.usfirst.frc.team2783.robot.triggers.LimitSwitch;
import org.usfirst.frc.team2783.robot.util.DiscreteToggle;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	public static Joystick manipulator = new Joystick(RobotMap.MANIPULATOR_CONTROLLER_ID);
	public static Joystick driver = new Joystick(RobotMap.XBOX_CONTROLLER_ID);
	
	public static DiscreteToggle overrideToggle = new DiscreteToggle();
	
	Button shooter = new JoystickButton(manipulator, 8);
	Button switchOverride = new JoystickButton(manipulator, 5);
		
	AxisButton climber = new AxisButton(manipulator, 3);
	
	AxisButton gearLift = new AxisButton(manipulator, 5);
	AxisButton gearHolder = new AxisButton(manipulator, 1);
	
	Button visionButton = new JoystickButton(driver, 1);
	
	LimitSwitch gearCheck = Robot.limitSwitches[0];
	
	Dpad gyroDriveNorth = new Dpad(driver, 0);
	Dpad gyroDriveSouth = new Dpad(driver, 180);
	Dpad gyroDriveEast = new Dpad(driver, 90);
	Dpad gyroDriveWest = new Dpad(driver, 270);

	public OI() {
		//visionButton.toggleWhenPressed(new AdjustRotationToTarget(AdjustRotationToTarget.Direction.LOOK_LEFT));
		
		switchOverride.whenActive(new ToggleLimits());
		
		shooter.toggleWhenPressed(new ShooterDrive());
		
		//climber.whileActive(new Climb());
		
		gearCheck.whenActive(new CollectGear());
		
		gyroDriveNorth.whileActive(new GyroSwerveDrive(0.0, 0.3, false));
		gyroDriveSouth.whileActive(new GyroSwerveDrive(180.0, 0.3, false));
		gyroDriveEast.whileActive(new GyroSwerveDrive(90.0, 0.3, false));
		gyroDriveWest.whileActive(new GyroSwerveDrive(270.0, 0.3, false));
	}

}
