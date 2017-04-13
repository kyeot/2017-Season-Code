package org.usfirst.frc.team2783.robot.subsystems;

import org.usfirst.frc.team2783.robot.RobotMap;
import org.usfirst.frc.team2783.robot.commands.GearRoller;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearRollerBase extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private VictorSP gearRoller;
	
	public GearRollerBase(){
		gearRoller = new VictorSP(RobotMap.GEAR_LIFTER_ROLLER_ID);
	}
	
	public void rollRoller(double vbusOutput){
		gearRoller.set(vbusOutput);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    	setDefaultCommand(new GearRoller());
    	
    }
}

