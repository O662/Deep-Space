/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Carriage extends Subsystem {
 
  public final CANSparkMax rollerMotor;
  public final CANEncoder rollEncoder;
  public final Solenoid pusher;
  public final Solenoid battleAxe;
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public Carriage(){
    rollerMotor = new CANSparkMax(RobotMap.ROLLER_MOTOR, MotorType.kBrushless);
    rollEncoder = new CANEncoder(rollerMotor);
    pusher = new Solenoid(RobotMap.PUSHER_CHANNEL);
    battleAxe = new Solenoid(RobotMap.BATTLE_AXE_CHANNEL);
  }
  
  //ROLLER
  public void moveRoller(Joystick joystick) {
		moveRollerSpeed(joystick.getRawAxis(1));
	}

	public void moveRollerSpeed(double speed) {
		rollerMotor.set(speed);
	}

	public void stopRoller() {
		rollerMotor.set(0);
		
  }

  public double getTemp(){
    double temp;
    temp = rollerMotor.getMotorTemperature();
    return temp;
  }
  
  //SOLINOID PUSHER

  public void setSolenoidValue(Boolean value) {
		pusher.set(value);
	}

	public boolean getSolenoidValue() {
		return pusher.get();
	}

	public void forward() {
		setSolenoidValue(true);
	}

	public void reverse() {
		setSolenoidValue(false);
  }
  
  //SOLINOID BATTLE AXE

  public void setSolenoidValueBattleAxe(Boolean value) {
		pusher.set(value);
	}

	public boolean getSolenoidValueBattleAxe() {
		return pusher.get();
	}

	public void forwardBatteAxe() {
		setSolenoidValue(true);
	}

	public void reverseBattleAxe() {
		setSolenoidValue(false);
  }
  
  //encoder

  /*
  public int getEncoderValue(){
    int position;
    position = rollerMotor.get

  }
*/
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
