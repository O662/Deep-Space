/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class MoveElevatorDown extends Command {

  double speed;
  double height0,height1,height2,height3,height4,currentHeight; // the heights for the elevator to reach

  public MoveElevatorDown() {
    requires(Robot.elevator);
    speed = this.speed;
    //heights in inches
    height0 = 19;//bottom
    height1 = 27.5;
    height2 = 43.5;
    height3 = 47;
    height4 = 55.5;
    currentHeight = Robot.elevator.currentHeight;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

    if(currentHeight == height4) {
      Robot.elevator.setElevatorHeight(height3);
   }

   else if(currentHeight == height3){
      Robot.elevator.setElevatorHeight(height2);
   }
  
   else if(currentHeight == height2){
     Robot.elevator.setElevatorHeight(height1);
   }

   else if(currentHeight == height1){
     Robot.elevator.setElevatorHeight(height0);
     Robot.elevator.isSwitchClosed();
   }
   else if(currentHeight == height4){
     System.out.println("you are at the bottom");
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
