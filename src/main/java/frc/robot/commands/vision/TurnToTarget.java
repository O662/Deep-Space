/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.vision;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotPreferences;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.vision.Limelight;

public class TurnToTarget extends Command {
  private Limelight mLimelight = Limelight.getInstance();
  double turn;
  double rf,rb,lf,lb;
  public TurnToTarget() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    reguire(Robot.driveTrain);
  }

  private void reguire(DriveTrain driveTrain) {
  }

// Called just before this Command runs the first time
  @Override
  protected void initialize() {
  
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    turn = RobotPreferences.KMecanumWheelbaseRadius * Robot.limelight.getTx();//getSidewaysAngle();
    turn = turn*RobotPreferences.kDistancePerRevolution* 4096* RobotPreferences.kRatioToOutput;
    rf = turn;
    rb = turn;
    lf = -turn;
    lb = -turn;
    turn = .5;
    double forwardRotations = 0;
    double sideRotations = 0;
    double turningRotations =turn;
    rf = (forwardRotations + sideRotations + turningRotations);
    lf = (forwardRotations - sideRotations - turningRotations);
    rb = (forwardRotations - sideRotations + turningRotations);
    lb = (forwardRotations + sideRotations - turningRotations);

    
      if(Robot.limelight.getTx() > .05 && Robot.limelight.getTx() < -.05 ){
           Robot.driveTrain.driveMotors(rf, rb, lf, lb, false);
      }
      //else{
      //  Robot.driveTrain.driveMotors(0, 0, 0, 0, false);

      

    //}

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(Robot.limelight.getTx()<.2 || Robot.limelight.getTx() > -.2){
      return true;
    } 
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
