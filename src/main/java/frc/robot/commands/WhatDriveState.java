/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.DriveTrain.DriveState;

public class WhatDriveState extends Command {

  //todo rename and organize the drive mode and drive state names
  public WhatDriveState(DriveState ds) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.driveTrain);
    state = ds;

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  DriveState state;

  public void choose() {
    switch(state){
      case SKIDSTEER:
        new SwichDriveState(true);
        break;
      case MECANUM:
        new SwichDriveState(false);
        break;
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    choose();
    SmartDashboard.putString("DriveState", "" + Robot.driveTrain.getDriveState());
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
