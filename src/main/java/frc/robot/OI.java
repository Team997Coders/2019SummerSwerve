package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OI {

  public Joystick gamepad;

  public OI() {
    gamepad = new Joystick(0);
  }

  public double getAxis(int port) {
    return gamepad.getRawAxis(port);
  }

}
