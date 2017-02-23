package org.usfirst.frc.team2783.robot.subsystems;

import org.usfirst.frc.team2783.robot.RobotMap;
import org.usfirst.frc.team2783.robot.commands.SwerveDrive;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class SwerveDriveBase extends Subsystem {
	
	public SwerveModule frMod;
	public SwerveModule flMod;
	public SwerveModule rrMod;
	public SwerveModule rlMod;
	
	private AHRS navSensor;
	private double angleOffset = 0;
	
	//IDs kp ki and kd
	private final double kp = 0.022; //0.025
	private final double ki = 0.04; //0.04
	private final double kd = 0.025; //0.025
	
	final private double ENCODER_TICKS_FOR_ADJUSTER_TRAVEL = 1;
	
	//Class for controlling PIDOutput
	public class PIDOutputClass implements PIDOutput {
		private VictorSP motor;
		
		public PIDOutputClass(VictorSP motor) {
			this.motor = motor;
		}
		
		@Override
		public void pidWrite(double output) {
			motor.set(output);
		}
	}
	
	//Class used for making and controlling Swerve Modules
	public class SwerveModule {
		  


		CANTalon driveMot;
		VictorSP swivelMot;
		Encoder enc;
		
		PIDOutputClass pidOut;
		PIDController pidCont;
		
		
		double lastAngle;
		
		//Constructor
		public SwerveModule(
				VictorSP swivelMot,
				CANTalon driveMot,
				Encoder enc
				) {
			
			this.driveMot = driveMot;
			this.swivelMot = swivelMot;
			this.enc = enc;
			
			
			pidOut = new PIDOutputClass(
							swivelMot
						);
			
			pidCont = new PIDController(
							kp, ki, kd,
							enc,
							pidOut
						);
			
			
			pidCont.setInputRange(-360, 360);
			pidCont.setContinuous();
			
			enc.setDistancePerPulse(0.875);
			enc.setSamplesToAverage(127);
		}
		
		//Sets a swerve modules angle and speed
		public void setModule(double angle, double speed) {
			
			//Keeps the angle within 0 and 360
			if(angle < 0) {
				angle += 360;
			}
			
			//Resets the encoder if it gets too high or low
			double curAngle = getAngle();
			if(curAngle >= 360 || curAngle <= -360) {
				enc.reset();
			}
			
			//Makes the module go to the opposite angle and go backwards when it's quicker than turning all the way around
	    	if(Math.abs(angle - curAngle) > 90 && Math.abs(angle - curAngle) < 270 && angle != 0) {
	    		angle = (angle +180)%360;
	    		speed = -speed;
	    	}
	    	
	    	//
	    	if(Math.abs(angle - curAngle) > 180) {
    			angle -= 360;
    		}
	    	
	    	//Makes it so when you stop moving it doesn't reset the angle to 0, but leaves it where it was
		    if(speed == 0) {
	    		setAngle(lastAngle);
		    	setSpeed(speed);
	    	} else {
	    		setAngle(angle);
	    		setSpeed(speed);
	    		
	    		lastAngle = angle;
	    	}
	    	
		}
		
		//Sets the module to a specific angle
		public void setAngle(double angle) {
			pidCont.enable();
			pidCont.setSetpoint(angle);
		}
		
		//Sets the drive motor's speed
		public void setSpeed(double speed) {
			driveMot.set(speed);
		}
		
		//Sets the Swivel Motor's speed
		public void setSwivel(double speed) {
			swivelMot.set(speed);
		}

		//Returns where the Encoder is
		public double getEncPercent() {
			return enc.getDistance() / ENCODER_TICKS_FOR_ADJUSTER_TRAVEL;
		}
		
		//Gets the current angle of the module
		public double getAngle() {
			if (enc != null) {
				return (getEncPercent());
			} else {
				return -1.0;
			}
		}
		
		//Sets a motor to brake mode
		public void setBrake(boolean bool) {
			driveMot.enableBrakeMode(bool);
		}
		
	}
	
	//Returns the cosine of an angle in radians
	public double cosDeg(double deg) {
		return Math.cos(Math.toRadians(deg));
	}
	
	//Returns the sine of an angle in radians
	public double sinDeg(double deg) {
		return Math.sin(Math.toRadians(deg));
	}
	
    public SwerveDriveBase() {
    	super();
    	
    	//Makes sure navX is on Robot, then instantiates it 
    	try {
	         navSensor = new AHRS(SPI.Port.kMXP);
	     } catch (RuntimeException ex ) {
	         DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
	     }
    	
    	
    	//Creates the front right Swerve Module
    	flMod = new SwerveModule(
    					new VictorSP(RobotMap.FRONT_RIGHT_SWIVEL),
    					new CANTalon(RobotMap.FRONT_RIGHT_WHEEL),
    					new Encoder(new DigitalInput(2), 
    								new DigitalInput(3),
    								false,
    								EncodingType.k4X)
    				);
    	
    	//Creates the front left Swerve Module
    	rlMod = new SwerveModule(
    					new VictorSP(RobotMap.FRONT_LEFT_SWIVEL),
    					new CANTalon(RobotMap.FRONT_LEFT_WHEEL),
    					new Encoder(new DigitalInput(0), 
    								new DigitalInput(1),
    								false,
    								EncodingType.k4X)
    				);
    	
    	//Creates the rear right Swerve Module
    	frMod = new SwerveModule(
    					new VictorSP(RobotMap.REAR_RIGHT_SWIVEL),
    					new CANTalon(RobotMap.REAR_RIGHT_WHEEL),
    					new Encoder(new DigitalInput(6), 
    								new DigitalInput(7),
    								false,
    								EncodingType.k4X)
    				);
    			
    	//Creates the rear left Swerve Module
    	rrMod = new SwerveModule(
    					new VictorSP(RobotMap.REAR_LEFT_SWIVEL),
    					new CANTalon(RobotMap.REAR_LEFT_WHEEL),
    					new Encoder(new DigitalInput(4), 
    								new DigitalInput(5),
    								false,
    								EncodingType.k4X)
    				); // ):
    	
    }

    //Initiates SwerveDrive as the Default Command
    public void initDefaultCommand() {
        setDefaultCommand(new SwerveDrive());
    }
    
    //Small, simple tank drive method
    public void tankDrive(double leftValue, double rightValue) {
    	if (DriverStation.getInstance().isFMSAttached() && DriverStation.getInstance().getMatchTime() < 4) {
    		setRobotBrake(true);
    	} else {
    		setRobotBrake(true);
    	}
    	
    	frMod.setSpeed(rightValue);
    	rrMod.setSpeed(rightValue);
    	
    	flMod.setSpeed(leftValue);
    	rlMod.setSpeed(leftValue);
    }
    
    //Method for calculating and setting Speed and Angle of individual wheels given 3 movement inputs
    public void swerveDrive(double fbMot, double rlMot, double rotMot, boolean fieldOriented) {
    	//Swerve Math Taken from: https://www.chiefdelphi.com/media/papers/2426
    	
    	if(fieldOriented) {
	    	double curAngle = getGyroAngle(true);
	    	double temp = fbMot*(cosDeg(curAngle)) + rlMot*(sinDeg(curAngle));
	    	rlMot = fbMot*(sinDeg(curAngle)) + -(rlMot*(cosDeg(curAngle)));
	    	fbMot = temp;
    	}
    	
    	double L = 1.0;
    	double W = 1.0;
    	double R = Math.sqrt((L*L) + (W*W));
    	
    	double A = rlMot - rotMot*(L/R);
    	double B = rlMot + rotMot*(L/R);
    	double C = fbMot - rotMot*(W/R);
    	double D = fbMot + rotMot*(W/R);
    	
    	double frSpd = Math.sqrt((B*B) + (D*D));
    	double flSpd = Math.sqrt((B*B) + (C*C));
    	double rlSpd = Math.sqrt((A*A) + (C*C));
    	double rrSpd = Math.sqrt((A*A) + (D*D));
    	
    	double t = 180/Math.PI;
    	
    	double frAng = Math.atan2(B, D)*t;
    	double flAng = Math.atan2(B, C)*t;
    	double rlAng = Math.atan2(A, C)*t;
    	double rrAng = Math.atan2(A, D)*t;
    	 
    	double max = frSpd;
    	if(max < flSpd) max = flSpd;
    	if(max < rlSpd) max = rlSpd;
    	if(max < rrSpd) max = rrSpd;
    	//I'm so sorry Jake
    	
    	//Father Jacobs forgives you
    	
    	if(max > 1) {
    		frSpd /= max;
    		flSpd /= max;
    		rlSpd /= max;
    		rrSpd /= max;
    	}
    	
    	//Set Wheel Speeds and Angles
    	frMod.setModule(frAng, frSpd);
    	flMod.setModule(flAng, flSpd);
    	rrMod.setModule(rrAng, rrSpd);
    	rlMod.setModule(rlAng, rlSpd);
    }
    
    //Returns navX sensor ?
    public AHRS getNavSensor() {
    	if (navSensor != null) {
    		return navSensor;
    	} else {
    		return null;
    	}
    }
    
    //Returns the Gyro Angle
    public double getGyroAngle(boolean reversed) {
    	if(reversed) {
    		return ((getNavSensor().getAngle()+180.0)%360) - angleOffset;
    	} else
    		return (getNavSensor().getAngle()) - angleOffset;
    }
    
    public void resetGyroNorth(double angle, double north) {
    	getNavSensor().reset();
    	angleOffset = angle - north;
    }
    
    //Sets all module's angles to 0
    public void setZero() {
    	frMod.lastAngle = 0;
    	flMod.lastAngle = 0;
    	rrMod.lastAngle = 0;
    	rlMod.lastAngle = 0;
    	
    	frMod.setAngle(0);
    	flMod.setAngle(0);
    	rrMod.setAngle(0);
    	rlMod.setAngle(0);
    }
    
    //Sets every module on the robot to brake
    public void setRobotBrake(boolean bool) {
    	frMod.setBrake(bool);
    	flMod.setBrake(bool);
    	rrMod.setBrake(bool);
    	rlMod.setBrake(bool);
    }
    
}

