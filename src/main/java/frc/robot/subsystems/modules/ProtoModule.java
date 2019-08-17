package frc.robot.subsystems.modules;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.MiniPID;
import frc.robot.RobotMap;
import frc.robot.commands.UpdateModule;

public class ProtoModule extends SwerveModule {

  private VictorSPX azimuth, drive;
  private AnalogInput azimuthEncoder;

  private final double ENCODER_MAX = 5;
  private double encoderZero;

  public ProtoModule(int ID, int azimuthID, int driveID, int encoderID, double encoderZero) {

    super(ID);

    azimuth = new VictorSPX(azimuthID);
    drive = new VictorSPX(driveID);
    azimuthEncoder = new AnalogInput(encoderID);
    this.encoderZero = encoderZero;

    azimuthController = new MiniPID(RobotMap.Values.AzimuthP, RobotMap.Values.AzimuthI, RobotMap.Values.AzimuthD);
    azimuthController.setOutputLimits(-1, 1);
  }

  @Override
  public void setTargetAngle(double angle) {
    double p = limitRange(angle, 0, 360);
    double current = getAngle();

    double delta = current - p;

    if (delta > 180) {
      p += 360;
    } else if (delta < -180) {
      p -= 360;
    }

    delta = current - p;
    if (delta > 90 || delta < -90) {
      if (delta > 90)
        p += 180;
      else if (delta < -90)
        p -= 180;
      drive.setInverted(false); // Pretty sure this should be true and line 54 should be false. Test this later
    } else {
      drive.setInverted(true);
    }

    this.targetAngle = p;
  }

  @Override
  public void setTargetSpeed(double speed) {
    targetSpeed = speed;
  }

  @Override
  public double getAzimuthError() {
    double current = getAngle();
    double error = targetAngle - current;
    if (Math.abs(error) > 180) {
      int sign = (int) (error / Math.abs(error));
      error += 180 * -sign;
      return error;
    } else {
      return error;
    }
  }

  @Override
  public void setAzimuthSpeed(double speed) {
    azimuth.set(ControlMode.PercentOutput, speed);
  }

  @Override
  public void setDriveSpeed(double speed) {
    drive.set(ControlMode.PercentOutput, speed);
  }

  public double getRawEncoder() {
    return azimuthEncoder.getVoltage();
  }

  public double getEncoderParsed() {
    double a = getRawEncoder() - encoderZero;
    return limitRange(a, 0, ENCODER_MAX);
  }

  @Override
  public double getContributingSpeed(double direction) {
    return 0;
  }

  public double getAngle() {
    return encoderToAngle(getEncoderParsed(), true);
  }

  public double limitRange(double a, double min, double max) {
    while (a < min)
      a += max;
    while (a > max)
      a -= max;
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

  public void updateSmartDashboard() {
    SmartDashboard.putNumber("[" + ID + "] Module Encoder", getRawEncoder());
    SmartDashboard.putNumber("[" + ID + "] Module Angle", getAngle());
    SmartDashboard.putNumber("[" + ID + "] Module Target Angle", getTargetAngle());
  }

  @Override
  public void update() {
    updateSmartDashboard();
  }

  @Override
  public double getTargetAngle() {
    return targetAngle;
  }

  @Override
  public double getTargetSpeed() {
    return targetSpeed;
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new UpdateModule(0, this));
  }

}
