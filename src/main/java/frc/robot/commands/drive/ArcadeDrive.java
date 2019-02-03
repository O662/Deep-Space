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
 * Implements arcade drive style.
 */
public class ArcadeDrive extends AbstractDriveStyle {

    // Not a command

    public ArcadeDrive() {
        super();
    }

    @Override
    public void execute() {
        double y = Robot.oi.getJoystick().getRawAxis(5);
        double z = Robot.oi.getJoystick().getRawAxis(4);
        double forward = map(y);
        double turn = map(z);
        double fr = forward + turn;
        double fl = forward - turn;
        boolean skidSteerDrive = true;
        double largest = Math.abs(fl) > Math.abs(fr) ? Math.abs(fl) : Math.abs(fr);
        if (largest > 1) {
            fr /= largest;
            fl /= largest;
        }
        Robot.driveTrain.driveMotors(fr, fr, fl, fl, skidSteerDrive);
        DriverStation.reportWarning("you are in arcade drive now", true);
    }
}
