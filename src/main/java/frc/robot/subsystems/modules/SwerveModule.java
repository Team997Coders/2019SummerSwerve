package frc.robot.subsystems.modules;

import frc.robot.MiniPID;

public abstract class SwerveModule {

  public MiniPID azimuthController;

  public double targetAngle = Double.MAX_VALUE, targetSpeed = 0;

  public abstract void setTargetAngle(double angle);
  public abstract void setTargetSpeed(double speed);
  public abstract void setAzimuthSpeed(double speed);
  public abstract void setDriveSpeed(double speed);

  public abstract double getTargetAngle();
  public abstract double getTargetSpeed();
  public abstract double getAzimuthError();

}