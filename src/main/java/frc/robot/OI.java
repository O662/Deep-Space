/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.IntakeRoller;
import frc.robot.commands.MoveArm;
import frc.robot.commands.MoveRoller;
import frc.robot.commands.Pusher;
import frc.robot.commands.ToggleDriveMode;
import frc.robot.commands.ToggleDriveState;
import frc.robot.operator.DriveOperatorGameController;
import frc.robot.operator.IDriveOperator;

//this sucks
/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    private static final OI INSTANCE = new OI();

    private IDriveOperator driveOperator;

    /*
    
    private class POVTrigger extends Trigger{
    	/*
    	 * Positions
    	 * 		7	0	1
    	 * 		6	-1	2
    	 * 		5	4	3
    	 * 		
    	 */
    /*
    private int degrees;
    private Joystick joy;
    //Make sure to call using -1 through 7, else all dies...
    public POVTrigger(Joystick joy, int position) {
    	degrees = position*45;
    	this.joy = joy;
    }
    
    @Override
    public boolean get() {
    	// TODO Auto-generated method stub
    	return joy.getPOV(0)==degrees;
    }
    }
    
    */

    public static OI getInstance() {
        return INSTANCE;
    }

    private OI() {
        driveOperator = new DriveOperatorGameController(new Joystick(0));

        //pov controls
        //int dMoveArmUp = 4;
        //int dMoveArmDown = 0;

        //speeds
        double armSpeed = 1;
        double rollerSpeed = 1;
        double intakeRollerSpeed = 1;

        // skid steer and not
        driveOperator.getToggleDriveModeButton().whenPressed(new ToggleDriveMode());

        //mecanum and not 
        driveOperator.getToggleDriveStateButton().whenPressed(new ToggleDriveState());// (new ToggleDriveState());
        /*
        Trigger moveDArmUp = new POVTrigger(getJoystick(), dMoveArmUp);
        moveDArmUp.whileActive(new MoveArm(armSpeed));
        Trigger moveDArmDown = new POVTrigger(getJoystick(), dMoveArmDown);
        moveDArmDown.whileActive(new MoveArm(-armSpeed));
        */
        driveOperator.getMoveArmUpButton().whenPressed(new MoveArm(armSpeed));
        driveOperator.getMoveArmDownButton().whenPressed(new MoveArm(-armSpeed));
        driveOperator.getMoveRollerButton().whenPressed(new MoveRoller(rollerSpeed));
        driveOperator.getMoveIntakeRollerForwardButton().whileHeld(new IntakeRoller(intakeRollerSpeed));
        driveOperator.getMoveIntakeRollerBackwardButton().whileHeld(new IntakeRoller(-intakeRollerSpeed));
        driveOperator.getPushForwardButton().whenPressed(new Pusher(true));
        driveOperator.getPushBackwardButton().whenPressed(new Pusher(false));
    }

    public IDriveOperator getDriveOperator() {
        return driveOperator;
    }

    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a
    //// joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}
