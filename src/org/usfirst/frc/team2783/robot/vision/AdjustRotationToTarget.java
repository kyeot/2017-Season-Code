package org.usfirst.frc.team2783.robot.vision;

import java.util.NoSuchElementException;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team2783.robot.Robot;
import org.usfirst.frc.team2783.tools.MovingAverage;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.vision.VisionThread;


public class AdjustRotationToTarget extends PIDCommand {

	final public static double kp = 0.45;
	final public static double ki = 0;
	final public static double kd = 0.0;

	MovingAverage rotationTarget;
	MovingAverage error;
	public static final int IMG_WIDTH = 320;
	public static final int IMG_HEIGHT = 240;
	private double centerX = 0.0;
	private final Object imgLock = new Object();
	private UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(2);
	private VisionThread visionThread;
	
    public AdjustRotationToTarget() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super(kp, ki, kd);
    	
    	requires(Robot.swerveBase);
    	
    	getPIDController().setContinuous(true);
    	//getPIDController().setAbsoluteTolerance(0.1);
    	
    	this.rotationTarget = new MovingAverage(3);
    	this.error = new MovingAverage(5);
    	
    	setSetpoint(0.5);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	rotationTarget.clearValues();
    	Robot.swerveBase.setZero();
    	
    	setSetpoint(0.5);
    	
    	camera.setExposureManual(3);
    	camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
    	
		visionThread = new VisionThread(this.camera, new GripPipeline(), pipeline -> {
	        if (pipeline.filterContoursOutput().size() == 2) {	   
	        	synchronized (imgLock) {
	        	Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
	        	Rect r2 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(1));
	        		centerX = ((r.x + (r.width/2)) + (r2.x + (r2.width/2)));
	        	}	        	
	    }
	});
		visionThread.start();
}

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	double centerX;
    	synchronized(imgLock){
    		centerX = this.centerX;
    		rotationTarget.addValue(centerX);

    	}

    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

    	return false;
     //   return Math.abs(error.addValue(getPIDController().getError())) < 0.20;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("finished");
    	Robot.swerveBase.setZero();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		System.out.println(centerX);
			return centerX/IMG_WIDTH;
		
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		Robot.swerveBase.swerveDrive(0, 0, -output, false);
	}
}





