
package org.usfirst.frc.team2783.robot;

<<<<<<< HEAD
import org.usfirst.frc.team2783.robot.commands.autonomous.modes.DriveTest;
=======
import org.usfirst.frc.team2783.robot.subsystems.RetrieverClimberBase;
import org.usfirst.frc.team2783.robot.subsystems.ShooterBase;
>>>>>>> refs/remotes/origin/manipulator
import org.usfirst.frc.team2783.robot.subsystems.SwerveDriveBase;


import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	//public static final SwerveDriveBase swerveBase = new SwerveDriveBase();
	public static final ShooterBase shooterBase = new ShooterBase();
	public static final RetrieverClimberBase retriever = new RetrieverClimberBase();
	public static final SwerveDriveBase swerveBase = new SwerveDriveBase();
	public static OI oi;
	public static Command autonomous;
	public static NetworkTable smartDashTable;
	

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		oi = new OI();
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);
		this.smartDashTable = NetworkTable.getTable("SmartDashboard");
		
		CameraServer usbCameraServer = CameraServer.getInstance();
		usbCameraServer.startAutomaticCapture("cam0", 0);
		
		String[] autonomousList = {"DriveTest"};
        this.smartDashTable.putStringArray("Auto List", autonomousList);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
    	
    	//Gets the autonomous selector value from the dashboard
    	String autoSelected = SmartDashboard.getString("Auto Selector", "None");
    	
    	//Switches the autonomous mode based on the value from the SmartDashboard
		switch(autoSelected) {
			case "DriveTest":
				autonomous = new DriveTest();
				break;
			case "None":
			default:
				autonomous = null;
				break;
		} 
		
    	if(autonomous != null) {
    		autonomous.start();
    	}
    	
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
