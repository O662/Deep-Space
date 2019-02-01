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
import frc.robot.subsystems.DriveTrain.DriveTrainMode;
import frc.robot.subsystems.DriveTrain.DriveTrainMode;

public class WhatDriveTrain extends Command {

  //houston we have a problem
  public static DriveTrainMode s;

public WhatDriveTrain(DriveTrainMode dms) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    
    requires(Robot.driveTrain);
    state = dms;
   // s = dms;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  DriveTrainMode state;

  public void choose() {
    switch(state){
      case ARCADE:
        new ArcadeDrive();
        break;
      case TANK:
        new TankDrive();
        break;
     // case FEILD_ORIANTED_MECANUM:
      //  break;
      case ROBOT_ORIANTED_MECANUM:
        new MecanumDriveTrain();
        break;
        default:
         System.out.println("Unexpected drive mode state: " + state);
         break;
    }
  }

  


  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    choose();
    SmartDashboard.putString("DriveMode", "" + Robot.driveTrain.getDriveState());
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
