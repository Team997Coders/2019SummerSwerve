package frc.robot.subsystems.modules;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class ProtoModule {

  private TalonSRX azimuth, drive;

  public ProtoModule(int azimuthID, int driveID) {
    azimuth = new TalonSRX(azimuthID);
    drive = new TalonSRX(driveID);
  }

  public void SetAzimuthSpeed(double s) {
    azimuth.set(ControlMode.PercentOutput, s);
  }

  public void SetDriveSpeed(double s) {
    drive.set(ControlMode.PercentOutput, s);
  }

}
