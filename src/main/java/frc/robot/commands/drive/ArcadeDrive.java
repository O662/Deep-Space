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
public class ArcadeDrive {

    //not a command

    public ArcadeDrive(){
        Robot.driveTrain.setBrake(true);
        Robot.driveTrain.setDefaltRampRate();
    }

    double deadzone = .1;

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
        // Called repeatedly when this Command is scheduled to run
       
        public void execute() {
          double y= Robot.oi.getJoystick().getRawAxis(5);
          double z= Robot.oi.getJoystick().getRawAxis(4);
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
          Robot.driveTrain.driveMotors(fr, fr, fl, fl, skidSteerDrive);
          DriverStation.reportWarning("you are in the arcade drive now", true);
      
        }
}
