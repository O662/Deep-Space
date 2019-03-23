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
import frc.robot.RobotPreferences;
import frc.robot.subsystems.Elevator.ElevatorPosition;

public class MoveElevatorUp extends Command {
 //44 inches
 
 
  double height0,height1,height2,height3,height4;
 ElevatorPosition currentHeight; // the heights for the elevator to reach
  
  public MoveElevatorUp() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.elevator);
   
    //heights in inches
   
    currentHeight = Robot.elevator.getElevatorHeight();
    
    
   
   
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {



    if(currentHeight == ElevatorPosition.LOWEST_HATCH) {
       Robot.elevator.setElevatorHeight(RobotPreferences.LowestCargo);
       Robot.elevator.setElevatorPosition(ElevatorPosition.MEDIUM_HATCH);
       
    }
    else if(currentHeight == ElevatorPosition.MEDIUM_HATCH){
      Robot.elevator.setElevatorHeight(RobotPreferences.MiddleCargo);
      Robot.elevator.setElevatorPosition(ElevatorPosition.TALL_HATCH);
    }
    else if(currentHeight == ElevatorPosition.TALL_HATCH){
      System.out.println("you are at the top");
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
