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
public interface IDriveControlBoard {
	/* Things the driver controls */
	
	
	public double getDriveForward();

	public double getDriveForward2();
	
	public double getDriveSideway();
	
	public double getDriveRotation();
	
	public boolean getToggleDriveMode();
	
	public boolean getToggleWheelState();
	
	public boolean getForceSkidSteer();
	
	public boolean getForceMecanum();
	
	public boolean getToggleBrake();
	
	public boolean getUseAssist();
}
