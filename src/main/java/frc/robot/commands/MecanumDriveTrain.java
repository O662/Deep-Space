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
	

	double deadzone = .1;
	
	public MecanumDriveTrain() {
		requires(Robot.driveTrain);

		

	}
	
	// Called just before this Command runs the first time
		@Override
		protected void initialize() {
			Robot.driveTrain.setBrake(true);
			
			Robot.driveTrain.setDefaltRampRate();
			
		}
		public double map(double input) {
			//  return value==0 ? 0 : value*value*(Math.abs(value)/value);
			if (Math.abs(input) < deadzone) {
			  return 0;
			}
			if (input>0) {
			  return (input - deadzone)/(1 - deadzone);
			} else if(input<0) {
			  return (input + deadzone)/(1 - deadzone);
			} else {
			  return 0;
			}
			  }

		// Called repeatedly when this Command is scheduled to run
		@Override
		protected void execute() {
		double rf,rb,lf,lb;
		double y = Robot.oi.getJoystick().getRawAxis(5);
		double x = Robot.oi.getJoystick().getRawAxis(4);
		double z = Robot.oi.getJoystick().getRawAxis(0);
		double forward = map(y);
		double right = map (x);
		double clockwise = map(z);
	//	double K = .1;//the value that determines sensitivity of turning tweek to edit
	//	clockwise = K*clockwise;
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

	// Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.driveTrain.driveMotors(0, 0, 0, 0, false);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }

}