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

public class ToggleDriveMode extends Command {
  public ToggleDriveMode() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

  DriveTrainMode dtm = Robot.driveTrain.getDriveTrain().next();
  if(Robot.driveTrain.getDriveState2()){
      if(dtm == DriveTrainMode.ROBOT_ORIANTED_MECANUM){
        dtm = dtm.next();
      }
  }
  
    
   Robot.driveTrain.setDriveTrain(dtm);

  
   SmartDashboard.putString("DriveTraininToggle" ,""+ Robot.driveTrain.getDriveTrain());

   /*

    if(Robot.driveTrain.getDriveTrain() == DriveTrainMode.ROBOT_ORIANTED_MECANUM){
      Robot.driveTrain.setDriveTrain(DriveTrainMode.ARCADE);
    }
    else if(Robot.driveTrain.getDriveTrain() == DriveTrainMode.ARCADE){
      Robot.driveTrain.setDriveTrain(DriveTrainMode.TANK);
    }
    else if(Robot.driveTrain.getDriveTrain() == DriveTrainMode.TANK){
      Robot.driveTrain.setDriveTrain(DriveTrainMode.ROBOT_ORIANTED_MECANUM);
    }
    */
     // SmartDashboard.putString("DriveTraininToggle" ,""+ Robot.driveTrain.getDriveTrain());

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
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
