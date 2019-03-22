/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class HatchPlacer extends Subsystem implements LoggableSubsystem {
  DoubleSolenoid pusher1;
  DoubleSolenoid pusher2;
  DoubleSolenoid flapper;
  public DigitalInput limitSwitch;
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public HatchPlacer(){
    limitSwitch = new DigitalInput(RobotMap.PUSHER_LIMIT);
    pusher1 = new DoubleSolenoid(RobotMap.PUSHER_1_CHANNEL_FORWARD, RobotMap.PUSHER_1_CHANNEL_REVERSE);
    pusher2 = new DoubleSolenoid(RobotMap.PUSHER_2_CHANNEL_FORWARD, RobotMap.PUSHER_2_CHANNEL_REVERSE);
    flapper = new DoubleSolenoid(RobotMap.FLAPPER_CHANNEL_FORWARD, RobotMap.FLAPPER_CHANNEL_REVERSE);
  }


public boolean isSwitchClosed(){
  return limitSwitch.get();
}

/**
 * true solenoid forward
 * false solenoid back
 */
  public Boolean getPusher(){
    if(pusher1.get() == Value.kForward){
      return true;
    }
    else{
      return false;
    }
  }

  /**
   *
   * @return
   * true solenoid forward
   * false soleanoid back
   */
  public Boolean getFlapper(){
    if(flapper.get() == Value.kForward){
      return true;
    }
    else{
      return false;
    }
  }

  public void togglePusher(){
    if(getPusher() == true){
      pusher1.set(Value.kReverse);
      pusher2.set(Value.kReverse);
    }
    else{
      pusher1.set(Value.kForward);
      pusher2.set(Value.kForward);
    }
  }

  public void toggleFlapper(){
    if(getFlapper() == true){
      flapper.set(Value.kReverse);
     
    }
    else{
      flapper.set(Value.kForward);
      
    }
  }
  public void setFlapper(Value state){
    
    flapper.set(state);
  }
  public void setPusher(Value state){
    
    pusher1.set(state);
    pusher2.set(state);
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  public void log() {
    SmartDashboard.putBoolean("limit Switch", isSwitchClosed());

  }
}
