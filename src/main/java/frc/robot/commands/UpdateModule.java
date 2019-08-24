package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.subsystems.modules.SwerveModule;

public class UpdateModule extends Command {

  private int moduleIndex;

  public UpdateModule(int index, Subsystem s) {
    moduleIndex = index;
    requires(s);
  }

  @Override
  protected void initialize() {
    module().resetAzimuthController();
  }

  @Override
  protected void execute() {
    module().setAzimuthAngle(module().getTargetAngle());
    module().setDriveSpeed(module().getTargetSpeed() * module().mod);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    module().setAzimuthSpeed(0);
    module().setTargetSpeed(0);
  }

  @Override
  protected void interrupted() {
    System.out.println("Interrupted");
    end();
  }

  public SwerveModule module() {
    return Robot.swerveDrive.getModule(moduleIndex);
  }

}