package org.usfirst.frc.team2783.robot;

import org.usfirst.frc.team2783.robot.commands.RetrieveGear;
import org.usfirst.frc.team2783.robot.commands.ShooterDrive;
import org.usfirst.frc.team2783.robot.commands.ToggleRetriever;
import org.usfirst.frc.team2783.robot.commands.VisionTrigger;
import org.usfirst.frc.team2783.robot.subsystems.RetrieverClimberBase.RetrieverDirection;

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
	
	Button gearAuto = new JoystickButton(manipulator, 2);
	Button shooter = new JoystickButton(manipulator, 8);
	Button gatherer = new JoystickButton(manipulator, 6);
	Button climber = new JoystickButton(manipulator, 5);
	Button gearPlace = new JoystickButton(manipulator, 4);
	Button retrieveGear = new JoystickButton(manipulator, 7);
	
	Button visionButton = new JoystickButton(driver, 1);
	

	public OI() {
		visionButton.toggleWhenPressed(new VisionTrigger());
		
		retrieveGear.whenPressed(new RetrieveGear());
		
		shooter.toggleWhenPressed(new ShooterDrive());
		
		climber.whenPressed(new ToggleRetriever(RetrieverDirection.RET_OUT, 1));
		gatherer.whenPressed(new ToggleRetriever(RetrieverDirection.RET_OUT, 0));
	}

	


}
