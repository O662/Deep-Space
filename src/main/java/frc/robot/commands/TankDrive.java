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

public class TankDrive extends Command {
  public TankDrive() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }
  public double map(double value) {
    return value==0 ? 0 : value*value*(Math.abs(value)/value);
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
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
