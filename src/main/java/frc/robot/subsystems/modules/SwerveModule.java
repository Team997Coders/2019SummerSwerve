package frc.robot.subsystems.modules;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.MiniPID;

public abstract class SwerveModule extends Subsystem {

  public SwerveModule(int ID) { this.ID = ID; }

  public MiniPID azimuthController;

  public int ID;
  public double targetAngle = 420, targetSpeed = 0;
  public int mod = 1;

  public abstract void setTargetAngle(double angle);
  public abstract void setTargetSpeed(double speed);
  public abstract void setAzimuthSpeed(double speed);
  public abstract void setDriveSpeed(double speed);

  public abstract double getTargetAngle();
  public abstract double getTargetSpeed();
  public abstract double getAzimuthError();
  public abstract double getContributingSpeed(double direction);

  public abstract void update();

}