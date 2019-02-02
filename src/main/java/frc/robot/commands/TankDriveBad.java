/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TankDriveBad extends Command {
  double deadzone = .1;
  public TankDriveBad() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.driveTrain.setBrake(true);
    Robot.driveTrain.setDefaltRampRate();
  }
  public double map(double input) {
    //return value==0 ? 0 : value*value*(Math.abs(value)/value);
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
   double fr= Robot.oi.getJoystick().getRawAxis(5);
   double fl = Robot.oi.getJoystick().getRawAxis(1);
   double forwardRight = map(fr);
   double forwardLeft = map(fl);
   Boolean skidSteerDrive = true;
   Robot.driveTrain.driveMotors(forwardRight, forwardRight, forwardLeft, forwardLeft, skidSteerDrive);
   DriverStation.reportWarning("you are in the tank drive now", true);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

 // Called once after isFinished returns true
 @Override
 protected void end() {
   Robot.driveTrain.driveMotors(0, 0, 0, 0, true);
 }

 // Called when another command which requires one or more of the same
 // subsystems is scheduled to run
 @Override
 protected void interrupted() {
   end();
 }
}
