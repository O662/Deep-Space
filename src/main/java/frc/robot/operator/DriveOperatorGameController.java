package frc.robot.operator;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class DriveOperatorGameController extends DriveOperator implements IDriveOperator {

    private final Joystick joystick;

    private final int bToggleDriveMode = 6;
    private final int bToggleDriveState = 5;
    private final int bMoveArmUp = 9;
    private final int bMoveArmDown = 4;
    private final int bRoller = 8;
    private final int bIntakeRollerForward = 10;
    private final int bIntakeRollerBackward = 7;
    private final int bPushForward = 2;
    private final int bPushBack = 1;

    public DriveOperatorGameController(Joystick stick) {
        this.joystick = stick;
    }

    @Override
    public double getDriveForward() {
        return joystick.getRawAxis(5);
    }

    @Override
    public double getDriveForward2() {
        return joystick.getRawAxis(1);
    }

    @Override
    public double getDriveSideway() {
        return joystick.getRawAxis(4);
    }

    @Override
    public double getDriveRotation() {
        return joystick.getRawAxis(0);
    }

    private Joystick getJoystick() {
        return joystick;
    }

    @Override
    public JoystickButton getToggleDriveModeButton() {
        return addButton(getJoystick(), bToggleDriveMode, "Toggle Drive Mode");
    }

    @Override
    public JoystickButton getToggleDriveStateButton() {
        return addButton(getJoystick(), bToggleDriveState, "Toggle Drive State");
    }

    @Override
    public JoystickButton getMoveArmUpButton() {
        return addButton(getJoystick(), bMoveArmUp, "Move Arm Up");
    }

    @Override
    public JoystickButton getMoveArmDownButton() {
        return addButton(getJoystick(), bMoveArmDown, "Move Arm Down");
    }

    @Override
    public JoystickButton getMoveRollerButton() {
        return addButton(getJoystick(), bRoller, "Move Roller");
    }

    @Override
    public JoystickButton getMoveIntakeRollerForwardButton() {
        return addButton(getJoystick(), bIntakeRollerForward, "Move Intake Roller Forward");
    }

    @Override
    public JoystickButton getMoveIntakeRollerBackwardButton() {
        return addButton(getJoystick(), bIntakeRollerBackward, "Move Intake Roller Backward");
    }

    @Override
    public JoystickButton getPushForwardButton() {
        return addButton(getJoystick(), bPushForward, "Push Forward");
    }

    @Override
    public JoystickButton getPushBackwardButton() {
        return addButton(getJoystick(), bPushBack, "Push Back");
    }

}
