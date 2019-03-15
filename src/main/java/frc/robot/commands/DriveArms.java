/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveArms extends Command {
  double axis;
  double axisDown;
  double axisRoller;
  public DriveArms() {
    requires(Robot.rearIntake);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    axis = Robot.oi.getJoystick().getRawAxis(3);
    axisDown = Robot.oi.getJoystick().getRawAxis(2);
    if(axisDown <.2){
      Robot.rearIntake.moveArm(axis);
    }
    if(axis <.2){
      Robot.rearIntake.moveArm(-axisDown);
    }
    axisRoller = Robot.oi.getJoystick2().getRawAxis(1);
    Robot.rearIntake.moveIntakeRollerSpeed(axisRoller);
   

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(axis < .2 && axisDown <.2 && axisRoller < .2){
      return true;
    }
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
