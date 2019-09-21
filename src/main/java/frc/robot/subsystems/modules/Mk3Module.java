package frc.robot.subsystems.modules;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.UpdateModule;
import frc.robot.util.Pair;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Mk3Module extends SwerveModule {

  private final int ALIGNMENT_TIMEOUT = 1250; // Milliseconds until I start complaining
  private final double ALIGNMENT_TOLERANCE = 2.5;
  private double lastGoodAlignment;

  private CANSparkMax azimuth, drive;
  private CANEncoder azimuthInternalEncoder, driveInternalEncoder;
  private AnalogInput azimuthEncoder;
  private CANPIDController azimuthController;

  private final double ENCODER_MAX = 5;
  private double encoderZero;

  public Mk3Module(int ID, int azimuthID, int driveID, int encoderID, double encoderZero) {
    
    super(ID);

    azimuth = new CANSparkMax(azimuthID, MotorType.kBrushless);
    azimuthInternalEncoder = azimuth.getEncoder();
    azimuthInternalEncoder.setPosition(0);
    drive = new CANSparkMax(driveID, MotorType.kBrushless);
    driveInternalEncoder = drive.getEncoder();
    driveInternalEncoder.setPosition(0);
    azimuthEncoder = new AnalogInput(encoderID);
    this.encoderZero = encoderZero;

    azimuthController = azimuth.getPIDController();
    azimuthController.setP(RobotMap.Values.Mk3P);
    azimuthController.setP(RobotMap.Values.Mk3I);
    azimuthController.setP(RobotMap.Values.Mk3D);
    azimuthController.setOutputRange(-1, 1);
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
      drive.setInverted(true); // Pretty sure this should be true and line 54 should be false. Test this later
    } else {
      drive.setInverted(false);
    }

    this.targetAngle = p;
  }

  @Override
  public void setTargetSpeed(double speed) {
    targetSpeed = speed;
  }

  @Override
  public void setAzimuthAngle(double angle) {
    double target = targetAngle;
    double actual = getAngle();
    if (Math.abs(target - actual) <= ALIGNMENT_TOLERANCE) {
      lastGoodAlignment = System.currentTimeMillis();
      SmartDashboard.putBoolean("[" + ID + "] Module Alignment Warning", true);
    } else {
      if (lastGoodAlignment + ALIGNMENT_TIMEOUT < System.currentTimeMillis()) {
        SmartDashboard.putBoolean("[" + ID + "] Module Alignment Warning", false);
      }
    }

    double error = getAzimuthError();
    azimuthInternalEncoder.setPosition(0);
    azimuthController.setReference(error, ControlType.kPosition);
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
    azimuth.set(speed);
  }

  @Override
  public void setDriveSpeed(double speed) {
    drive.set(speed);
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

  @Override
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
    SmartDashboard.putNumber("[" + ID + "] Module Target Speed", getTargetSpeed());
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
  public void resetAzimuthController() {
    azimuthController.setIAccum(0);
  }

  @Override
  public Pair<Double, Double> getVector() {
    // TO-DO: Take robot orientation into account
    double theta = getAngle() + Robot.swerveDrive.getGyroAngle();
    theta = limitRange(theta, 0, 360);
    double speed = driveInternalEncoder.getVelocity();

    double x = speed * Math.sin(theta);
    double y = speed * Math.cos(theta);

    if (theta > 90 && theta <= 180) {
      y = -y;
    } else if (theta > 180 && theta <= 270) {
      y = -y;
      x = -x;
    } else if (theta > 270) {
      x = -x;
    }

    Pair<Double, Double> v = new Pair<Double,Double>(x, y);

    return v;
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new UpdateModule(0, this));
  }

}