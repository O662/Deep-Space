/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.vision;

import frc.robot.RobotPreferences;

/**
 * Add your docs here.
 */
public class HatchTarget implements IVisionTarget{
	
	public HatchTarget() {
		
	}

	@Override
	public double getDistance() {
		//return (Constants.kHatchTargetHeight*Constants.kPixelHeight) / (2*(Limelight.getInstance().getTa()/.15)*Math.tan(Constants.kVerticalFOV)); //Old an innaccurate way
		return RobotPreferences.kCameraDistanceFromFront + //Distance offset to make distance relative to robot front
			  (RobotPreferences.kCameraHeight -	(RobotPreferences.kHatchTargetBottomToHatchCenter + RobotPreferences.kFloorToLowHatchCenter)) / //Y component of the triangle
			  (Math.tan((RobotPreferences.kCameraAngle - getHeightAngle()) * Math.PI / 180)); //Angle of the triangle
	}

	@Override
	public double getHeightAngle() {
		return Limelight.getInstance().getTy();
	}

	@Override
	public double getOffsetAngle() {
		return Limelight.getInstance().getTs();
	}

	@Override
	public double getSidewaysAngle() {
		return Limelight.getInstance().getTx();
	}

	@Override
	public IVisionTarget getTargetType() {
		return this;
	}


}
