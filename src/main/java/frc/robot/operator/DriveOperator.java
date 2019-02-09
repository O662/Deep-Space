package frc.robot.operator;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public abstract class DriveOperator implements IDriveOperator {

    protected JoystickButton addButton(Joystick joystick, int buttonNumber) {
        return addButton(joystick, buttonNumber, null);
    }

    protected JoystickButton addButton(Joystick joystick, int buttonNumber, String key) {
        JoystickButton button = new JoystickButton(joystick, buttonNumber);
        //TODO uncomment to see commands on dashboard
        //if (key != null && !key.isEmpty()) {
        //    SmartDashboard.putData(key, button);
        //}
        return button;
    }
}
