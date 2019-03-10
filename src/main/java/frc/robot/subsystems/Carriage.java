/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.nio.ByteBuffer;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;


/**
 * Add your docs here.
 */
public class Carriage extends Subsystem implements LoggableSubsystem {


 
  public final TalonSRX rollerMotor;
  public final Solenoid pusher;
  public final Solenoid battleAxe;
  private final I2C limit;
  byte[] lazer = new byte[8];
  double lazerDistance;
  public SensorCollection sensors;//this is for the lazer limit switch
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public Carriage(){
    rollerMotor = new TalonSRX(RobotMap.ROLLER_MOTOR);
    rollerMotor.setNeutralMode(NeutralMode.Brake);
    
    pusher = new Solenoid(RobotMap.BUTTERFLY_PCM_MODULE1,RobotMap.PUSHER_CHANNEL);
  //  armMotor = new TalonSRX(RobotMap.FRONT_ARM_MOTOR);
    battleAxe = new Solenoid(RobotMap.BUTTERFLY_PCM_MODULE1,RobotMap.BATTLE_AXE_CHANNEL);
    limit = new I2C(RobotMap.LAZER_LIMIT2, 0x52);
    sensors = rollerMotor.getSensorCollection();
  
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
    if (battleAxe.get() == false){
      forwardBatteAxe();
    }
    else{
      reverseBattleAxe();
    }
  }

  //this method reads a byte value from the lazer encoder which holds the distance 
  public double getLazer(){
    limit.read(0x52, 1, lazer);
    ByteBuffer buffer = ByteBuffer.wrap(lazer); //turns the byte value into a double
    //lazerDistance =  buffer.getDouble();
   
    byte b = buffer.get(0);
     SmartDashboard.putNumber("lazer distance", b);
    return (double)b;
  }

/**
 * 
 * @return 
 * This method looks at the distance the lazer is reading and sets it to true or false if the 
 * ball is in the roller or not
 *  true = the ball is in the roller
 *  false = the ball is not in the roller
 */
  public boolean stopRollerLazer(){
    boolean isStopped = false;
     if(getLazer() <= 5){
       stopRoller();
       isStopped = true;
     }
     return isStopped;
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
