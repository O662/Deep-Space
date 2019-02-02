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
public class MecanumDrive {

    //not a command

    double deadzone = .1;
    
    public MecanumDrive(){
        Robot.driveTrain.setBrake(true);
			
	    Robot.driveTrain.setDefaltRampRate();

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

    // Called repeatedly when this Command is scheduled to run
    
    public void execute() {
    double rf,rb,lf,lb;
    double y = Robot.oi.getJoystick().getRawAxis(5);
    double x = Robot.oi.getJoystick().getRawAxis(0);
    double z = Robot.oi.getJoystick().getRawAxis(4);
    double forward = map(y);
    double right = map (x);
    double clockwise = map(z);
//	double K = .1;//the value that determines sensitivity of turning tweek to edit
//	clockwise = K*clockwise;
    //inverse kinimatics
    
    rf = forward + clockwise + right;
    lf = forward - clockwise - right;
    lb = forward + clockwise - right;
    rb = forward - clockwise + right;



    boolean skidSteerDrive = false;
    
    //the driving force that drives the mecanum drive ...im tired
    Robot.driveTrain.driveMotors(rf, rb, lf, lb, skidSteerDrive);
    DriverStation.reportWarning("you are in the Mecanum drive now", true);
    }
}
