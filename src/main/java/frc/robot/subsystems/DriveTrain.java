package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.MecanumDriveTrain;
import frc.robot.commands.WhatDriveTrain;


public class DriveTrain extends Subsystem {
	
	public enum DriveModeState{
		ARCADE,
		TANK,
		FEILD_ORIANTED_MECANUM,
		ROBOT_ORIANTED_MECANUM;

		private static DriveModeState[] vals = values();
		public DriveModeState next() {
			return vals[(this.ordinal()+1) % vals.length];
		}
		/*
		public DriveModeState last() {
			return vals[(this.ordinal()-1) % vals.length];
		}*/
	}

	public enum DriveState{
		MECANUM,
		SKIDSTEER;

		private static DriveState[] vals = values();
		public DriveState next() {
			return vals[(this.ordinal()+1) % vals.length];
		}
		/*
		public DriveModeState last() {
			return vals[(this.ordinal()-1) % vals.length];
		}*/
	}
	

	// todo find out about encoders for spark motor contorler
	//creates motors
	public final CANSparkMax  motorLeft1;
	private final CANSparkMax   motorLeft2;
	private final CANSparkMax  motorRight1, motorRight2;
	private final Solenoid solenoid1,solenoid2;
	//public final MecanumDrive drive;
	private int direction = RobotMap.DRIVE_TRAIN_FORWARD_DIRECTION;
	private static DriveModeState mDriveState;

	public static DriveModeState getDriveState() {
		return mDriveState;
	}

	public static void setDriveState(DriveModeState dms) {
		mDriveState = dms;
	}


//mecanum and skid steer
	private static DriveState mDriveType;

	public static DriveState getDriveType() {
		return mDriveType;
	}


	

	public static void setDriveType(DriveState ds) {
		mDriveType = ds;
	}



	
	//initilizes the motors
	public DriveTrain() {

		super();
		motorLeft1 = new CANSparkMax (RobotMap.MOTOR_DRIVE_LEFT1, MotorType.kBrushless);
		motorLeft2 = new CANSparkMax (RobotMap.MOTOR_DRIVE_LEFT2, MotorType.kBrushless);
		motorRight1 = new CANSparkMax (RobotMap.MOTOR_DRIVE_RIGHT1, MotorType.kBrushless);
		motorRight2 = new CANSparkMax (RobotMap.MOTOR_DRIVE_RIGHT2, MotorType.kBrushless);
		solenoid1 = new Solenoid(RobotMap.BUTTERFLY_PCM_MODULE1, RobotMap.BUTTERFLY_FORWARD_CHANNEL1);
		solenoid2 = new Solenoid(RobotMap.BUTTERFLY_PCM_MODULE2, RobotMap.BUTTERFLY_FORWARD_CHANNEL2);
		
		//mecanum
		SpeedControllerGroup left1 = new SpeedControllerGroup(motorLeft1);//left front
		SpeedControllerGroup left2 = new SpeedControllerGroup(motorLeft2);//left back
		SpeedControllerGroup right1 = new SpeedControllerGroup(motorRight1);//right front
		SpeedControllerGroup right2 = new SpeedControllerGroup(motorRight2);//right back

		//TankDrive and arcade drive
		SpeedControllerGroup leftGroup = new SpeedControllerGroup(motorLeft1,motorLeft2);//left front
		SpeedControllerGroup rightGroup = new SpeedControllerGroup(motorRight1,motorRight2);//left back
		
		mDriveState = DriveModeState.ROBOT_ORIANTED_MECANUM;

		//motorLeft1.setSelectedSensorPosition(0, 0, 0);



	}
	
		
	
	//sets the break mode for each motor
	public void setBrake(boolean brake) {
		// Formerly: frontLeft.enableBrakeMode(brake);
		// See https://github.com/CrossTheRoadElec/Phoenix-Documentation#installing-phoenix-framework-onto-your-frc-robot
		IdleMode mode = brake ? IdleMode.kBrake : IdleMode.kCoast;

		//motorLeft1.setNeutralMode(mode);
		motorLeft1.setIdleMode(mode);
		motorLeft2.setIdleMode(mode);
		//motorRight1.setNeutralMode(mode);
		motorRight1.setIdleMode(mode);
		motorRight2.setIdleMode(mode);
	}


	// i know its messy but i didnt want to think of a way to compact it
	// i will fix it later but im lazy and dont want to right now

	public double getMotorLeft1Temp(){
		double temp;
		temp = motorLeft1.getMotorTemperature();
		return temp;
	}

	public double getMotorLeft2Temp(){
		double temp;
		temp = motorLeft2.getMotorTemperature();
		return temp;
	}

	public double getMotorRight1Temp(){
		double temp;
		temp = motorRight1.getMotorTemperature();
		return temp;
	}

	public double getMotorRight2Temp(){
		double temp;
		temp = motorRight2.getMotorTemperature();
		return temp;
	}

	public void setDirection(int direction) {
		this.direction = direction * RobotMap.DRIVE_TRAIN_FORWARD_DIRECTION;
	}
	
	/**
	 * 
	 * @param state True = Skid Steer False = Mecanum
	 */
	public void switchState(boolean state){
		solenoid1.set(state);
		solenoid2.set(state);
	}

	public Boolean getState(){
		Boolean s = solenoid1.get();
		return s;
	}
	/*
	public void toggleDrive(){
		if(getState()){
			switchState(false);
		}
		else if(getState() == false){
			switchState(true);
		}
	}
	

*/

	

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new WhatDriveTrain(mDriveState));
		// TODO Auto-generated method stub

	}
	/**
	 * 
	 * @param rf    the right front
	 * @param rb	the right back
	 * @param lf	the left front
	 * @param lb	the left back
	 * @param skidSteerDrive true = in skid steer  false = not in skid steer
	 */
	//what is being seen by the mecanumDriveTrain class 
	public void driveMotors(double rf, double rb, double lf, double lb, boolean skidSteerDrive) {
		if(skidSteerDrive) {
			if(!(Math.abs(lf-lb)<=.01)){
				lb=lf;
			}
			if(!(Math.abs(rf-rb)<=.01)){
				rb=rf;
			}
		}
		motorLeft1.set(lf);
		motorLeft2.set(lb);
		motorRight1.set(rf);
		motorRight2.set(rb);


	}

	/*
	//what is driving the robot
	public void driveMecanum(Joystick joystick) {
		
		drive.driveCartesian(Robot.oi.stick.getX(), Robot.oi.stick.getY(), Robot.oi.stick.getZ(), 0);
		
		Timer.delay(0.01);
		
		
	}
	*/

}
