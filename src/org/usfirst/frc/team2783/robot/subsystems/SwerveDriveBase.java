package org.usfirst.frc.team2783.robot.subsystems;

import org.usfirst.frc.team2783.robot.RobotMap;
import org.usfirst.frc.team2783.robot.commands.SwerveDrive;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

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
	
	private SwerveModule frMod;
	private SwerveModule flMod;
	private SwerveModule rrMod;
	private SwerveModule rlMod;
	
	private AHRS navSensor;
	
	private final double p = 0.05;    //0.015;
	private final double i = 0.0025;    //0.005;
	private final double d = 0.005;    //0.125;
	
	final private double ENCODER_TICKS_FOR_ADJUSTER_TRAVEL = 1;
	
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
	
	public class SwerveModule {
		
		VictorSP swivelMot;
		CANTalon driveMot;
		Encoder enc;
		PIDController pidCont;
		PIDOutputClass pidOut;
		
		public SwerveModule(
				VictorSP swivelMot,
				CANTalon driveMot,
				Encoder enc) {
			
			this.swivelMot = swivelMot;
			this.driveMot = driveMot;
			this.enc = enc;
			
			pidOut = new PIDOutputClass(
							swivelMot
						);
			
			pidCont = new PIDController(
							p, i, d,
							enc,
							pidOut
						);
			
			
			
			pidCont.setInputRange(0, 360);
			pidCont.setContinuous();
			
			enc.setDistancePerPulse(0.875);
			enc.setSamplesToAverage(127);
		}
		
		public void setModule(double angle, double speed) {
			
			if(angle < 0) {
				angle += 360;
			}
			
			double curAngle = getAngle();
	    	if(Math.abs(angle - curAngle) > 90 && Math.abs(angle - curAngle) < 270 && angle != 0) {
	    		angle = ((int)angle + 180)%360;
	    		speed = -speed;
	    	}
	    	
			System.out.println(curAngle);
	    	setAngle(angle);
	    	setSpeed(speed*0.3);
		}
		
		public void setAngle(double angle) {
			pidCont.enable();
			pidCont.setSetpoint(angle);
		}

		public void setSpeed(double speed) {
			driveMot.set(speed);
		}
		
		public void setSwivel(double speed) {
			swivelMot.set(speed);
		}

		public double getEncPercent() {
			return Math.abs(enc.getDistance() / ENCODER_TICKS_FOR_ADJUSTER_TRAVEL);
		}
		
		public double getAngle() {
			if (enc != null) {
				return (getEncPercent());
			} else {
				return -1.0;
			}
		}
		
		public void setBrake(boolean bool) {
			driveMot.enableBrakeMode(bool);
		}
		
	}
	
	public double cosDeg(double deg) {
		return Math.cos(Math.toRadians(deg));
	}
	
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
    	
    	frMod = new SwerveModule(
    					new VictorSP(RobotMap.FRONT_RIGHT_SWIVEL),
    					new CANTalon(RobotMap.FRONT_RIGHT_WHEEL),
    					new Encoder(new DigitalInput(2), 
    								new DigitalInput(3))
    				);
    	
    	flMod = new SwerveModule(
    					new VictorSP(RobotMap.FRONT_LEFT_SWIVEL),
    					new CANTalon(RobotMap.FRONT_LEFT_WHEEL),
    					new Encoder(new DigitalInput(0), 
    								new DigitalInput(1))
    				);
    	
    	rrMod = new SwerveModule(
    					new VictorSP(RobotMap.REAR_RIGHT_SWIVEL),
    					new CANTalon(RobotMap.REAR_RIGHT_WHEEL),
    					new Encoder(new DigitalInput(6), 
    								new DigitalInput(7))
    				);
    			
    	rlMod = new SwerveModule(
    					new VictorSP(RobotMap.REAR_LEFT_SWIVEL),
    					new CANTalon(RobotMap.REAR_LEFT_WHEEL),
    					new Encoder(new DigitalInput(4), 
    								new DigitalInput(5))
    				); // ):
    	
    }

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
    public void swerveDrive(double fbMot, double rlMot, double rotMot) {
    	//Swerve Math Taken from: https://www.chiefdelphi.com/media/papers/2426
    	
    	double curAngle = getNavSensor().getAngle();
    	double temp = fbMot*(cosDeg(curAngle)) + rlMot*(sinDeg(curAngle));
    	rlMot = fbMot*(sinDeg(curAngle)) + -(rlMot*(cosDeg(curAngle)));
    	fbMot = temp;
    	
    	double L = 1.0;
    	double W = 1.0;
    	double R = Math.sqrt((L*L) + (W*W));
    	
    	double A = rlMot - rotMot*(L/R);
    	double B = rlMot + rotMot*(L/R);
    	double C = fbMot - rotMot*(W/R);
    	double D = fbMot + rotMot*(W/R);
    	
    	double frSpd = Math.sqrt((B*B) + (C*C));
    	double flSpd = Math.sqrt((B*B) + (D*D));
    	double rlSpd = Math.sqrt((A*A) + (D*D));
    	double rrSpd = Math.sqrt((A*A) + (C*C));
    	
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
    
    public void setRobotBrake(boolean bool) {
    	frMod.setBrake(bool);
    	flMod.setBrake(bool);
    	rrMod.setBrake(bool);
    	rlMod.setBrake(bool);
    }
    
}
