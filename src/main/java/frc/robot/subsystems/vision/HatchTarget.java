/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.vision;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotPreferences;

/**
 * Add your docs here.
 */
public class HatchTarget implements IVisionTarget{
	
	public HatchTarget() {
		
	}

	@Override
	public double getDistance() {
		SmartDashboard.putNumber("HatchDistance Lst Call", Timer.getFPGATimestamp());
		//return (Constants.kHatchTargetHeight*Constants.kPixelHeight) / (2*(Limelight.getInstance().getTa()/.15)*Math.tan(Constants.kVerticalFOV)); //Old an innaccurate way
		return -12 - RobotPreferences.kCameraDistanceFromFront - //Distance offset to make distance relative to robot front
			  (RobotPreferences.kCameraHeight + 4.5 - (RobotPreferences.kHatchTargetBottomToHatchCenter + RobotPreferences.kFloorToLowHatchCenter)) / //Y component of the triangle
			  (Math.tan(Math.toRadians(RobotPreferences.kCameraAngle + getHeightAngle()))); //Angle of the triangle
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
