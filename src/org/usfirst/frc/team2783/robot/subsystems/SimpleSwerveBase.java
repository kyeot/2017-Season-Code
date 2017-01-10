package org.usfirst.frc.team2783.robot.subsystems;

import org.usfirst.frc.team2783.robot.RobotMap;
import org.usfirst.frc.team2783.robot.commands.AlignToGoal;

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
public class SimpleSwerveBase extends Subsystem {
	
	private SwerveModule frMod;
	private SwerveModule flMod;
	private SwerveModule rrMod;
	private SwerveModule rlMod;
	
	private AHRS navSensor;
	
	private final double p = 0.05;
	private final double i = 0.0025;
	private final double d = 0.005;
	
	private static final double ENCODER_TICKS_FOR_ADJUSTER_TRAVEL = 1;
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
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
	
	public SimpleSwerveBase() {
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
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new AlignToGoal());
    }
    
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

