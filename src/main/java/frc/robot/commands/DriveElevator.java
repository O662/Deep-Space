/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class DriveElevator extends Command {
  double axis;
  boolean goodPlace;
  double deadzone =.05;
  public DriveElevator() {
    goodPlace = false;
    requires(Robot.elevator);
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
    
    if(Robot.elevator.getTranslateHeight() >= 44 && Robot.hatchPlacer.getPusher() ==false && Robot.hatchPlacer.isSwitchClosed() == false){
      goodPlace = false;
      Robot.elevator.moveElevator(0);
      Robot.hatchPlacer.togglePusher();
      goodPlace = true;
    }

    else if(Robot.elevator.getTranslateHeight() < 44){
      goodPlace = false;
       axis = Robot.oi.getJoystick2().getRawAxis(5);
       axis = map(axis);
       Robot.elevator.moveElevator(axis);
       //Robot.elevator.moveElevator(s);
    }
    else if(Robot.elevator.getTranslateHeight() >= 44 && Robot.hatchPlacer.getPusher() == true && Robot.hatchPlacer.getPusher()){
      goodPlace = true;
      axis = Robot.oi.getJoystick2().getRawAxis(5);
      axis = map(axis);
       Robot.elevator.moveElevator(axis);
    }
    
    
    
    //System.out.println("Moving @ "+s);
  }

  public double map(double input) {
    //  return value==0 ? 0 : value*value*(Math.abs(value)/value);
    if (Math.abs(input) < deadzone) {
      return 0;
    }
    if (input>0) {
      return (input - deadzone)/(1 - deadzone);
    } else if(input<0) {
      return (input + deadzone)/(1 - deadzone);
    } else {
      return 0;
    }
      }
   
  

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    //if(axis == 0 && Robot.elevator.getTranslateHeight() < 44){
    //  return true;
    //}
    if(axis == 0 ){//&& goodPlace == true
      return true;
    }
    //if(Robot.elevator.getTranslateHeight() >= 55.5){
    //  return true;
      
  //  }
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
