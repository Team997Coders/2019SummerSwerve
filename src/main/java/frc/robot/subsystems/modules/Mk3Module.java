package frc.robot.subsystems.modules;

import com.ctre.phoenix.CANifier;
import com.ctre.phoenix.CANifier.PWMChannel;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.MiniPID;
import frc.robot.Robot;
import frc.robot.RobotMap;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Mk3Module extends SwerveModule {

  private CANSparkMax azimuth, drive;
  private CANEncoder driveEncoder;
  private CANifier absoluteEncoder;

  private final double ENCODER_MAX = 1022;
  private double encoderZero;

  public Mk3Module(int ID, int azimuthID, int driveID, int encoderID, double encoderZero) {

    super(ID);

    azimuth = new CANSparkMax(azimuthID, MotorType.kBrushless);
    drive = new CANSparkMax(driveID, MotorType.kBrushless);
    driveEncoder = drive.getEncoder();
    absoluteEncoder = new CANifier(encoderID);
    this.encoderZero = encoderZero;

    azimuthController = new MiniPID(RobotMap.Values.AzimuthP, RobotMap.Values.AzimuthI, RobotMap.Values.AzimuthD);
    azimuthController.setOutputLimits(-1, 1);
  }

  @Override
  public void setTargetAngle(double angle) {
    this.targetAngle = limitRange(angle, 0, 360);
  }

  @Override
  public void setTargetSpeed(double speed) {
    targetSpeed = speed;
  }

  @Override
  public double getAzimuthError() {
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

  @Override
  public void setAzimuthSpeed(double speed) {
    azimuth.set(speed);
  }

  @Override
  public void setDriveSpeed(double speed) {
    drive.set(speed);
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

  @Override
  public double getTargetAngle() { return targetAngle; }
  @Override
  public double getTargetSpeed() { return targetSpeed; }

  @Override
  public double getContributingSpeed(double direction) {
    double robotAngle = Robot.swerveDrive.getGyroAngle();
    double relAng = limitRange(robotAngle + getAngle(), 0, 360);
    double porAng = relAng - direction;

    

    return 0;
  }

  @Override
  public void update() { }

  @Override
  protected void initDefaultCommand() {

  }

}