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
 * Implements tank drive style.
 */
public class TankDrive extends AbstractDriveStyle {

    // Not a command

    public TankDrive() {
        super();
    }

    @Override
    public void execute() {
        double fr = Robot.oi.getDriveOperator().getDriveForward();
        double fl = Robot.oi.getDriveOperator().getDriveForward2();
        double forwardRight = map(fr);
        double forwardLeft = map(fl);
        Boolean skidSteerDrive = true;
        Robot.driveTrain.driveMotors(forwardRight, forwardRight, forwardLeft, forwardLeft, skidSteerDrive);
        DriverStation.reportWarning("you are in tank drive now", true);
    }
}
