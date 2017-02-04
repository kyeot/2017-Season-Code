package org.usfirst.frc.team2783.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team2783.robot.commands.ToggleRetriever;
import org.usfirst.frc.team2783.robot.subsystems.RetrieverClimberBase.RetrieverDirection;
import org.usfirst.frc.team2783.robot.commands.Agitator;
import org.usfirst.frc.team2783.robot.commands.Climb;
import org.usfirst.frc.team2783.robot.commands.ShooterDrive;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	public static Joystick manipulator = new Joystick(RobotMap.MANIPULATOR_CONTROLLER_ID);
	public static Joystick xBoxController = new Joystick(RobotMap.XBOX_CONTROLLER_ID);
	
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as detzermined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	
<<<<<<< HEAD
	public static Joystick xBoxController = new Joystick(RobotMap.XBOX_CONTROLLER_ID);
	public static Joystick joystick = new Joystick(RobotMap.JOYSTICK_ID);
	
	public OI() {
	}
=======
	Button shooter = new JoystickButton(manipulator, 8);
	Button gatherer = new JoystickButton(manipulator, 6);
	Button climber = new JoystickButton(manipulator, 5);
	Button agitator = new JoystickButton(manipulator, 4);
	
	
	public OI() {
		
		shooter.toggleWhenPressed(new ShooterDrive());
		
		agitator.whenPressed(new Agitator());
		
		climber.whenPressed(new Climb(RetrieverDirection.RET_IN));
		
		gatherer.whenPressed(new ToggleRetriever(RetrieverDirection.RET_IN));
		
		
	}
	
>>>>>>> refs/remotes/origin/manipulator
}
