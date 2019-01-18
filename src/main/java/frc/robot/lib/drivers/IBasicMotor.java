/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.lib.drivers;

/**
 * Add your docs here.
 */
public interface IBasicMotor {	
	
	public abstract void driveRaw(double speed);
	public abstract void driveRaw(double speed, boolean reverseInput);
	public abstract boolean getBrakeMode();
	public abstract void setBrakeMode(boolean on);
	public abstract void invertMotor(boolean inverted);
}
