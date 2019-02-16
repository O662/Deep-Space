/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotPreferences;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.DriveTrain.DriveState;

public class DriveForward extends Command {

  DriveState state = Robot.driveTrain.getDriveState();
  double rf,lf,lb,rb;
  double dis;
  public DriveForward(double distnace) {
    requires(Robot.driveTrain);
    dis = distnace;//inches
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(state == DriveState.MECANUM){
      double clockwise = 0;
      double right = 0;
      double forward = dis/ RobotPreferences.kDrivetrainWheelCircumference * 4096 * RobotPreferences.kRatioToOutput;
      rf = forward + clockwise + right;
      lf = forward - clockwise - right;
      lb = forward + clockwise - right;
      rb = forward - clockwise + right;


      Robot.driveTrain.driveMotorsVision(rf, rb, lf, lb, false);

    }
    else if(state == DriveState.SKIDSTEER){
      double distnace = dis/ RobotPreferences.kDrivetrainWheelCircumference * 4096 * RobotPreferences.kRatioToOutput;
      
      Robot.driveTrain.driveMotorsVision(distnace, distnace, distnace, distnace, true);

    }
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
