package frc.robot.subsystems.modules;

import com.ctre.phoenix.CANifier;
import com.ctre.phoenix.CANifier.PWMChannel;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.MiniPID;
import frc.robot.RobotMap;

public class ProtoModule {

  private TalonSRX azimuth, drive;
  private CANifier absoluteEncoder;

  private double targetAngle = Double.MAX_VALUE;
  private double targetSpeed = 0;
  private final double ENCODER_MAX = 1022;
  private double encoderZero;

  public MiniPID azimuthController;

  public ProtoModule(int azimuthID, int driveID, int encoderID, double encoderZero) {
    azimuth = new TalonSRX(azimuthID);
    drive = new TalonSRX(driveID);
    absoluteEncoder = new CANifier(encoderID);
    this.encoderZero = encoderZero;

    azimuthController = new MiniPID(RobotMap.Values.AzimuthP, RobotMap.Values.AzimuthI, RobotMap.Values.AzimuthD);
    azimuthController.setOutputLimits(-1, 1);
  }

  public void setTargetAngle(double targetAngle) {
    this.targetAngle = limitRange(targetAngle, 0, 360);
  }

  public void setTargetSpeed(double a) {
    targetSpeed = a;
  }
  public double getError() {
    if (targetAngle == Double.MAX_VALUE) {
      return 0;
    }

    double current = getAngle();
    double error = targetAngle - current;
    if (Math.abs(error) > 180) {
      int sign = (int)(error / Math.abs(error));
      error += 360 * -sign;
      return error;
    } else {
      return error;
    }
  }

  public void setAzimuthSpeed(double s) {
    azimuth.set(ControlMode.PercentOutput, s);
  }

  public void setDriveSpeed(double s) {
    drive.set(ControlMode.PercentOutput, s);
  }

  public double getRawEncoder() {
    double[] a = new double[2];
    absoluteEncoder.getPWMInput(PWMChannel.PWMChannel0, a);
    SmartDashboard.putNumber("Duty Cycle", a[1]);
    return a[0];
  }

  public double getEncoderParsed() {
    double a = getRawEncoder() - encoderZero;
    return limitRange(a, 0, ENCODER_MAX);
  }

  public double getAngle() {
    return encoderToAngle(getEncoderParsed(), true);
  }

  public double limitRange(double a, double min, double max) {
    while (a < min) a += max;
    while (a > max) a -= max;
    return a;
  }

  public double encoderToAngle(double val, boolean isParsed) {
    if (!isParsed) {
      val = val - encoderZero;
      val = limitRange(val, 0, ENCODER_MAX);
    }

    double mod = val / ENCODER_MAX;
    return 360 * mod;
  }

  public double angleToEncoder(double val) {
    return (ENCODER_MAX * val) / 360;
  }

  public double getTargetAngle() { return targetAngle; }
  public double getTargetSpeed() { return targetSpeed; }

}
