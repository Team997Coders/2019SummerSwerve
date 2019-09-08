package frc.robot.util;

import edu.wpi.first.wpilibj.command.Command;

public abstract class SpartanAction extends Command {

  public boolean hasInit = false;
  public boolean ended = false;

  protected abstract void exec();
  protected abstract boolean isDone();
  protected abstract void interrupt();
  protected abstract void init();

  @Override
  protected void initialize() {
    init();
  }

  @Override
  protected void execute() {
    exec();
  }

  @Override
  protected boolean isFinished() {
    return isDone();
  }

  @Override
  protected void interrupted() {
    interrupt();
    ended = true;
  }

}