/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.vision;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.DriveForward;
import frc.robot.commands.Pusher;

public class PlaceHatch extends CommandGroup {
  /**
   * Add your docs here.
   */
  public PlaceHatch() {
    //have it like line up with the vison target
   // addSequential(new TurnToTarget());
   // addSequential(new VisionDriveRobot());
    addSequential(new Pusher(true));
    addSequential(new DriveForward(12));
    addSequential(new Pusher(false));
    
  }
}
