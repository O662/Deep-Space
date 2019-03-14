/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.nio.ByteBuffer;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


import com.revrobotics.Rev2mDistanceSensor;
import com.revrobotics.Rev2mDistanceSensor.RangeProfile;

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
  private Rev2mDistanceSensor distSens;
 
 public SensorCollection sensors;//this is for the lazer limit switch
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public Carriage(){
    rollerMotor = new TalonSRX(RobotMap.ROLLER_MOTOR);
    rollerMotor.setNeutralMode(NeutralMode.Brake);
    
    pusher = new Solenoid(RobotMap.BUTTERFLY_PCM_MODULE1,RobotMap.PUSHER_CHANNEL);
  //  armMotor = new TalonSRX(RobotMap.FRONT_ARM_MOTOR);
    battleAxe = new Solenoid(RobotMap.BUTTERFLY_PCM_MODULE1,RobotMap.BATTLE_AXE_CHANNEL);
    distSens = new Rev2mDistanceSensor(RobotMap.LAZER_LIMIT_CARRIAGE);
    distSens.setRangeProfile(RangeProfile.kDefault);
    distSens.setAutomaticMode(true);
    distSens.setEnabled(true);
    rollerMotor.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
    rollerMotor.configForwardSoftLimitEnable(false);
    //armMotor.configForwardSoftLimitThreshold(((int) (RobotPreferences.kSRXEncoderCPR / RobotPreferences.kRearEncoderToOutputRatio * RobotPreferences.kRearMaxAngle / 360)));
    rollerMotor.configReverseSoftLimitEnable(false);
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

 
  public double getLazerDistance(){
     boolean isValid = distSens.isRangeValid();
     if (isValid){
       return distSens.getRange();
     }else {
       return Double.NaN;
    }
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
     if(getLazerDistance() <= 5){
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
    SmartDashboard.putNumber("carriage lazer", getLazerDistance());
    SmartDashboard.putBoolean("is valid", distSens.isRangeValid());
  }
}
