/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class RearIntake extends Subsystem implements LoggableSubsystem {
    private static final RearIntake INSTANCE = new RearIntake();

    // arm
    private final WPI_TalonSRX armMotor;

    // roller
    private final WPI_VictorSPX intakeRollerMotor;

    private RearIntake() {
        super();
        // arm
        armMotor = new WPI_TalonSRX(RobotMap.ARM_MOTOR);

        // roller
        intakeRollerMotor = new WPI_VictorSPX(RobotMap.INTAKE_ROLLER_MOTOR);

    }

    public static RearIntake getInstance() {
        return INSTANCE;
    }

    public void moveArm(Joystick joystick) {
        moveArm(joystick.getRawAxis(1));
    }

    public void moveArm(double speed) {
        armMotor.set(speed);
    }

    public void stop() {
        armMotor.set(0);

    }

    public void moveIntakeRoller(Joystick joystick) {
        moveIntakeRollerSpeed(joystick.getRawAxis(1));
    }

    public void moveIntakeRollerSpeed(double speed) {
        intakeRollerMotor.set(speed);
    }

    public void stopIntake() {
        intakeRollerMotor.set(0);

    }

    // encoder
    public int getEncoderValue() {
        int position;
        position = armMotor.getSelectedSensorPosition();
        return position;
    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void log() {

    }
}
