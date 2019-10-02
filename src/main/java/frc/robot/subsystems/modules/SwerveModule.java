package frc.robot.subsystems.modules;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.MiniPID;

public abstract class SwerveModule extends Subsystem {

  public SwerveModule(int ID) { this.ID = ID; }

  public MiniPID azimuthController;

  public int ID;
  protected double targetAngle = 5, targetSpeed = 1;
  // public int mod = 1;

  public abstract void setTargetAngle(double angle);
  public abstract void setTargetSpeed(double speed);
  protected abstract void setAzimuthSpeed(double speed);
  protected abstract void setDriveSpeed(double speed);

  public abstract void update();
  public abstract void updateSmartDashboard();

  public abstract double getTargetAngle();
  public abstract double getTargetSpeed();
  protected abstract double getAzimuthError();
  public abstract double getContributingSpeed(double direction);
  public abstract double getAngle();

}