package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.MecanumDriveTrain;


public class DriveTrain extends Subsystem {
	
	//creates motors
	public final WPI_TalonSRX  motorLeft1;
	private final WPI_TalonSRX  motorLeft2;
	private final WPI_TalonSRX motorRight1, motorRight2;
	//public final MecanumDrive drive;
	private int direction = RobotMap.DRIVE_TRAIN_FORWARD_DIRECTION;
	//initilizes the motors
	public DriveTrain() {

		super();
		motorLeft1 = new WPI_TalonSRX(RobotMap.MOTOR_DRIVE_LEFT1);
		motorLeft2 = new WPI_TalonSRX(RobotMap.MOTOR_DRIVE_LEFT2);
		motorRight1 = new WPI_TalonSRX(RobotMap.MOTOR_DRIVE_RIGHT1);
		motorRight2 = new WPI_TalonSRX(RobotMap.MOTOR_DRIVE_RIGHT2);
		//mecanum
		SpeedControllerGroup left1 = new SpeedControllerGroup(motorLeft1);//left front
		SpeedControllerGroup left2 = new SpeedControllerGroup(motorLeft2);//left back
		SpeedControllerGroup right1 = new SpeedControllerGroup(motorRight1);//right front
		SpeedControllerGroup right2 = new SpeedControllerGroup(motorRight2);//right back

		//TankDrive and arcade drive
		SpeedControllerGroup leftGroup = new SpeedControllerGroup(motorLeft1,motorLeft2);//left front
		SpeedControllerGroup rightGroup = new SpeedControllerGroup(motorRight1,motorRight2);//left back
		
		//mecanum speed command math
		double rf,rb,lf,lb;
		double forward = -Robot.oi.getJoystick().getY();
		double right = Robot.oi.getJoystick().getX();
		double clockwise = Robot.oi.getJoystick().getZ();
		double K = .01;//the value that determines sensitivity of turning tweek to edit
		clockwise = K*clockwise;
		//inverse kinimatics
		rf = forward + clockwise + right;
		lf = forward - clockwise - right;
		lb = forward + clockwise - right;
		rb = forward - clockwise + right;
		/*
		left1.set(lf);
		left2.set(lb);
		right1.set(rf);
		right2.set(rb);
		*/
	
		
		//drive = new MecanumDrive(left1,left2,right1,right2);

		//TankDrive
		//drive = new DifferentialDrive(leftGroup,rightGroup);



	}
	
		
	
	//sets the break mode for each motor
	public void setBrake(boolean brake) {
		// Formerly: frontLeft.enableBrakeMode(brake);
		// See https://github.com/CrossTheRoadElec/Phoenix-Documentation#installing-phoenix-framework-onto-your-frc-robot
		NeutralMode mode = brake ? NeutralMode.Brake : NeutralMode.Coast;

		//motorLeft1.setNeutralMode(mode);
		motorLeft1.setNeutralMode(mode);
		motorLeft2.setNeutralMode(mode);
		//motorRight1.setNeutralMode(mode);
		motorRight1.setNeutralMode(mode);
		motorRight2.setNeutralMode(mode);
	}

	public void setDirection(int direction) {
		this.direction = direction * RobotMap.DRIVE_TRAIN_FORWARD_DIRECTION;
	}
	
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new MecanumDriveTrain());
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
