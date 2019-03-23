/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;

public class PlaceHatch extends CommandGroup {
  /**
   * Add your docs here.
   */
  public PlaceHatch() {
    requires(Robot.hatchPlacer);
    

    if(Robot.hatchPlacer.isHatchPresent() == true){
      addSequential(new setFlapper(false));//close
      addSequential(new setPusher(true));//forward
      Timer.delay(2);
      addSequential(new setPusher(false));//back

    }
    else{
      addSequential(new setPusher(true));
      addSequential(new setFlapper(true));
      addSequential(new setPusher(false));
      if(Robot.hatchPlacer.isHatchPresent() == false){
        addSequential(new setFlapper(false));
      }
    }



    
  
  }
}
