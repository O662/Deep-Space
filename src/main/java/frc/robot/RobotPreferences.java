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

    //vision
    public static final double kCameraDistanceFromFront = 6; 
    public static final double kCameraHeight = 36; //TODO Correct
    public static final double kCameraAngle = -18; 
    public static final double kHatchTargetBottomToHatchCenter = 6.5;
    public static final double kFloorToLowHatchCenter = 19;
    public static final double kMecanumWheelWidth = 2.25;
    public static final double kDrivetrainWheelDiameterInches = 4.0;

}
