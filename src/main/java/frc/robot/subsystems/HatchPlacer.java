/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


import com.sun.jdi.event.Event;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.drivers.Rev2mDistanceSensor;
import frc.robot.drivers.Rev2mDistanceSensor.RangeProfile;
import frc.robot.drivers.Rev2mDistanceSensor.Unit;

/**
 * Add your docs here.
 */
public class HatchPlacer extends Subsystem implements LoggableSubsystem {
 // DoubleSolenoid pusher1;
  //DoubleSolenoid pusher2;
  //DoubleSolenoid flapper;
  public DigitalInput limitSwitch;
  private Rev2mDistanceSensor distSens;
  double distance;
  NetworkTableEntry lazer;
  double dist;
  boolean cheak;
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public HatchPlacer(){
    limitSwitch = new DigitalInput(RobotMap.PUSHER_LIMIT);
    distSens = new Rev2mDistanceSensor(RobotMap.LAZER_LIMIT_CARRIAGE,Unit.kInches,RangeProfile.kDefault);
  //  distSens.setRangeProfile(RangeProfile.kHighSpeed);
    distSens.setDistanceUnits(Unit.kInches);
    distSens.setAutomaticMode(true);
    distSens.setEnabled(true);
    distance = distSens.getRange();
   

    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("the lazer");
    lazer = table.getEntry("distance");
     dist = distSens.getRange();
    /*
    pusher1 = new DoubleSolenoid(RobotMap.PUSHER_1_CHANNEL_FORWARD, RobotMap.PUSHER_1_CHANNEL_REVERSE);
    pusher2 = new DoubleSolenoid(RobotMap.PUSHER_2_CHANNEL_FORWARD, RobotMap.PUSHER_2_CHANNEL_REVERSE);
    flapper = new DoubleSolenoid(RobotMap.FLAPPER_CHANNEL_FORWARD, RobotMap.FLAPPER_CHANNEL_REVERSE);
    */
  }


public boolean isSwitchClosed(){
  boolean closed = limitSwitch.get();

  
    return limitSwitch.get();
  }
 

  public void cheakLazer(){
   cheak = false;
   
    lazer.setDouble(dist);
    dist = distSens.getRange();

    lazer.addListener(event -> {
      cheak = true;
      System.out.println("Lazer Changed Value :" + event.value.getValue());
    }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
   if (cheak == false){
     System.out.print("help me");
    distSens = new Rev2mDistanceSensor(RobotMap.LAZER_LIMIT_CARRIAGE,Unit.kInches,RangeProfile.kHighSpeed);
   }
  }

  public double getLazerDistance(){
    double dist = distSens.getRange();
    
    
    if(dist == distance){
      distSens = new Rev2mDistanceSensor(RobotMap.LAZER_LIMIT_CARRIAGE,Unit.kInches,RangeProfile.kHighSpeed);
    }
    boolean isValid = distSens.isRangeValid();
    
    if (isValid){
      return distSens.getRange();
    }else {
      System.out.println("time" + distSens.getTimestamp());
      
      //distSens = new Rev2mDistanceSensor(RobotMap.LAZER_LIMIT_CARRIAGE,Unit.kInches,RangeProfile.kHighSpeed);
      return Double.NaN;
     
   }
 }

 

/**
 * true solenoid forward
 * false solenoid back
 */
/*
  public Boolean getPusher(){
    if(pusher1.get() == Value.kForward){
      return true;
    }
    else{
      return false;
    }
  }
  */

  /**
   *
   * @return
   * true solenoid forward
   * false soleanoid back
   */
  /*
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
  */
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  public void log() {
    SmartDashboard.putBoolean("limit Switch", isSwitchClosed());
    SmartDashboard.putNumber("lazer", getLazerDistance());

  }
}
