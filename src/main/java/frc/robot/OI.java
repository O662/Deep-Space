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
import frc.robot.ControlBoard.GamepadControlBoardType;
import frc.robot.lib.drivers.IDriveControlBoard;
import frc.robot.RobotMap;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI implements IDriveControlBoard{
 



  private static OI mInstance = null;

	@SuppressWarnings("serial")
	private static final HashMap<String, Integer> xboxButtonNameToId = new HashMap<String, Integer>() {{
		//Mapping for XBox buttons
		put("A", 1);
		put("B", 2);
		put("X", 3);
		put("Y", 4);
		put("Left Shoulder", 5);
		put("Right Shoulder", 6);
		put("Back", 7);
		put("Start", 8);
	}};
	
	private static final HashMap<String, Integer> xboxAxisNameToId = new HashMap<String, Integer>() {{
		//Mapping for XBox axis
		put("Left X", 0);			//-1 to 1
		put("Left Y", 1);			//-1 to 1
		put("Left Trigger", 2);		//0 to 1
		put("Right Trigger", 3);	//0 to 1
		put("Right X", 4);			//-1 to 1
		put("Right Y", 5);			//-1 to 1
		
  }};
  

	


	public OI(GamepadControlBoardType kgamepadtype) {
		mjoystick = new Joystick(RobotMap.kDriveGamepadPort);
	}

	public static OI getInstance() {
        if (mInstance == null) {
            mInstance = new OI(RobotMap.kGamepadType);
        }

        return mInstance;
    }

   private Joystick mjoystick;
  private Joystick mjoystick2;

    
    
   // private GamepadDriveControlBoard(GamepadControlBoardType type) {
  //      mJoystick = new Joystick(RobotMap.kDriveGamepadPort);
   // }
	
    @Override
	public double getDriveForward() {
		return mjoystick.getRawAxis(5);
	}

    @Override
	public double getDriveForward2() {
		return mjoystick.getRawAxis(1);
	}

    @Override
	public double getDriveSideway() {
		return mjoystick.getRawAxis(4);
	}

    @Override
	public double getDriveRotation() {
		return mjoystick.getRawAxis(0);
	}

    @Override
	public boolean getToggleDriveMode() {
		return mjoystick.getRawButton(5);
	}

    @Override
	public boolean getForceSkidSteer() {
		return mjoystick.getRawButton(1); // TODO rando button
	}

    @Override
	public boolean getForceMecanum() {
		return mjoystick.getRawButton(2); // TODO rando button
	}

	@Override
	public boolean getToggleWheelState() {
		return mjoystick.getRawButton(6);
	}

	@Override
	public boolean getToggleBrake() {
		return mjoystick.getRawButton(2);
	}
	
	@Override
	public boolean getUseAssist() {
		return mjoystick.getRawButton(4);
	}
  private JoystickButton addButton(Joystick joystick, int buttonNumber, String key) {
		JoystickButton button = new JoystickButton(joystick, buttonNumber);
		//TODO uncomment to see commands on dashboard
		//SmartDashboard.putData(key, button);
		return button;
	}

  public Joystick getJoystick() {
		return mjoystick;
  }
 
}
