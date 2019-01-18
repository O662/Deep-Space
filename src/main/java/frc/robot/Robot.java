/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.DrivetrainState;

import java.util.ArrayList;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Vision.Limelight;

import frc.robot.lib.drivers.Looper;
import frc.robot.lib.utilitys.ButterflyDriveHelper;
import frc.robot.lib.utilitys.LatchedBoolean;
import frc.robot.subsystems.LoggableSubsystem;

import frc.robot.IControlBoard;
import frc.robot.subsystems.DrivetrainState.DriveModeState;
import frc.robot.subsystems.DrivetrainState.DrivetrainWheelState;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  //public static ExampleSubsystem m_subsystem = new ExampleSubsystem();
  public static OI m_oi;

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  private ArrayList<LoggableSubsystem> subsystemsList = new ArrayList<LoggableSubsystem>();

  public static DriveTrain driveTrain;
  public static PowerDistributionPanel pdp;
  public static DrivetrainState mDriveTrainState;
  //public static Loop mLoop;
  public static Limelight mLimelight;
  private DriveTrain mDrivetrain = DriveTrain.getInstance();
  private Looper mEnabledLooper = new Looper();
  private Looper mDisabledLooper = new Looper();
  private ButterflyDriveHelper mButterflyDriveHelper = new ButterflyDriveHelper();
  private IControlBoard mControlBoard = ControlBoard.getInstance();

  //prob goes in OI in here for now
  private LatchedBoolean mToggleDriveModeStatePressed = new LatchedBoolean();
  private LatchedBoolean mToggleDrivetrainWheelStatePressed = new LatchedBoolean();
  private LatchedBoolean mToggleBrakePressed = new LatchedBoolean();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_oi = new OI(null);

    driveTrain = new DriveTrain();
    subsystemsList.add(driveTrain);

    mDriveTrainState = new DrivetrainState();
    subsystemsList.add(mDriveTrainState);

   // mLoop = new Loop();
  //  subsystemsList.add(mLoop);



    

    pdp = new PowerDistributionPanel();

    Limelight mLimelight = new Limelight();

    //put the commapnds here
    //m_chooser.setDefaultOption("Default Auto", new ExampleCommand());
    // chooser.addOption("My Auto", new MyAutoCommand());
    SmartDashboard.putData("Auto mode", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  
  private double lastGoodS = 0;
  @Override
  public void teleopPeriodic() {

    boolean v = mLimelight.isTarget();
    double x = mLimelight.getTx();
    double y = mLimelight.getTy();
    double s = mLimelight.getTs();
    double area = mLimelight.getTa();
    lastGoodS = s!=0d && s!=-90d ? s : lastGoodS;  

    Scheduler.getInstance().run();
    double forward = mControlBoard.getDriveForward();
    double forward2 = mControlBoard.getDriveForward2();
    double sideway = mControlBoard.getDriveSideway();
    double rotation = mControlBoard.getDriveRotation();
    
    SmartDashboard.putNumber("Forward", forward);
    SmartDashboard.putNumber("Sideway", sideway);
    SmartDashboard.putNumber("Rotation", rotation);
    
    boolean tempMode = mToggleDriveModeStatePressed.update(mControlBoard.getToggleDriveMode());
    boolean tempWheel = mToggleDrivetrainWheelStatePressed.update(mControlBoard.getToggleWheelState());
    SmartDashboard.putBoolean("Drive Mode State Pressed", tempMode);
    SmartDashboard.putBoolean("Drive Wheel State Pressed", tempWheel);
    
    boolean tempBrake = mToggleBrakePressed.update(mControlBoard.getToggleBrake());
    
    DriveModeState mode = mDrivetrain.getDriveModeState();
    DrivetrainWheelState wheel = mDrivetrain.getDrivetrainWheelState();
    if(tempMode) {
      mode = mode.next();
      
    }
    if(tempWheel) {
      wheel = wheel.next();
      mDrivetrain.setWheelState(wheel);
    }
  SmartDashboard.putString("Temp mode", ""+mode);
  SmartDashboard.putString("Temp wheel", ""+wheel);

  
  if(v && mControlBoard.getUseAssist()) {
    //Rotate to head on
    //if(Math.abs(x)>.5) {
      double horizontalScalar = Math.abs(x)>7 ? x * .05: x * .1;
      //mDrivetrain.setOpenLoop(mButterflyDriveHelper.butterflyDrive(forward, sideway, rotation+horizontalScalar, 0, DriveModeState.MECANUM_ROBOT_RELATIVE, DrivetrainWheelState.MECANUM, true));
    //} else {
      double distance = mLimelight.getTargetDistance();
      double angle = lastGoodS<-50 ? 90-lastGoodS : -lastGoodS;
      double sidewaysComp = distance * Math.tan(angle) * (lastGoodS<-50 ? 1 : -1);
      double distanceComp =distance/12 - 1;
      if(distanceComp<0) distanceComp = 0;
      distanceComp/=100;
      SmartDashboard.putNumber("Distance", distance);
      SmartDashboard.putNumber("Distance Comp", distanceComp);
      mDrivetrain.setOpenLoop(mButterflyDriveHelper.butterflyDrive(forward-distanceComp, sideway+(distance<300 ? sidewaysComp*.1 : 0), rotation+horizontalScalar, 0, DriveModeState.MECANUM_ROBOT_RELATIVE, DrivetrainWheelState.MECANUM, true, true));
    //}
        
  }else {
    switch(mode) {
      case TANK:
        mDrivetrain.setOpenLoop(mButterflyDriveHelper.butterflyDrive(forward, forward2, rotation, 0, mode, wheel, tempBrake));
            break;
      case ARCADE:
            mDrivetrain.setOpenLoop(mButterflyDriveHelper.butterflyDrive(forward, sideway, rotation, 0, mode, wheel, tempBrake));
            break;
      case MECANUM_FIELD_RELATIVE:
      case MECANUM_ROBOT_RELATIVE:
            mDrivetrain.setOpenLoop(mButterflyDriveHelper.butterflyDrive(forward, sideway, rotation, 0, mode, wheel, tempBrake));
            break; 
    }
  }
    
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
  /**
	 * The log method puts interesting information to the SmartDashboard.
	 */
	private void log() {
		for (LoggableSubsystem subsystem : subsystemsList) {
			if (subsystem != null) {
				subsystem.log();
			}
		}
	}

	/**
	 * Log subsystems on the SmartDashboard
	 * @param subsystems list of subsystems
	 */
	private void addSubsystemsToDashboard(ArrayList<LoggableSubsystem> subsystems) {
		for (LoggableSubsystem subsystem : subsystems) {
			if (subsystem != null && subsystem instanceof Subsystem) {
				SmartDashboard.putData((Subsystem) subsystem);
			}
		}
	}
}
