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
public abstract class Motor implements IBasicMotor{
	
	public int mCANId, mPDPId;
	protected boolean mBrakeMode;
	
	public Motor(int CANId) {
		this(CANId, -1);
	}
	
	public Motor(int CANId, int PDPId) {
		mCANId = CANId;
		mPDPId = PDPId;
	}
	
	public int getCANId() {
		return mCANId;
	}
	
	@Override
	public boolean getBrakeMode() {
		return mBrakeMode;
	}
}
