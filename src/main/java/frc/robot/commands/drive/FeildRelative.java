/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class FeildRelative {
    double deadzone = .1;

    public FeildRelative(){
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

    public void execute(){
        
        
        double y = Robot.oi.getJoystick().getRawAxis(5);
        double z = Robot.oi.getJoystick().getRawAxis(0);
        double x = Robot.oi.getJoystick().getRawAxis(4);
        y= map(y);
        x = map(x);
        z = map(z);
        double h= Robot.navX.getYaw() ;//yaw from navx

        //TODO MAKE FEILD RELATIVE
		double mFR, mFL, mBR, mBL;
		//SmartDashboard.putNumber("Recieved Heading", h);

		//double costheta = Math.abs(h);
		//double sintheta = (Math.abs(h)>90);
		double h1 = Math.abs(h);//= h>=0?h:180-h;
		double h2 = (180-h)*Math.abs(h)/h;

		double hcorrect = h<0?360+h:h;
		h1=hcorrect;
		h2=hcorrect;
		

		double fo = Math.cos(Math.toRadians(h1));//*(Math.abs(h)>90?-1:1);
		double si = -Math.sin(Math.toRadians(h2));//*(h>0?-1:1);
		SmartDashboard.putNumber("\"Cos\"", fo);
		SmartDashboard.putNumber("\"Sin\"", si);

		double forward = fo*y+si*x;
		double sideway = fo*x-si*y;
		SmartDashboard.putNumber("\"Forward\"", forward);
		SmartDashboard.putNumber("\"Sideway\"", sideway);

		//if(h<0)forward*=-1;
		//if(Math.abs(h)>=45&&Math.abs(h)<=135) sideway*=-1;
		//sideway*=-1;
		sideway*=1.5;

		mFR = forward + sideway + z;
		mFL = forward - sideway - z;
		mBR = forward- sideway + z;
		mBL = forward + sideway - z;

		double largest = Math.abs(mFR)>Math.abs(mFL) ? Math.abs(mFR) : Math.abs(mFL);
		largest = largest>Math.abs(mBR) ? largest : Math.abs(mBR);
		largest = largest>Math.abs(mBL) ? largest : Math.abs(mBL);
		SmartDashboard.putString("Drive Intent Calculating: ", "Robot Relative, largest - "+largest);

		if(largest>1) {
			mFR/=largest;
			mFL/=largest;
			mBR/=largest;
			mBL/=largest;
        }
        /*
		if(visionDriving) {
			mFR*=.5;
			mFL*=.5;
			mBR*=.5;
			mBL*=.5;
        }
        */
        Robot.driveTrain.driveMotors(mFR, mBR, mFL, mBL, false);
		//oldDriveIntent = new DriveIntent(mFR, mFL, mBR, mBL, driveMode, true);
		//SmartDashboard.putNumber("FR Field", mFR);
		//SmartDashboard.putNumber("FL Field", mFL);
		//SmartDashboard.putNumber("BR Field", mBR);
		//SmartDashboard.putNumber("BL Field", mBL);

		//return oldDriveIntent;
    }

}
