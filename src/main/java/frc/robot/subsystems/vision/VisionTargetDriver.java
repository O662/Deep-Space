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
import frc.robot.subsystems.DriveTrain.DriveTrainMode;
import frc.robot.subsystems.vision.Limelight.Target;

/**
 * Add your docs here.
 */

public class VisionTargetDriver {
    private Limelight mLimelight = Limelight.getInstance();
    //private ButterflyDriveHelper mButterflyDriveHelper = new ButterflyDriveHelper();

    private double lastGoodS = 0;
    private double lastGoodSTime = 0;

    public VisionTargetDriver(){

    }

    /*
    public DriveIntent pureVisionDriveRaw(Target target){
        if(mLimelight.getTargetSelected()!=target) {
            mLimelight.setPipeline(target);
        }/*
        if(mLimelight.isTarget()){
            return DriveIntent.MECANUM_BRAKE;
        }*/
        /*
        if(false)return oldWay();
        else return smartPath();
    }

  */

   
    public PathIntent pureVisionDriveControl(Target target) {
        if(mLimelight.getTargetSelected()!=target) {
            mLimelight.setPipeline(target);
        }
        return directVectorTravel();
    }

    private PathIntent directVectorTravel() {
        //Grab values
        double scew = mLimelight.getSidewaysAngle();
        if(scew!=0&&scew!=-90) {
            lastGoodS = (lastGoodS<-45 ? 90-scew : -scew);
            lastGoodSTime = Timer.getFPGATimestamp();  
        }
        if(Timer.getFPGATimestamp()-lastGoodSTime>5) {
            lastGoodS = 0;
        }

        double angle = mLimelight.getSidewaysAngle();
        double distanceInches = mLimelight.getDistance()/10;

        //Run distance calculations
        double rotationInches = distanceInches*Math.PI*angle/180;
        double sidewaysInches = distanceInches * Math.tan(lastGoodS) * (lastGoodS<-45 ? 1 : -1);

        //rot = angle/360   x   wheelWidth x pi / circumference
        //Calculate wheel rotations
        double turningRotations = (angle * RobotPreferences.kMecanumWheelWidth) / (360 * RobotPreferences.kDrivetrainWheelDiameterInches);
        double sideRotations = sidewaysInches / (RobotPreferences.kDrivetrainWheelDiameterInches * Math.PI);
        double forwardRotations = distanceInches / (RobotPreferences.kDrivetrainWheelDiameterInches * Math.PI);

        
		double mFR = (forwardRotations + sideRotations + turningRotations);
		double mFL = (forwardRotations - sideRotations - turningRotations);
		double mBR = (forwardRotations - sideRotations + turningRotations);
		double mBL = (forwardRotations + sideRotations - turningRotations);

        

        return new PathIntent(mFR, mFL, mBR, mBL, DriveTrainMode.ROBOT_ORIANTED_MECANUM, true);
    }
}