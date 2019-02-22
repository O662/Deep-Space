/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
public class RobotPreferences {

    //where constats for the robot are placed

    public static final double kDistancePerRevolution = 1;//in inches
    public static final double kRatioToOutput = 60/18;
    public static final double kChassisHeightOffset = 4.5; //in inches
    public static final double kDrivetrainWheelCircumference = 4.0 * Math.PI;

    //vision
    public static final double kCameraDistanceFromFront = 6; 
    public static final double kCameraHeight = 36; //TODO Correct
    public static final double kCameraAngle = -18; 
    public static final double kHatchTargetBottomToHatchCenter = 6.5;
    public static final double kFloorToLowHatchCenter = 19;
    public static final double kMecanumWheelWidth = 2.25;
    public static final double kDrivetrainWheelDiameterInches = 4.0;
    public static final double kMecanumWheelbaseWidthInches = 21.596300;	//Sidenote: length runs parallel to the Inner and Outer Side Chassis pieces
	public static final double kMecanumWheelbaseLengthInches = 14.000000;	//Note: All dimension taken from CAD model, when that drivetrain is deployed

    public static final double kMecanumWheelbaseDiameter = Math.sqrt(Math.pow(kMecanumWheelbaseWidthInches, 2) + Math.pow(kMecanumWheelbaseLengthInches, 2));
    public static final double KMecanumWheelbaseRadius = kMecanumWheelbaseDiameter/2;

    public static final double LowestHatch = 19;//bottom
    public static final double LowestCargo = 27.5;
    public static final double cargoCargo = 40;
    public static final double MiddleHatch = 47;
    public static final double MiddleCargo = 55.5;
}
