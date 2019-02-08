/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class MoveElevator extends Command {
 
  double speed;
  double height0,height1,height2,height3,height; // the heights for the elevator to reach
  
  public MoveElevator(double speed) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.elevator);
    speed = this.speed;
    height0 = 0;
    height1 = 1;
    height2 = 2;
    height3 = 3;
   
   
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(Robot.elevator.height == height0) {
       Robot.elevator.setElevatorHeight(height1);
    }
    
    if(Robot.elevator.height == height1){
       Robot.elevator.setElevatorHeight(height2);
    }
   
    if(Robot.elevator.height == height2){
      Robot.elevator.setElevatorHeight(height3);
    }

    if(Robot.elevator.height == height3){
      Robot.elevator.setElevatorHeight(height0);
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
