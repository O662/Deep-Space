/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.vision;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.BattleAxe;
import frc.robot.commands.Delay;
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
    addSequential(new BattleAxe());
    // Timer.delay runs immediately, but we need the delay to happen when the commandgroup
    // is running.  So, use a delay command instead...
    addSequential(new Delay(2));
    //Timer.delay(2);
    addSequential(new Pusher(true));
    addSequential(new Delay(4));
    //Timer.delay(4);
    addSequential(new BattleAxe());
    addSequential(new Delay(2));
    //Timer.delay(3);
    // addSequential(new DriveForward(12));
    addSequential(new Pusher(false));
    
    
  }
}
