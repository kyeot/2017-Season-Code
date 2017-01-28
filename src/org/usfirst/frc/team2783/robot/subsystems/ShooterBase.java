package org.usfirst.frc.team2783.robot.subsystems;

import org.usfirst.frc.team2783.robot.Robot;
import org.usfirst.frc.team2783.robot.RobotMap;
import org.usfirst.frc.team2783.robot.commands.ShooterDrive;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterBase extends Subsystem {

	private VictorSP shooterWheelMotor1 = new VictorSP(RobotMap.SHOOTER_WHEEL_1_ID);
	
	private VictorSP shooterWheelMotor2 = new VictorSP(RobotMap.SHOOTER_WHEEL_2_ID);
	
	private VictorSP gathererMotor = new VictorSP(RobotMap.GATHERER_WHEEL_ID);
	
	private Servo gearShifter = new Servo(RobotMap.GEAR_SHIFTER_ID);
	
	public void setShooterSpeedVbus(double vbusOutput) {
		shooterWheelMotor1.set(vbusOutput);
		shooterWheelMotor2.set(vbusOutput);
		
	}
	
	public void setGathererSpeedVbus(double vbusOutput) {
		gathererMotor.set(vbusOutput);
		
	}
	
	public void shiftGear(double Angle){
		gearShifter.setAngle(Angle);
		
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	setDefaultCommand(new ShooterDrive());
    	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

