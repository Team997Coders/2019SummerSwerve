package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class AutoTestAngle extends Command {

  private double f, s;

  public AutoTestAngle(double f, double s) {
    this.f = f;
    this.s = s;
    requires(Robot.swerveDrive);
  }

  @Override
  protected void execute() {
    Robot.swerveDrive.SwerveMixer(f, s, 0, false);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

}