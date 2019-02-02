/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class TankDrive {

    //not a command

    double deadzone = .1;

    public TankDrive(){
        Robot.driveTrain.setBrake(true);
        Robot.driveTrain.setDefaltRampRate();
        
    }

    public double map(double input) {
        //return value==0 ? 0 : value*value*(Math.abs(value)/value);
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
      // Called repeatedly when this Command is scheduled to run
      
      public void execute() {
       double fr= Robot.oi.getJoystick().getRawAxis(5);
       double fl = Robot.oi.getJoystick().getRawAxis(1);
       double forwardRight = map(fr);
       double forwardLeft = map(fl);
       Boolean skidSteerDrive = true;
       Robot.driveTrain.driveMotors(forwardRight, forwardRight, forwardLeft, forwardLeft, skidSteerDrive);
       DriverStation.reportWarning("you are in the tank drive now", true);
      }
}
