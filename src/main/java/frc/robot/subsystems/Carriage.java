/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
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
public class Carriage extends Subsystem implements LoggableSubsystem {


 
  public final TalonSRX rollerMotor;
  public final Solenoid pusher;
  public final Solenoid battleAxe;
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public Carriage(){
    rollerMotor = new TalonSRX(RobotMap.ROLLER_MOTOR);
    
    pusher = new Solenoid(RobotMap.BUTTERFLY_PCM_MODULE1,RobotMap.PUSHER_CHANNEL);
    battleAxe = new Solenoid(RobotMap.BUTTERFLY_PCM_MODULE1,RobotMap.BATTLE_AXE_CHANNEL);
  }
  
  //ROLLER
  public void moveRoller(Joystick joystick) {
		moveRollerSpeed(joystick.getRawAxis(1));
	}

	public void moveRollerSpeed(double speed) {
		rollerMotor.set(ControlMode.PercentOutput,speed);
	}

	public void stopRoller() {
		rollerMotor.set(ControlMode.PercentOutput,0);
		
  }

  public double getPosition(){
    double p;
    p = rollerMotor.getSelectedSensorPosition();
    return p;
  }
  
  //SOLINOID PUSHER

  public void toggleSolenoidValue() {
    if(getSolenoidValue()){
      pusher.set(false);
    }
	  else{
      pusher.set(true);
    }
	}

	public boolean getSolenoidValue() {
		return pusher.get();
	}

	
  
  //SOLINOID BATTLE AXE

  public void setSolenoidValueBattleAxe(Boolean value) {
		pusher.set(value);
	}

	public boolean getSolenoidValueBattleAxe() {
		return pusher.get();
	}

	public void forwardBatteAxe() {
		battleAxe.set(true);
	}

	public void reverseBattleAxe() {
		battleAxe.set(false);
  }
  
  public void BattleAxeSwitcher(){
    if (battleAxe.get() == true){
      forwardBatteAxe();
    }
    else{
      reverseBattleAxe();
    }
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

  @Override
  public void log() {

  }
}
