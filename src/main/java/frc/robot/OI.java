/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.HashMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


import frc.robot.RobotMap;
import frc.robot.commands.*;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.DriveTrain.DriveModeState;


//this sucks
/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

private Joystick mJoystick;	
public OI(){
	
	
	Joystick mJoystick = new Joystick(1);
	
	int bToggleDriveTrain = 1;
	int bToggleDriveState = 2;

	

	JoystickButton toggleDriveState = addButton(getJoystick(), bToggleDriveState, "Toggle Drive Mode State");
	toggleDriveState.whenPressed(new ToggleDriveMode());


	JoystickButton toggleDriveTrain = addButton(getJoystick(), bToggleDriveTrain, "Toggle Drive Train");
		toggleDriveTrain.whenPressed(new ToggleDriveState());
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
	
	private JoystickButton addButton(Joystick joystick, int buttonNumber, String key) {
		JoystickButton button = new JoystickButton(joystick, buttonNumber);
		//TODO uncomment to see commands on dashboard
		//SmartDashboard.putData(key, button);
		return button;
	}

	
	
	public Joystick getJoystick() {
		return mJoystick;
	}



	
}
