/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.subsystems.Carriage;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.LoggableSubsystem;
import frc.robot.subsystems.RearIntake;


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




/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
	
	public static OI oi;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();
	private ArrayList<LoggableSubsystem> subsystemsList = new ArrayList<LoggableSubsystem>();

	
	public static DriveTrain driveTrain;
	public static Carriage carriage;
	public static RearIntake rearIntake;
	public static PowerDistributionPanel pdp;
	public static Elevator elevator;

	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	
	
	@Override
	public void robotInit() {
		//m_chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		//SmartDashboard.putData("Auto mode", m_chooser);
		//SmartDashboard.putString("DriveMode rob", "" + Robot.driveTrain.getDriveState());
		//SmartDashboard.putString("DriveStateRob", "" + Robot.driveTrain.getDriveTrain());
		//SmartDashboard.putString("i am here", "here");
		
		//subsystems
		pdp = new PowerDistributionPanel();
		driveTrain = new DriveTrain();
		subsystemsList.add(driveTrain);
		carriage = new Carriage();
		subsystemsList.add(carriage);
		rearIntake = new RearIntake();
		subsystemsList.add(rearIntake);
		elevator = new Elevator();
		subsystemsList.add(elevator);

		
		oi = new OI();
		//ArrayList<LoggableSubsystem> tempList = new ArrayList<LoggableSubsystem>();
		//addSubsystemsToDashboard(tempList);
		ArrayList<LoggableSubsystem> tempList = new ArrayList<LoggableSubsystem>();
		tempList.add(driveTrain);
		addSubsystemsToDashboard(tempList);
		
	}
	public void operatorControl(){
		double counter = 0.0;
		while(isOperatorControl() && isEnabled()){
			SmartDashboard.putNumber("counter", counter++);
		}
		Timer.delay(.10);
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
		SmartDashboard.putData(Scheduler.getInstance());
		
	}


	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		log();
		
	}
	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		Scheduler.getInstance().run();
		//SmartDashboard.putData("Auto mode", m_chooser);
		//SmartDashboard.putString("DriveMode", "" + Robot.driveTrain.getDriveTrain());
		//SmartDashboard.getString("DriveState", "" + Robot.driveTrain.getDriveState());
	}

	private void log() {
		for (LoggableSubsystem subsystem : subsystemsList) {
			if (subsystem != null) {
				subsystem.log();
			}
		}
	//	SmartDashboard.putNumber("front left motor", Robot.driveTrain.motorLeft1.get());
	//	SmartDashboard.putString("driveState","" + Robot.driveTrain.getDriveState());
	//	SmartDashboard.putString("driveMode in robot","" + Robot.driveTrain.getDriveTrain());

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
