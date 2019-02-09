package frc.robot.subsystems.vision;

import frc.robot.subsystems.DriveTrain.DriveTrainMode;

public class PathIntent {
	private double mFrontRightWheel, mFrontLeftWheel, mBackRightWheel, mBackLeftWheel;
	
	private boolean mBrakeMode;
	private DriveTrainMode mDMS;
	
	
	
	public PathIntent(double frontRightWheel, double frontLeftWheel, double backRightWheel, double backLeftWheel, DriveTrainMode ds, boolean brakeMode) {
		mFrontRightWheel = frontRightWheel;
		mFrontLeftWheel = frontLeftWheel;
		mBackRightWheel = backRightWheel;
		mBackLeftWheel = backLeftWheel;
		mDMS = ds;
		mBrakeMode = brakeMode;
	}
	
	public void setDriveModeState(DriveTrainMode mode) {
		mDMS = mode;
	}
	
	public double getFrontRightRoations() {
		return mFrontRightWheel;
	}
	
	public double getFrontLeftRoations() {
		return mFrontLeftWheel;
	}
	
	public double getBackRightRoations() {
		return mBackRightWheel;
	}
	
	public double getBackLeftRoations() {
		return mBackLeftWheel;
	}
	
	public boolean getBrakeMode() {
        return mBrakeMode;
    }
	
	public DriveTrainMode getDriveModeState() {
		return mDMS;
	}
	
	@Override
    public String toString() {
        return "FR: "+mFrontRightWheel+", FL: "+mFrontLeftWheel+", BR: "+mBackRightWheel+", BL: "+mBackLeftWheel+", Brake Enabled: "+mBrakeMode+", DriveState: "+mDMS;
    }
	
}
