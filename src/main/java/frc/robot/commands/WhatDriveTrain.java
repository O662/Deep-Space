/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.commands.drive.ArcadeDrive;
import frc.robot.commands.drive.FeildRelative;
import frc.robot.commands.drive.MecanumDrive;
import frc.robot.commands.drive.TankDrive;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.DriveTrain.DriveTrainMode;
import frc.robot.subsystems.DriveTrain.DriveTrainMode;

public class WhatDriveTrain extends CommandGroup {

  //houston we have a problem
  public static DriveTrainMode s;
  public String mode;
  private final ArcadeDrive arcade;
  private final MecanumDrive mecanum;
  private final TankDrive tank;
  private final FeildRelative feild;

public WhatDriveTrain(DriveTrainMode dms) {
    
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    
    requires(Robot.driveTrain);
    state = dms;
     arcade = new ArcadeDrive();
     mecanum = new MecanumDrive();
     tank = new TankDrive();
     feild = new FeildRelative();
   // s = dms;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  DriveTrainMode state;

  public void choose() {
    switch(Robot.driveTrain.getDriveTrain()){
      case ARCADE:
      // addSequential(new ArcadeDrive());;
        arcade.execute();
        DriverStation.reportWarning("arcadedrive", false);
        mode = "arcade";
        break;
      case TANK:
        //addSequential(new TankDrive());
        tank.execute();
        DriverStation.reportWarning("tankDrive", false);
        mode = "Tank";
        break;
     // case FEILD_ORIANTED_MECANUM:
      //  break;
      case ROBOT_ORIANTED_MECANUM:
        //addSequential(new MecanumDriveTrain());
        
        mecanum.execute();;
        DriverStation.reportWarning("mecanum", false);
        mode = "mecanum";
        break;
      case FEILD_ORIANTED_MECANUM:
        feild.execute();
        mode = "feild";
        break;
        default:
         System.out.println("Unexpected drive mode state: " + state);
         break;
    }
  }


  


  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //not the problem
    choose();
   // SmartDashboard.putString("DriveMode", "" + Robot.driveTrain.getDriveState());
    SmartDashboard.putString("DriveMode", "" + mode);
   // SmartDashboard.putNumber("motor", Robot.driveTrain.motorLeft1.get());
    SmartDashboard.putString("motor",""+ 1.1);
    DriverStation.reportWarning("we are here man" , true);
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
