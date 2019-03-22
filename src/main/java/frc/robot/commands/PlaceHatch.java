/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;

public class PlaceHatch extends CommandGroup {
  /**
   * Add your docs here.
   */
  public PlaceHatch() {
    requires(Robot.hatchPlacer);
    

    //add thing for limit switch to tell what state it is in and then do it


    addSequential(new setFlapper(Value.kReverse));//open
    addSequential(new setPusher(Value.kForward));//forward
    addSequential(new setPusher(Value.kReverse));//back
  
  }
}
