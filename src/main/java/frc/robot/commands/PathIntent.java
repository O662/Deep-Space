package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrain.DriveTrainMode;

public class PathIntent extends Command{
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
		requires(Robot.driveTrain);
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
	
	public DriveTrainMode getDriveTrainMode() {
		return mDMS;
	}

	public void Drivetotarget(){
		Robot.driveTrain.driveMotors(getFrontLeftRoations(), getBackRightRoations(), getFrontLeftRoations(), getBackLeftRoations(), false);
	}
	
	@Override
    public String toString() {
        return "FR: "+mFrontRightWheel+", FL: "+mFrontLeftWheel+", BR: "+mBackRightWheel+", BL: "+mBackLeftWheel+", Brake Enabled: "+mBrakeMode+", DriveState: "+mDMS;
    }

	@Override
	protected boolean isFinished() {
		return false;
	}
	
}
