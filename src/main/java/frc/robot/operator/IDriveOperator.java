package frc.robot.operator;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * Interface for driver operator controls.
 */
public interface IDriveOperator {

    public double getDriveForward();

    public double getDriveForward2();

    public double getDriveSideway();

    public double getDriveRotation();

    /**
     * skid steer state
     * 
     * @return
     */
    public JoystickButton getToggleDriveModeButton();

    /**
     * mecanum state
     * 
     * @return
     */
    public JoystickButton getToggleDriveStateButton();

    public JoystickButton getMoveArmUpButton();

    public JoystickButton getMoveArmDownButton();

    public JoystickButton getMoveRollerButton();

    public JoystickButton getMoveIntakeRollerForwardButton();

    public JoystickButton getMoveIntakeRollerBackwardButton();

    public JoystickButton getPushForwardButton();

    public JoystickButton getPushBackwardButton();

}
