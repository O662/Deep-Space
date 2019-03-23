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
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.*;

import frc.robot.commands.vision.TurnToTarget;
import frc.robot.commands.vision.VisionDriveRobot;
//import frc.robot.subsystems.DriveTrain;
//import frc.robot.subsystems.DriveTrain.DriveTrainMode;


//this sucks
/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

private Joystick mJoystick;	
private Joystick mJoystick2;

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
public OI(){
	
	
	mJoystick = new Joystick(0);
	mJoystick2 = new Joystick(1);

	//joystick 1
	int bToggleDriveTrain = 6;//5;
	int bToggleDriveState = 5;//6;
	int bSwitchDrive = 16;
	//int bToggleDriveTrain2 = 3;
	 
	

	int bDriveToTarget =7;//3;
	
	int bElevatorMoveUp = 113;
	int bElevatorMoveDown = 17;
	int bPusher = 6;
	int bFlapper = 7;
	int bHatchPlace = 5;


	//Joystick 2
	
	int bElevatorUp = 2;
	int bElevatorDown = 4;
	int bElevatorBottom = 5;
	
	
	

	
	

	//pov controls
	
	double elevatorSpeed = 1;



//and i done change this here too
// skid steer and not
//JoystickButton toggleDriveTrain = addButton(getJoystick(), bToggleDriveState, "Toggle Drive Mode State");
	//toggleDriveTrain.whenPressed(new ToggleDriveMode());


JoystickButton switchDrive = addButton(getJoystick(), bSwitchDrive, "Toggle Drive Mode State");
	switchDrive.whenPressed(new SwitchDrive());

JoystickButton pusher = addButton(getJoystick(), bPusher, "Push hatch");
	pusher.whenPressed(new TogglePusher());

JoystickButton flapper = addButton(getJoystick(), bFlapper, "flapper control");
	flapper.whenPressed(new ToggleFlapper());

JoystickButton hatch = addButton(getJoystick(), bHatchPlace, "hatch");
	hatch.whenPressed(new PlaceHatch());

//mecanum and not 
/*
JoystickButton toggleDriveState = addButton(getJoystick(), bToggleDriveTrain, "Toggle Drive Train");
		toggleDriveState.whenPressed(new SwichDriveState(true));// (new ToggleDriveState());

JoystickButton toggleDriveState2 = addButton(getJoystick(), bToggleDriveTrain2, "toogle");
		toggleDriveState2.whenPressed(new SwichDriveState(false));
		*/
JoystickButton toggleDriveState = addButton(getJoystick(), bToggleDriveTrain, "Toggle Drive Train");
	//	toggleDriveState.whenPressed(new ToggleDriveState());// (new ToggleDriveState());
//bring this back



		/*
Trigger moveDArmUp = new POVTrigger(getJoystick(), dMoveArmUp);
		moveDArmUp.whileActive(new MoveArm(armSpeed));
Trigger moveDArmDown = new POVTrigger(getJoystick(), dMoveArmDown);
		moveDArmDown.whileActive(new MoveArm(-armSpeed));
*/






/*
JoystickButton elevatorUp = addButton(getJoystick2(), bElevatorUp, "Elevator up");
		elevatorUp.whenPressed(new MoveElevatorUp()); 


JoystickButton elevatorDown = addButton(getJoystick2(), bElevatorDown, "Elevator Down");
		elevatorDown.whenPressed(new MoveElevatorDown()); 
*/
JoystickButton elevatorUp = addButton(getJoystick2(), bElevatorUp, "Elevator up");
		elevatorUp.whenPressed(new MoveElevatorUp()); 


JoystickButton elevatorDown = addButton(getJoystick2(), bElevatorDown, "Elevator Down");
		elevatorDown.whenPressed(new MoveElevatorDown());

JoystickButton elevatorBottom = addButton(getJoystick2(), bElevatorBottom, "Elevator Down");
		elevatorBottom.whenPressed(new MoveElevatorBottom()); 

		/*
JoystickButton elevatorMoveUp = addButton(getJoystick(), bElevatorMoveUp, "Elevator Up");
		elevatorMoveUp.whileHeld(new MoveElevatorNoPosition(.5)); 
		elevatorMoveUp.whenReleased(new MoveElevatorNoPosition(0));

JoystickButton elevatorMoveDown = addButton(getJoystick(), bElevatorMoveDown, "Elevator Down");
		elevatorMoveDown.whileHeld(new MoveElevatorNoPosition(-.5)); 
		elevatorMoveDown.whenReleased(new MoveElevatorNoPosition(0));
		*/



JoystickButton driveToTarget = addButton(getJoystick(), bDriveToTarget, "drive to target");
		driveToTarget.whenPressed(new VisionDriveRobot());
 



SmartDashboard.putData("ZERO ELEVATOR", new ZeroElevatorEncoders());
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
		return new JoystickButton(joystick, buttonNumber);
		//TODO uncomment to see commands on dashboard
		//SmartDashboard.putData(key, button);
		//return button;
	}

	
	
	public Joystick getJoystick() {
		return mJoystick;
	}

	public Joystick getJoystick2() {
		return mJoystick2;
	}

	
}
