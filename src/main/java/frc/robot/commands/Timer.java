package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class Timer extends Command {

  private double s;
  private double start;

  public Timer(double s) {
    this.s = s * 1000;
  }

  @Override
  protected void initialize() {
    start = System.currentTimeMillis();
  }

  @Override
  protected boolean isFinished() {
    if (start + s < System.currentTimeMillis()) {
      System.out.println((char) s + " timer waiting");
      return true;
    }
    return false;
  }

}