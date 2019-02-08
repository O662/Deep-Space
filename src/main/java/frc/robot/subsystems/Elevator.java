/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.SensorTerm;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Elevator extends Subsystem implements LoggableSubsystem {

  

  private static final double kDistancePerRevolution = 1;//in inches
  private static final double kRatioToOutput = 60/18;
  private static final double kChassisHeightOffset = 4.5; //in inches
  public int height;


  //this magical subsystems creates the motors that control the elevator 

  public TalonSRX elevatorMotor1;
  public TalonSRX elevatorMotor2;

  public SensorCollection sensors;

  public Elevator(){
    elevatorMotor1 = new TalonSRX(RobotMap.ELEVATOR_MOTOR_1);
    elevatorMotor2 = new TalonSRX(RobotMap.ELEVATOR_MOTOR_2);
    sensors = elevatorMotor1.getSensorCollection();

    elevatorMotor1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    elevatorMotor2.follow(elevatorMotor1, FollowerType.AuxOutput1);
    //TODO add PID settings into elevatorMotor1
  }

  public void setElevatorHeight(double height) {
    int targetSensorPosition = (int) Math.round((height - kChassisHeightOffset) / kDistancePerRevolution * 4096 * kRatioToOutput);
    elevatorMotor1.set(ControlMode.Position, targetSensorPosition);
    this.height = (int) height;
  }

  public void zeroEncoder() {
    elevatorMotor1.setSelectedSensorPosition(0, 0, 10);
  }

  public int getEncoder(){
    return elevatorMotor1.getSelectedSensorPosition();
  }
  /**
   * 
   * @return true if greater positive false if negative
   */
  public boolean getEncoderDirection() {
    boolean direction = true;
    if(elevatorMotor1.getSelectedSensorPosition() > 0){
      direction = true;
    }
    if(elevatorMotor1.getSelectedSensorPosition() < 0){
      direction = false;
    }

    return direction;

  }
  
  public boolean getLaser(){
     return sensors.isFwdLimitSwitchClosed();

  }
   
  public boolean isSwitchClosed(){
    if(getLaser()){
      zeroEncoder();
    }
    return getLaser();
  }
/**
 * 
 * @param speed sets the speed of both motors for the elevator
 */
  public void moveElevator(double speed){
    elevatorMotor1.set(ControlMode.PercentOutput,speed);
    //elevatorMotor2.set(ControlMode.PercentOutput,speed);
    if(isSwitchClosed()){
      zeroEncoder();
    }
  }

  public void endElevator(){
    elevatorMotor1.set(ControlMode.PercentOutput, 0);
    //elevatorMotor2.set(ControlMode.PercentOutput, 0);
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

  


