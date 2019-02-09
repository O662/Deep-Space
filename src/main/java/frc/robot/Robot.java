/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Carriage;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LoggableSubsystem;
import frc.robot.subsystems.RearIntake;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

    /** Operator interface */
    public static final OI oi = OI.getInstance();

    Command m_autonomousCommand;
    SendableChooser<Command> m_chooser = new SendableChooser<>();
    private ArrayList<LoggableSubsystem> subsystemsList = new ArrayList<LoggableSubsystem>();

    public static final DriveTrain driveTrain = DriveTrain.getInstance();
    public static final Carriage carriage = Carriage.getInstance();
    public static final RearIntake rearIntake = RearIntake.getInstance();
    public static final PowerDistributionPanel pdp = new PowerDistributionPanel();

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */

    @Override
    public void robotInit() {
        // Subsystems
        // subsystemsList.add(pdp);
        subsystemsList.add(driveTrain);
        subsystemsList.add(carriage);
        subsystemsList.add(rearIntake);

        // Log subsystem itself to dashboard -- typically a small subset of subsystems
        // The log method of all LoggableSubsystem's are called during periodic
        ArrayList<LoggableSubsystem> tempList = new ArrayList<LoggableSubsystem>();
        tempList.add(driveTrain);
        addSubsystemsToDashboard(tempList);

    }

    /**
     * Why is this here???
     */
    @Deprecated
    public void operatorControl() {
        double counter = 0.0;
        while (isOperatorControl() && isEnabled()) {
            SmartDashboard.putNumber("counter", counter++);
        }
        Timer.delay(.10);
    }

    /**
     * This function is called once each time the robot enters Disabled mode. You
     * can use it to reset any subsystem information you want to clear when the
     * robot is disabled.
     */
    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
        log();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable chooser
     * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
     * remove all of the chooser code and uncomment the getString code to get the
     * auto name from the text box below the Gyro
     *
     * <p>
     * You can add additional auto modes by adding additional commands to the
     * chooser code above (like the commented example) or additional comparisons to
     * the switch structure below with additional strings & commands.
     */
    @Override
    public void autonomousInit() {
        m_autonomousCommand = m_chooser.getSelected();

        /*
         * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
         * switch(autoSelected) { case "My Auto": autonomousCommand = new
         * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
         * ExampleCommand(); break; }
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
        log();
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
        log();
    }

    /**
     * Call log method for all subsystems.
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
     * 
     * @param subsystems
     *            list of subsystems
     */
    private void addSubsystemsToDashboard(ArrayList<LoggableSubsystem> subsystems) {
        for (LoggableSubsystem subsystem : subsystems) {
            if (subsystem != null && subsystem instanceof Subsystem) {
                SmartDashboard.putData((Subsystem) subsystem);
            }
        }
    }
}
