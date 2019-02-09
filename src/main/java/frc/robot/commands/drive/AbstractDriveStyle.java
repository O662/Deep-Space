/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drive;

import frc.robot.Robot;

/**
 * Abstract base class for implementing drive styles, such as mecanum, tank and
 * arcade. Typically, implementing classes need only to implement the
 * {@link #execute()} method.
 */
public abstract class AbstractDriveStyle {

    private final double deadzone;

    /**
     * Default constructor with dead zone = 0.1.
     */
    public AbstractDriveStyle() {
        this(0.1);
    }

    /**
     * Constructor specifying dead zone.
     * 
     * @param deadZone
     *            dead zone for suppressing small values from idle joystick
     */
    public AbstractDriveStyle(double deadZone) {
        this.deadzone = deadZone;
        Robot.driveTrain.setBrake(true);
        Robot.driveTrain.setDefaltRampRate();
    }

    /**
     * Maps an input value from the joystick to an output value ranging from 0 to 1.
     * Incorporates a deadzone to suppress small values from an idle joystick.
     * 
     * @param input
     *            value from joystick
     * @return output value to provide to drive train subsystem
     */
    public double map(double input) {
        double absval = Math.abs(input);
        if (absval <= deadzone) {
            return 0;
        } else {
            return (absval / input) * (absval - deadzone) / (1 - deadzone);
        }
    }

    /**
     * Provides input to the drive train subsystem based on joystick values. Called
     * repeatedly by command for drive train.
     */
    public abstract void execute();
}
