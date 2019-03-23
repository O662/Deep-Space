/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


//import com.sun.jdi.event.Event;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DriverStation;
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
  Solenoid pusher1;
  
  Solenoid flapper;
  public DigitalInput limitSwitch;
  private Rev2mDistanceSensor distSens;
  private boolean distSensInitialized = false;
  double distance;
  NetworkTableEntry lazer;
  double dist;
  boolean cheak;
  boolean nan;
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public HatchPlacer(){
    limitSwitch = new DigitalInput(RobotMap.PUSHER_LIMIT);
    //distSens = new Rev2mDistanceSensor(RobotMap.LAZER_LIMIT_CARRIAGE,Unit.kInches,RangeProfile.kDefault);
    initializeLazer();
  //  distSens.setRangeProfile(RangeProfile.kHighSpeed);
    distSens.setDistanceUnits(Unit.kInches);
    distSens.setAutomaticMode(true);
    distSens.setEnabled(true);
    distance = distSens.getRange();

    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("the lazer");
    lazer = table.getEntry("distance");
     dist = distSens.getRange();
    
    pusher1 = new Solenoid(RobotMap.PUSHER_CHANNEL);
    
    flapper = new Solenoid(RobotMap.FLAPPER_CHANNEL);
    
  }

  private void initializeLazer() {
    int nretry = 10;
    int iretry = 0;
    while (iretry < nretry) {
      distSens = new Rev2mDistanceSensor(RobotMap.LAZER_LIMIT_CARRIAGE,Unit.kInches,RangeProfile.kDefault);
      distSensInitialized = distSens.isInitialized();
      if (distSensInitialized) {
        DriverStation.reportWarning(this.getClass().getName() + ": Successfully initialized Rev2mDistanceSensor after try #" + iretry, false);
        return;
      }
      iretry++;
      try {
        Thread.sleep(250);
      } catch (InterruptedException e) {
        // Ignore and carry on...
      }
    }
    DriverStation.reportWarning(this.getClass().getName()
        + ": Failed to initialize Rev2mDistanceSensor after " + nretry + " attempts!", false);
  }

  public boolean isSwitchClosed(){
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

  /**
   * 
   * @return
   * returns a number stating the distance the sensor reads if it is valid
   * if it is not valid do to being far away then it will return NaN
   */
  public double getLazerDistance(){
    double dist = distSens.getRange();

    //if(dist == distance){
    //  distSens = new Rev2mDistanceSensor(RobotMap.LAZER_LIMIT_CARRIAGE,Unit.kInches,RangeProfile.kHighSpeed);
    //}
    boolean isValid = distSens.isRangeValid();
    if (isValid){
      nan = false;
      return distSens.getRange();
    }else {
     // System.out.println("time" + distSens.getTimestamp());
      nan= true;
      //distSens = new Rev2mDistanceSensor(RobotMap.LAZER_LIMIT_CARRIAGE,Unit.kInches,RangeProfile.kHighSpeed);
      return Double.NaN;
    }
  }

  /**
   * 
   * @return
   * true = if hatch is present
   * false = if hatch is not present
   */
  public boolean isHatchPresent(){
    if(nan){
      return false;
    }
    else if(nan == false && getLazerDistance() < 2){
      return true;
    }
    else{
      return false;
    }

  }

/**
 * true solenoid forward
 * false solenoid back
 */

  public Boolean getPusher(){
   return pusher1.get();
  }
  

  /**
   *
   * @return
   * true solenoid forward
   * false soleanoid back
   */
  
  public Boolean getFlapper(){
    return flapper.get();
  }

  public void togglePusher(){
    if(getPusher() == true){
      pusher1.set(false);
      
    }
    else{
      pusher1.set(true);
     
    }
  }

  public void toggleFlapper(){
    if(getFlapper() == true){
      flapper.set(false);
     
    }
    else{
      flapper.set(true);
      
    }
  }
  public void setFlapper(Boolean state){
    
    flapper.set(state);
  }
  public void setPusher(Boolean state){
    
    pusher1.set(state);
    
  }
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  public void log() {
    SmartDashboard.putBoolean("limit Switch", isSwitchClosed());
    SmartDashboard.putNumber("lazer", getLazerDistance());
    SmartDashboard.putBoolean("initilized",  distSensInitialized);
    SmartDashboard.putBoolean("hatch present", isHatchPresent());
  }
}
