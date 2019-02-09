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

import frc.robot.subsystems.DriveTrain.DriveState;
import frc.robot.subsystems.DriveTrain.DriveTrainMode;

public class SwichDriveState extends Command {

  private final boolean open;

  public SwichDriveState(boolean open) {
    requires(Robot.driveTrain);
    this.open = open;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  //false = mecanum
  //true = skidSteer
  @Override
  protected void initialize() {
  Robot.driveTrain.switchState(open);
    if(open){
      if(Robot.driveTrain.getDriveTrain() == DriveTrainMode.ROBOT_ORIANTED_MECANUM){
        Robot.driveTrain.switchState(false);
       // SmartDashboard.putBoolean("mecanum", false);
      }
      else if (Robot.driveTrain.getDriveTrain() == DriveTrainMode.FEILD_ORIANTED_MECANUM){
        Robot.driveTrain.switchState(false);
      }
      /*
      else if(Robot.driveTrain.getDriveState() == DriveModeState.FEILD_ORIANTED_MECANUM){
        Robot.driveTrain.switchState(false);
      }
      */
      
      else{
         Robot.driveTrain.switchState(true);
        // SmartDashboard.putBoolean("skidSteer", true );
      }
  
    }
     else{
      Robot.driveTrain.switchState(false);
      //SmartDashboard.putBoolean("mecanum", false);
    }
     
     
     
    }
  

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
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
