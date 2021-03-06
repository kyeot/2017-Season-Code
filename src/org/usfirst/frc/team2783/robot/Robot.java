
package org.usfirst.frc.team2783.robot;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team2783.robot.commands.autonomous.modes.MiddleGear;
import org.usfirst.frc.team2783.robot.commands.autonomous.modes.ShootHigh;
import org.usfirst.frc.team2783.robot.commands.autonomous.modes.SideGear;
import org.usfirst.frc.team2783.robot.leds.LedStrip;
import org.usfirst.frc.team2783.robot.subsystems.ActiveGearBase;
import org.usfirst.frc.team2783.robot.subsystems.ClimberBase;
import org.usfirst.frc.team2783.robot.subsystems.ShooterBase;
import org.usfirst.frc.team2783.robot.subsystems.SwerveDriveBase;
import org.usfirst.frc.team2783.robot.triggers.LimitSwitch;
import org.usfirst.frc.team2783.robot.vision.GripPipeline;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final ShooterBase shooterBase = new ShooterBase();
	public static final ClimberBase climberBase = new ClimberBase();
	public static final SwerveDriveBase swerveBase = new SwerveDriveBase();
	public static final ActiveGearBase activeGearBase = new ActiveGearBase();
	public static OI oi;
	public static Command autonomous;
	//public static NetworkTable smartDashTable;
	//public static GripPipeline pipeline = new GripPipeline();
	
	public static AnalogInput usSensor1;
	public static AnalogInput usSensor2;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	
	public static final int IMG_WIDTH = 320;
	public static final int IMG_HEIGHT = 240;
	public UsbCamera visionCamera = CameraServer.getInstance().startAutomaticCapture(1);
	public UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);
	private VisionThread visionThread;
	public static double centerX = 0.0;
	public final static Object imgLock = new Object();
	
	public static NetworkTable smartDashTable;
	public static NetworkTable visionControl;
	
	public LimitSwitch gearChecker;
	public LimitSwitch holderPos;
	public static LimitSwitch[] limitSwitches;
	
	public static LedStrip ledStrip;
										

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		
		// chooser.addObject("My Auto", new MyAutoCommand());
		//SmartDashboard.putData("Auto mode", chooser);
		
//		CameraServer usbCameraServer = CameraServer.getInstance();
//		usbCameraServer.startAutomaticCapture("cam0", 0);
		//usbCameraServer.startAutomaticCapture("cam1", 1);
		//usbCameraServer.startAutomaticCapture("cam2", 2);
		
		gearChecker = new LimitSwitch(0);
		holderPos = new LimitSwitch(1);
		limitSwitches = new LimitSwitch[]{gearChecker, holderPos};
		
		oi = new OI();
		
		usSensor1 = new AnalogInput(2);
//		usSensor2 = new AnalogInput(1);
		
		this.smartDashTable = NetworkTable.getTable("SmartDashboard");
		//this.visionControl = NetworkTable.getTable("Usage");
		
		String[] autonomousList = {"Middle Gear",
									"Right Side Gear",
									"Left Side Gear",
									"Blue Shoot",
									"Red Shoot"};
        this.smartDashTable.putStringArray("Auto List", autonomousList);
        
        ledStrip = new LedStrip();
         
        visionCamera.setExposureManual(20);
    	visionCamera.setResolution(IMG_WIDTH, IMG_HEIGHT);
        
        visionThread = new VisionThread(this.visionCamera, new GripPipeline(), pipeline -> {
		
        	switch (pipeline.filterContoursOutput().size()){
        		
        	case 1:
        		synchronized (imgLock) {
	        		Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
	        		centerX = r.x + (r.width/2);
	        		//System.out.println(centerX);
	        	}
        		
        		System.out.println(centerX);
        		
        		break;
        	
        	case 2:
        		synchronized (imgLock) {
	        		Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
	        		Rect r2 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(1));
	        		centerX = ((r.x + (r.width/2)) + (r2.x + (r2.width/2)))/2;
	        		System.out.println(centerX);
	        	}	  
        		break;
        		
        	default:
        		centerX = 1.0;
        		System.out.println(pipeline.filterContoursOutput().size() + " contours found; outside of accepted range of 1-2");
        		break;
        	}
        	
//	        if (pipeline.filterContoursOutput().size() == 2) {	
//	        	synchronized (imgLock) {
//	        		Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
//	        		Rect r2 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(1));
//	        		centerX = ((r.x + (r.width))/2 + (r2.x + (r2.width))/2);
//	        		//System.out.println(centerX);
//	        	}	        	
//	        }
		});
		visionThread.start();
		
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
		ledStrip.solid(LedStrip.Color.PURPLE);
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
		
		visionCamera.setExposureManual(1);
    	
    	//Gets the autonomous selector value from the dashboard
    	String autoSelected = SmartDashboard.getString("Auto Selector", "None");
    	
    	//Switches the autonomous mode based on the value from the SmartDashboard
		switch(autoSelected) {
			case "Middle Gear":
				autonomous = new MiddleGear();
				break;
			case "Red Shoot":
				autonomous = new ShootHigh(ShootHigh.Alliance.RED);
				break;
			case "Blue Shoot":
				autonomous = new ShootHigh(ShootHigh.Alliance.BLUE);
				break;
			case "Right Side Gear":
				autonomous = new SideGear(SideGear.Side.RIGHT);
				break;
			case "Left Side Gear":
				autonomous = new SideGear(SideGear.Side.LEFT);
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
		ledStrip.solid(LedStrip.Color.ORANGE);
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
		
		System.out.println(Robot.swerveBase.getGyroAngle(false));

//    	System.out.println("Limit 8: " + Robot.limitSwitches[0].get());
//    	System.out.println("Limit 9: " + Robot.limitSwitches[1].get());
    	//System.out.println("Analog0:" + Robot.limitSwitches[0].limitSwitch.getValue());
    	
//    	if(limitSwitches[0].get()) {
//    		ledStrip.solid(LedStrip.Color.YELLOW);
//    	} else {
//    		ledStrip.solid(LedStrip.Color.RED);
//    	}
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
