/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.lib.drivers.IDriveControlBoard;
import frc.robot.OI;
import frc.robot.IControlBoard;

/**
 * Add your docs here.
 */
public class ControlBoard implements IControlBoard {
    private static ControlBoard mInstance = null;

       public static ControlBoard getInstance() {
           if (mInstance == null) {
               mInstance = new ControlBoard();
           }
           return mInstance;
       }

       private IDriveControlBoard mDriveControlBoard;
      // private IAssisstantControlBoard mAssisstantControlBoard;

       private ControlBoard() {
           if (RobotMap.kUseGamepadForDriving) {
               mDriveControlBoard = OI.getInstance();
           } else {
               //mDriveControlBoard = MainDriveControlBoard.getInstance();
           }

           if (RobotMap.kUseGamepadForButtons) {
               //mAssisstantControlBoard = GamepadAssisstantControlBoard.getInstance();
           } else {
               //mAssisstantControlBoard = MainAssisstantBoard.getInstance();
           }
       }

  // @Override
   public double getDriveForward() {
       return mapDeadband(mDriveControlBoard.getDriveForward());
   }
   

  // @Override
   public double getDriveForward2() {
       return mapDeadband(mDriveControlBoard.getDriveForward2());
   }

//   @Override
   public double getDriveSideway() {
       return mapDeadband(mDriveControlBoard.getDriveSideway());
   }

//   @Override
   public double getDriveRotation() {
       return mapDeadband(mDriveControlBoard.getDriveRotation());
   }

//   @Override
   public boolean getToggleDriveMode() {
       return mDriveControlBoard.getToggleDriveMode();
   }

  // @Override
   public boolean getToggleWheelState() {
       return mDriveControlBoard.getToggleWheelState();
   }

//   @Override
   public boolean getForceSkidSteer() {
       return mDriveControlBoard.getForceSkidSteer();
   }

 //  @Override
   public boolean getForceMecanum() {
       return mDriveControlBoard.getForceMecanum();
   }
   
   private double mapDeadband(double value) {
       double deadband = (RobotMap.kUseGamepadForDriving ? RobotMap.kGamepadDeadband : RobotMap.kJoystickDeadband);
       double absvalue = Math.abs(value);
       if(absvalue <= deadband) {
           return 0;
       } else {
           return ((absvalue-deadband)*(absvalue/value)) / (1-deadband);
       }
   }
   
   public enum GamepadControlBoardType{
       XBOX,
       PLAYSTATION
   }

   public enum JoystickControlBoardType{
       ATTACK3,
       PRO_3D
   }

  // @Override
   public boolean getToggleBrake() {
       return mDriveControlBoard.getToggleBrake();
   }
   
  // @Override 
   public boolean getUseAssist() {
       return mDriveControlBoard.getUseAssist();
   }
}

