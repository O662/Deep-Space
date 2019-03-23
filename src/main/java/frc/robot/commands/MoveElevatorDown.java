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
import frc.robot.subsystems.Elevator.ElevatorPosition;

public class MoveElevatorDown extends Command {

  double speed;
  double height0,height1,height2,height3,height4;
  ElevatorPosition currentHeight; // the heights for the elevator to reach

  public MoveElevatorDown() {
    requires(Robot.elevator);
    speed = this.speed;
    //heights in inches
   
    currentHeight = Robot.elevator.getElevatorHeight();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

    if(currentHeight == ElevatorPosition.TALL_HATCH) {
      Robot.elevator.setElevatorHeight(RobotPreferences.MiddleHatch);
      Robot.elevator.setElevatorPosition(ElevatorPosition.MEDIUM_HATCH);
   }

   else if(currentHeight == ElevatorPosition.MEDIUM_HATCH){
      Robot.elevator.setElevatorHeight(RobotPreferences.cargoCargo);
      Robot.elevator.setElevatorPosition(ElevatorPosition.LOWEST_HATCH);
   }
   else if(currentHeight == ElevatorPosition.LOWEST_HATCH){
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
