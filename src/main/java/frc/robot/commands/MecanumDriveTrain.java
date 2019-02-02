/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrain;

//created by alyssa cassity 1/18/2019

public class MecanumDriveTrain extends Command {
	

	
	
	public MecanumDriveTrain() {
		requires(Robot.driveTrain);

		

	}
	
	// Called just before this Command runs the first time
		@Override
		protected void initialize() {
			Robot.driveTrain.setBrake(false);
			
			//Robot.driveTrain.setDefaltRampRate();
		}

		// Called repeatedly when this Command is scheduled to run
		@Override
		protected void execute() {
		double rf,rb,lf,lb;
		double forward = -Robot.oi.getJoystick().getRawAxis(5);
		double right = Robot.oi.getJoystick().getRawAxis(4);
		double clockwise = Robot.oi.getJoystick().getRawAxis(0);
		double K = .01;//the value that determines sensitivity of turning tweek to edit
		clockwise = K*clockwise;
		//inverse kinimatics
		rf = forward + clockwise + right;
		lf = forward - clockwise - right;
		lb = forward + clockwise - right;
		rb = forward - clockwise + right;
		boolean skidSteerDrive = false;
		
		//the driving force that drives the mecanum drive ...im tired
		Robot.driveTrain.driveMotors(rf, rb, lf, lb, skidSteerDrive);
		DriverStation.reportWarning("you are in the Mecanum drive now", true);
		}

		
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}