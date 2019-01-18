package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.lib.drivers.DriveIntent;
import frc.robot.lib.drivers.IAdvancedMotor;
import frc.robot.lib.drivers.ILooper;
import frc.robot.lib.drivers.IAdvancedMotor.EncoderType;
import frc.robot.lib.utilitys.ChoosableSolenoid;
import frc.robot.lib.utilitys.ChoosableSolenoid.SolenoidState;
import frc.robot.subsystems.DrivetrainState.DriveModeState;
import frc.robot.subsystems.DrivetrainState.DrivetrainWheelState;
import frc.robot.lib.drivers.Loop;

public class DriveTrain extends Subsystem implements LoggableSubsystem {

	private static DriveTrain mInstance = new DriveTrain();

	// Hardware
	private final IAdvancedMotor mFrontRight, mFrontLeft, mBackRight, mBackLeft;
	private final ChoosableSolenoid mShifterRight, mShifterLeft;

	// Control States
	private DriveModeState mDriveModeState;
	
	//Hardware States
	private DrivetrainWheelState mDrivetrainWheelState;
	private boolean mBrakeMode;
    private PeriodicIO mPeriodicIO;
	
	private final Loop mLoop = new Loop() {
        @Override
        public void onStart(double timestamp) {
            synchronized (DriveTrain.this) {
                setOpenLoop(new DriveIntent(0, 0, 0, 0, DriveModeState.TANK, true));
                setBrakeMode(true);
                //startLogging();
            }
        }

       // @Override
        public void onLoop(double timestamp) {
            synchronized (DriveTrain.this) {
                switch (mDriveModeState) {
                    case MECANUM_ROBOT_RELATIVE:
                        break;
                    case MECANUM_FIELD_RELATIVE:
                    	break;
                    case TANK:
                        break;
                    case ARCADE:
                    	break;
                    default:
                        System.out.println("Unexpected drive mode state: " + mDriveModeState);
                        break;
                }
            }
        }

       // @Override
        public void onStop(double timestamp) {
            stop();
            //stopLogging();
        }
    };
    
    public static DriveTrain getInstance() {
        return mInstance;
    }
	
	
	public DriveTrain() {
		//Create Motors
		if(RobotMap.kUsingTalonSRXs) {
			mFrontRight = new TalonSRXMotor(RobotMap.kFrontRightCANId, RobotMap.kFrontRightPDPId, EncoderType.SRX);
			mFrontLeft = new TalonSRXMotor(RobotMap.kFrontLeftCANId, RobotMap.kFrontLeftPDPId, EncoderType.SRX);
			mBackRight = new TalonSRXMotor(RobotMap.kBackRightCANId, RobotMap.kBackRightPDPId, EncoderType.SRX);
			mBackLeft = new TalonSRXMotor(RobotMap.kBackLeftCANId, RobotMap.kBackLeftPDPId, EncoderType.SRX);
		} else {
			//TODO Code for Neo's
		}
		mFrontRight.invertMotor(true);
		mBackRight.invertMotor(true);
		
		//Create shifters
		if(RobotMap.kUsingSpringSolenoids) {
			mShifterRight = new ChoosableSolenoid(RobotMap.kShifterModule, RobotMap.kShifterRightSkidId);
			mShifterLeft = new ChoosableSolenoid(RobotMap.kShifterModule, RobotMap.kShifterLeftSkidId);
		} else {
			mShifterRight = new ChoosableSolenoid(RobotMap.kShifterModule, RobotMap.kShifterRightMecId, RobotMap.kShifterRightSkidId);
			mShifterLeft = new ChoosableSolenoid(RobotMap.kShifterModule, RobotMap.kShifterLeftMecId, RobotMap.kShifterLeftSkidId);
		}
		
		//Reset everything and stop
		setBrakeMode(true);
		setWheelState(DrivetrainWheelState.MECANUM);
		setOpenLoop(DriveIntent.MECANUM_BRAKE);
		
		//TODO add sensor zero's
		
	}
	
	public void setOpenLoop(DriveIntent driveIntent) {		
		//Handle wheel state detection/correction
		mDriveModeState = driveIntent.getDriveModeState();
		switch(mDrivetrainWheelState) {
			case MECANUM:
				break;
			case SKID_STEER:
				if(!(mDriveModeState == DriveModeState.ARCADE || mDriveModeState == DriveModeState.TANK)) {
					DriverStation.reportWarning("Requested drive mode only available for Mecanum. "+((RobotMap.kFixIncompatableDriveMode) ? "Wheel state correction is enabled, resorting to Mecanum." : "Wheel state correction is disabled, terminating drive command."), false);
					if(RobotMap.kFixIncompatableDriveMode) {
						setWheelState(DrivetrainWheelState.MECANUM);
					} else {
						return;
					}
				}
				break;
			default:
				DriverStation.reportError("Unknown wheel state ("+mDrivetrainWheelState+"), stopping drive command to protect vehicle integrity.", false);
				return;
		}
		//Drive motors
		setBrakeMode(driveIntent.getBrakeMode());
		

		mPeriodicIO.front_right_intent = driveIntent.getFrontRight();
		mPeriodicIO.front_left_intent = driveIntent.getFrontLeft();
		mPeriodicIO.back_right_intent = driveIntent.getBackRight();
		mPeriodicIO.back_left_intent = driveIntent.getBackLeft();
		
		mFrontRight.driveRaw(driveIntent.getFrontRight());
		mFrontLeft.driveRaw(driveIntent.getFrontLeft());
		mBackRight.driveRaw(driveIntent.getBackRight());
		mBackLeft.driveRaw(driveIntent.getBackLeft());
		
	}

	public synchronized void setBrakeMode(boolean on) {
		if(mBrakeMode != on) {
			mBrakeMode = on;
			mFrontRight.setBrakeMode(mBrakeMode);
			mFrontLeft.setBrakeMode(mBrakeMode);
			mBackRight.setBrakeMode(mBrakeMode);
			mBackLeft.setBrakeMode(mBrakeMode);
		}
	}
	
	public synchronized void setWheelState(DrivetrainWheelState newState) {
		if(mDrivetrainWheelState != newState) {
			mDrivetrainWheelState = newState;
			switch(mDrivetrainWheelState) {
				case MECANUM:
					mShifterRight.setState(SolenoidState.FORCED_REVERSE);
					mShifterLeft.setState(SolenoidState.FORCED_REVERSE);
					break;
				case SKID_STEER:
					mShifterRight.setState(SolenoidState.FORCED_FORWARD);
					mShifterLeft.setState(SolenoidState.FORCED_FORWARD);
					break;
				default:
					DriverStation.reportError("Drivetrain has been requested to shift into an unknown state: "+mDrivetrainWheelState+". Reverting to Mecanum drivetrain for safety.", false);
					setWheelState(DrivetrainWheelState.MECANUM);
			}
			
		}
	}
	
	public synchronized DriveModeState getDriveModeState() {
		return mDriveModeState;
	}
	
	public synchronized DrivetrainWheelState getDrivetrainWheelState() {
		return mDrivetrainWheelState;
	}
	
	//@Override
    public void registerEnabledLoops(ILooper in) {
        in.register(mLoop);
    }
	

//	@Override
	public boolean checkSystem() {
		// TODO Auto-generated method stub
		return false;
	}


	//@Override
	public void outputTelemetry() {
		SmartDashboard.putString("Drive Mode State", ""+mDriveModeState);
		SmartDashboard.putString("Drivetrain Wheel State", ""+mDrivetrainWheelState);

		SmartDashboard.putNumber("FRP", mPeriodicIO.front_right_position_ticks);
		SmartDashboard.putNumber("FLP", mPeriodicIO.front_left_position_ticks);
		SmartDashboard.putNumber("BRP", mPeriodicIO.back_right_position_ticks);
		SmartDashboard.putNumber("BLP", mPeriodicIO.back_left_position_ticks);
		
		SmartDashboard.putNumber("FRV", mPeriodicIO.front_right_velocity_ticks_per_100ms);
		SmartDashboard.putNumber("FLV", mPeriodicIO.front_left_velocity_ticks_per_100ms);
		SmartDashboard.putNumber("BRV", mPeriodicIO.back_right_velocity_ticks_per_100ms);
		SmartDashboard.putNumber("BLV", mPeriodicIO.back_left_velocity_ticks_per_100ms);
		
		SmartDashboard.putNumber("FRD", mPeriodicIO.front_right_distance);
		SmartDashboard.putNumber("FLD", mPeriodicIO.front_left_distance);
		SmartDashboard.putNumber("BRD", mPeriodicIO.back_right_distance);
		SmartDashboard.putNumber("BLD", mPeriodicIO.back_left_distance);

		SmartDashboard.putNumber("FRI", mPeriodicIO.front_right_intent);
		SmartDashboard.putNumber("FLI", mPeriodicIO.front_left_intent);
		SmartDashboard.putNumber("BRI", mPeriodicIO.back_right_intent);
		SmartDashboard.putNumber("BLI", mPeriodicIO.back_left_intent);
		
	}


	//@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
//	@Override
	public void zeroSensors() {
		mFrontRight.zeroEncoder();
		mFrontLeft.zeroEncoder();
		mBackRight.zeroEncoder();
		mBackLeft.zeroEncoder();
	}
	
	

  //  @Override
    public synchronized void readPeriodicInputs() {
        double prevFrontRightTicks = mPeriodicIO.front_right_position_ticks;
        double prevFrontLeftTicks = mPeriodicIO.front_left_position_ticks;
        double prevBackRightTicks = mPeriodicIO.back_right_position_ticks;
        double prevBackLeftTicks = mPeriodicIO.back_left_position_ticks;
        
        mPeriodicIO.front_right_position_ticks = mFrontRight.getEncoderPosition();
        mPeriodicIO.front_left_position_ticks = mFrontLeft.getEncoderPosition();
        mPeriodicIO.back_right_position_ticks = mBackRight.getEncoderPosition();
        mPeriodicIO.back_left_position_ticks = mBackLeft.getEncoderPosition();

        mPeriodicIO.front_right_velocity_ticks_per_100ms = mFrontRight.getEncoderVelocity();
        mPeriodicIO.front_left_velocity_ticks_per_100ms = mFrontLeft.getEncoderVelocity();
        mPeriodicIO.back_right_velocity_ticks_per_100ms = mBackRight.getEncoderVelocity();
        mPeriodicIO.back_left_velocity_ticks_per_100ms = mBackLeft.getEncoderVelocity();
        
        //mPeriodicIO.gyro_heading = Rotation2d.fromDegrees(mPigeon.getFusedHeading()).rotateBy(mGyroOffset);

        double deltaFrontRightTicks = ((mPeriodicIO.front_right_position_ticks - prevFrontRightTicks) / 4096.0) * Math.PI;
        mPeriodicIO.front_right_distance += deltaFrontRightTicks * RobotMap.kDrivetrainWheelDiameterInches;
        double deltaFrontLeftTicks = ((mPeriodicIO.front_left_position_ticks - prevFrontLeftTicks) / 4096.0) * Math.PI;
        mPeriodicIO.front_left_distance += deltaFrontLeftTicks * RobotMap.kDrivetrainWheelDiameterInches;
        double deltaBackRightTicks = ((mPeriodicIO.back_right_position_ticks - prevBackRightTicks) / 4096.0) * Math.PI;
        mPeriodicIO.back_right_distance += deltaBackRightTicks * RobotMap.kDrivetrainWheelDiameterInches;
        double deltaBackLeftTicks = ((mPeriodicIO.back_left_position_ticks - prevBackLeftTicks) / 4096.0) * Math.PI;
        mPeriodicIO.back_left_distance += deltaBackLeftTicks * RobotMap.kDrivetrainWheelDiameterInches;
    }
	

    public static class PeriodicIO {
        // INPUTS
        public int front_right_position_ticks;
        public int front_left_position_ticks;
        public int back_right_position_ticks;
        public int back_left_position_ticks;
        public double front_right_distance;
        public double front_left_distance;
        public double back_right_distance;
        public double back_left_distance;
        public int front_right_velocity_ticks_per_100ms;
        public int front_left_velocity_ticks_per_100ms;
        public int back_right_velocity_ticks_per_100ms;
        public int back_left_velocity_ticks_per_100ms;
        
        public double front_right_intent;
        public double front_left_intent;
        public double back_right_intent;
        public double back_left_intent;
        //public Rotation2d gyro_heading = Rotation2d.identity();

        // OUTPUTS
        /*
        public double left_demand;
        public double right_demand;
        public double left_accel;
        public double right_accel;
        public double left_feedforward;
        public double right_feedforward;
        */
    }

	@Override
	public void log() {

	}

	@Override
	protected void initDefaultCommand() {

	}
}


/*
	private void calculateWheels() {
		if( (DrivetrainState.driveModeState == DrivetrainState.DriveModeState.MECANUM_FIELD_RELATIVE ||
			 DrivetrainState.driveModeState == DrivetrainState.DriveModeState.MECANUM_ROBOT_RELATIVE) &&
			DrivetrainState.drivetrainWheelState == DrivetrainState.DrivetrainWheelState.SKID_STEER 		) {
			
		}
		switch(mDS) {
			case MECANUM_ROBOT_RELATIVE: 
				calculateRobotRelative();
				break;
			case MECANUM_FIELD_RELATIVE:
				calculateFieldRelative();
				break;
			case TANK:
				calculateTank();
				break;
			case ARCADE:
				calculateArcade();
				break;
		}
	}
*/
