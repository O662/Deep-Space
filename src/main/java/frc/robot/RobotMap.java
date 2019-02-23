/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	public static final int DRIVE_TRAIN_FORWARD_DIRECTION = 1;
	public static final int MOTOR_DRIVE_LEFT1 = 14;// front left
	public static final int MOTOR_DRIVE_LEFT2 = 13;//back left
	public static final int MOTOR_DRIVE_RIGHT1 = 11;//front right
	public static final int MOTOR_DRIVE_RIGHT2 = 12;//back right
	public static final int ARM_MOTOR = 31;
	public static final int ARM_MOTOR_2 = 32;
	public static final int ROLLER_MOTOR = 23;
	public static final int INTAKE_ROLLER_MOTOR = 33;
	public static final int ELEVATOR_MOTOR_1 = 21;
	public static final int ELEVATOR_MOTOR_2 = 22;
	

	//i cry

	//ENCODER
	//public static final int ARM_ENCODER_A = 0;
    //public static final int ARM_ENCODER_B = 1;
	//public static final int HEIGHT_A = 2;
	//public static final int HEIGHT_B = 3;

	//LIMIT SWITCH
	public static final Port LAZER_LIMIT = I2C.Port.kOnboard;
	
	public static final int LIMIT_SWITCH = 0;

	//PHNEMATICS OR HOWEVER ITS SPELLED
	public static final int BUTTERFLY_PCM_MODULE1 = 1;
//	public static final int BUTTERFLY_PCM_MODULE2 = 1;
//	public static final int BUTTERFLY_PCM_MODULE3 = 2;
//	public static final int BUTTERFLY_PCM_MODULE4 = 3;
	public static final int BUTTERFLY_FORWARD_CHANNEL1 = 0;
	public static final int BUTTERFLY_FORWARD_CHANNEL2 = 1;
	public static final int PUSHER_CHANNEL = 2;
	public static final int BATTLE_AXE_CHANNEL = 5;
	public static final int LOCK_CHANNEL = 4;

}
