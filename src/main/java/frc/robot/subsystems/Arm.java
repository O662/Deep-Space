/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Arm extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
private final WPI_TalonSRX armMotor;
private final Encoder armEncoder;
  public Arm(){
    armMotor = new WPI_TalonSRX(RobotMap.ARM_MOTOR);
    armEncoder = new Encoder(RobotMap.ARM_ENCODER_A, RobotMap.ARM_ENCODER_B);

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

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
