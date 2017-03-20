package org.usfirst.frc.team2783.robot.commands;

import org.usfirst.frc.team2783.robot.OI;
import org.usfirst.frc.team2783.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SwerveDrive extends Command {
	
	public enum ControlType {
		CONTROLLER(1, 0, 4, 5, 4, 3, 6),
		JOYSTICK(1, 0, 2, 1, 2, 4, 6);
		
		int fbAxis;
		int rlAxis;
		int rotAxis;
		
		int doubleSpeed;
		int centerGyro;
		int zeroModules;
		int dockingMode;
		
		private ControlType(
				int fbAxis, int rlAxis, int rotAxis,
				int doubleSpeed, int centerGyro, int zeroModules, int dockingMode) {
			
			this.fbAxis = fbAxis;
			this.rlAxis = rlAxis;
			this.rotAxis = rotAxis;
			this.doubleSpeed = doubleSpeed;
			this.centerGyro = centerGyro;
			this.zeroModules = zeroModules;
			this.dockingMode = dockingMode;
		}
		
		public double getFBAxis() {
			return OI.driver.getRawAxis(fbAxis);
		}
		
		public double getRLAxis() {
			return OI.driver.getRawAxis(rlAxis);
		}
		
		public double getRotAxis() {
			return OI.driver.getRawAxis(rotAxis);
		}
		
		public boolean getDoubleSpeedButton() {
			return OI.driver.getRawButton(doubleSpeed);
		}
		
		public boolean getCenterGyroButton() {
			return OI.driver.getRawButton(centerGyro);
		}
		
		public boolean getZeroModulesButton() {
			return OI.driver.getRawButton(zeroModules);
		}
		
		public boolean getDockingModeButton() {
			return OI.driver.getRawButton(dockingMode);
		}
		
	}

	private ControlType controlType;

	//Makes SwerveDrive require the subsystem swerveBase
    public SwerveDrive(ControlType controlType) {
    	requires(Robot.swerveBase);
		this.controlType = controlType;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	//Sets input for swerveDrive method as input from controller stick axes. Note: FBValue is negative as required by doc linked to in swerveDrive method
    	Double fbValue = controlType.getFBAxis()/2;
    	Double rlValue = -(controlType.getRLAxis())/2;
    	Double rotValue = controlType.getRotAxis()/2;
    	
    	//Makes it so if the left stick is barely moved at all it doesn't move at all
    	if ((fbValue > -.2 && fbValue < .2) && (rlValue > -.2 && rlValue < .2)){
    		fbValue = 0.0;
    		rlValue = 0.0;
    	}
    	
    	//Makes it so if the right stick is barely moved at all it doesn't move at all
    	if (rotValue > -.2 && rotValue < .2){
    		rotValue = 0.0;
    	}
    	
    	//While the left bumper is held goes half speed
    	if(controlType.getDoubleSpeedButton()) {
    		fbValue *= 2;
    		rlValue *= 2;
    		rotValue *= 2;
    	}
    	
    	//If the X button is pressed resets the Swerve Modules
    	if(controlType.getZeroModulesButton()) {
    		Robot.swerveBase.setZero();
    	}
    	
    	//If Y is pressed resets the field orientation
    	if(controlType.getCenterGyroButton()) {
    		Robot.swerveBase.resetGyroNorth(0, 0);
    	}
    	
    	if(controlType.getDockingModeButton()) {
    		System.out.println("Docking Mode");
    		
    		Robot.swerveBase.swerveDrive(rlValue, fbValue, rotValue, false);
    	} else {
    		Robot.swerveBase.swerveDrive(fbValue, rlValue, rotValue, true);
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

}
