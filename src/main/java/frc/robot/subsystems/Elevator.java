/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Elevator extends Subsystem implements LoggableSubsystem {

    private static final Elevator INSTANCE = new Elevator();

    // PUT MOTOR HERE!!!!!!!!!

    private Elevator() {
        super();
    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public static Elevator getInstance() {
        return INSTANCE;
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void log() {
        // TODO Auto-generated method stub

    }
}
