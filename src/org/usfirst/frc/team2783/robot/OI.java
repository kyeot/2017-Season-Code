package org.usfirst.frc.team2783.robot;

import org.usfirst.frc.team2783.robot.commands.Climb;
import org.usfirst.frc.team2783.robot.commands.GyroSwerveDrive;
import org.usfirst.frc.team2783.robot.commands.RetrieveGear;
import org.usfirst.frc.team2783.robot.commands.ShooterDrive;
import org.usfirst.frc.team2783.robot.commands.ToggleRetriever;
import org.usfirst.frc.team2783.robot.commands.VisionTrigger;
import org.usfirst.frc.team2783.robot.subsystems.RetrieverClimberBase.RetrieverDirection;
import org.usfirst.frc.team2783.robot.triggers.AxisButton;
import org.usfirst.frc.team2783.robot.triggers.Dpad;
import org.usfirst.frc.team2783.robot.vision.AdjustRotationToTarget;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	public static Joystick manipulator = new Joystick(RobotMap.MANIPULATOR_CONTROLLER_ID);
	public static Joystick driver = new Joystick(RobotMap.XBOX_CONTROLLER_ID);
	public static Joystick pidTuner = new Joystick(2);
	
	Button gearAuto = new JoystickButton(manipulator, 2);
	Button shooter = new JoystickButton(manipulator, 8);
	Button gatherer = new JoystickButton(manipulator, 6);
	Button gearPlace = new JoystickButton(manipulator, 4);
	Button retrieveGear = new JoystickButton(manipulator, 7);
	
	AxisButton climber = new AxisButton(manipulator, 3);
	
	Button visionButton = new JoystickButton(driver, 1);
	
	Dpad gyroDriveNorth = new Dpad(driver, 0);
	Dpad gyroDriveSouth = new Dpad(driver, 180);
	Dpad gyroDriveEast = new Dpad(driver, 90);
	Dpad gyroDriveWest = new Dpad(driver, 270);
	
	public static Dpad pValueUp = new Dpad(pidTuner, 0);
	public static Dpad pValueDown = new Dpad(pidTuner, 180);
	
	VisionTrigger visionTrigger;

	public OI() {
		visionButton.toggleWhenPressed(new AdjustRotationToTarget());
		retrieveGear.toggleWhenPressed(new RetrieveGear());
		
		shooter.toggleWhenPressed(new ShooterDrive());
		
		climber.whileActive(new Climb());
		
		gatherer.whenPressed(new ToggleRetriever(RetrieverDirection.RET_OUT, 0));
		
		gyroDriveNorth.whileActive(new GyroSwerveDrive(0.0, 0.3, false));
		gyroDriveSouth.whileActive(new GyroSwerveDrive(180.0, 0.3, false));
		gyroDriveEast.whileActive(new GyroSwerveDrive(90.0, 0.3, false));
		gyroDriveWest.whileActive(new GyroSwerveDrive(270.0, 0.3, false));
	}

	


}
