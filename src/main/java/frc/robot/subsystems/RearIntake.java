/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.nio.ByteBuffer;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.Rev2mDistanceSensor;
import com.revrobotics.Rev2mDistanceSensor.RangeProfile;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.RobotPreferences;
import frc.robot.commands.DriveArms;
import frc.robot.commands.IntakeRoller;

/**
 * Add your docs here.
 */
public class RearIntake extends Subsystem implements LoggableSubsystem {
  //arm
  private final TalonSRX armMotor;
  private final VictorSPX armMotor2;
  public boolean switcher;
  private final Solenoid lock;
  private Rev2mDistanceSensor distSens;

  
  //roller
  public final VictorSPX intakeRollerMotor;
  public SensorCollection sensors;//this is for the lazer limit switch

  


  public RearIntake(){
    //laser stopper thing ask kirby for correct api cause im lazy and dont want
    //to find it on my own

    //update: kirby does not know 

    distSens = new Rev2mDistanceSensor(RobotMap.LAZER_LIMIT_REAR_INTAKE);
    
    distSens.setAutomaticMode(true);
    distSens.setEnabled(true);
    distSens.setRangeProfile(RangeProfile.kHighSpeed);
    //arm
    armMotor = new TalonSRX(RobotMap.ARM_MOTOR);
    armMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    armMotor2 = new VictorSPX(RobotMap.ARM_MOTOR_2);

    armMotor2.follow(armMotor);
    armMotor.setInverted(InvertType.InvertMotorOutput);
    moveArm(0);
    armMotor.setNeutralMode(NeutralMode.Brake);
    armMotor2.setNeutralMode(NeutralMode.Brake);
    

    //roller
    intakeRollerMotor = new VictorSPX(RobotMap.INTAKE_ROLLER_MOTOR);
    sensors = armMotor.getSensorCollection();
    armMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

    
    //limit = new DigitalInput(RobotMap.LIMIT_SWITCH);
    //lock
    lock = new Solenoid(RobotMap.BUTTERFLY_PCM_MODULE1, RobotMap.LOCK_CHANNEL);
    armMotor.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
    armMotor.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
    armMotor.configForwardSoftLimitEnable(false);
    //armMotor.configForwardSoftLimitThreshold(((int) (RobotPreferences.kSRXEncoderCPR / RobotPreferences.kRearEncoderToOutputRatio * RobotPreferences.kRearMaxAngle / 360)));
    armMotor.configReverseSoftLimitEnable(false);
   // intakeRollerMotor.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
    intakeRollerMotor.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
    intakeRollerMotor.configForwardSoftLimitEnable(false);
    //armMotor.configForwardSoftLimitThreshold(((int) (RobotPreferences.kSRXEncoderCPR / RobotPreferences.kRearEncoderToOutputRatio * RobotPreferences.kRearMaxAngle / 360)));
    intakeRollerMotor.configReverseSoftLimitEnable(false);
   // armMotor.configReverseSoftLimitThreshold(((int) (RobotPreferences.kSRXEncoderCPR / RobotPreferences.kRearEncoderToOutputRatio * RobotPreferences.kRearMinAngle / 360)));
    
  }

  public void moveArm(Joystick joystick) {
    moveArm(joystick.getRawAxis(1));
    
  }

  //set and moves arm to specific degrees for the arm
  public void armPickup(int degree){
    int targetSensorPosition = (int) Math.round(degree / RobotPreferences.kDistancePerRevolution * 4096 * RobotPreferences.kRatioToOutput);
    armMotor.set(ControlMode.Position, targetSensorPosition);
  }

	public void moveArm(double speed) {
   /*
    //zeros the encoder if it crosses in or out of the limit switch
    if(isLimitSwitchClosed() && switcher == false) {
      zeroEncoder();
      setSwitcher();
    }
    else if( isLimitSwitchClosed() == false && switcher == true){
      zeroEncoder();
      setSwitcher();
    }
    */
    armMotor.set(ControlMode.PercentOutput,speed);
  }

  //stops arm
	public void stop() {
    armMotor.set(ControlMode.PercentOutput, 0);
    
  }

  
  //roller
  public void moveIntakeRoller(Joystick joystick) {
    // goes while the limit switch is not activated
    while(stopRollerLazer() == false){
      moveIntakeRollerSpeed(joystick.getRawAxis(1));
      stopRollerLazer();
    }
  }

	public void moveIntakeRollerSpeed(double speed) {
		intakeRollerMotor.set(ControlMode.PercentOutput,speed);
	}

	public void stopIntake() {
		intakeRollerMotor.set(ControlMode.PercentOutput,0);
		
  }

  //LOCK
  public void toggleLock(){
    if(getLock()){
      lock.set(false);
    }
    else{
      lock.set(true);
    }
  }

  public boolean getLock(){
    return lock.get();
  }

  //encoder
  public int getEncoderValue(){
    int position;
    position = armMotor.getSelectedSensorPosition();
    return position;
  }

  public void zeroEncoder(){
    armMotor.setSelectedSensorPosition(0);
  }

  //limit switch which just so happens to be a LAZER! how cool is that!... coffee rocks!
  
  public boolean isLimitSwitchClosed(){
    boolean closed;
    if(sensors.isFwdLimitSwitchClosed()){
      closed = true;
    }
    else{
      closed = false;
    }
   return closed;
  }

  public void setSwitcher(){
    if(isLimitSwitchClosed()){
      switcher = true;
    }
    else{
      switcher = false;
    }
  }

  
  public double getLazerDistance(){
    boolean isValid = distSens.isRangeValid();
    //if (isValid){
      return distSens.getRange();
    //}else {
    //  return Double.NaN;
    //}
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
       stopIntake();
       isStopped = true;
     }
     return isStopped;
  }
 

  @Override
  public void initDefaultCommand() {
    //setDefaultCommand(new DriveArms());
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  public void log() {
    SmartDashboard.putBoolean("is arm up", isLimitSwitchClosed());
    SmartDashboard.putNumber("arm Encoder", getEncoderValue());
    SmartDashboard.putNumber("Lazer", getLazerDistance());

  }
}
