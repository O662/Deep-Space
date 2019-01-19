/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ArcadeDrive extends Command {
  public ArcadeDrive() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }
  public double map(double value) {
    return value==0 ? 0 : value*value*(Math.abs(value)/value);
	}
  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double y= Robot.oi.getJoystick().getY();
    double z= Robot.oi.getJoystick().getZ();
    double forward = map(y);
    double turn = map(z);
    double fr = forward + turn;
    double fl = forward - turn;
    boolean skidSteerDrive = true;
    double largest = Math.abs(fl)>Math.abs(fr) ? Math.abs(fl): Math.abs(fr);
			if(largest>1) {
				fr/=largest;
				fl/=largest;
			}
    Robot.driveTrain.driveMotors(fr, fl, fr, fl, skidSteerDrive);


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
