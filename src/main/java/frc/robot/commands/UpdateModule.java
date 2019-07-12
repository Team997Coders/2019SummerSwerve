package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.modules.SwerveModule;

public class UpdateModule extends Command {

  private int moduleIndex;

  private double lastTargetAngle = 0;

  public UpdateModule(int index) {
    moduleIndex = index;
  }

  @Override
  protected void initialize() {
    Robot.swerveDrive.getModule(moduleIndex).azimuthController.reset();
  }

  @Override
  protected void execute() {

    double target = module().getTargetAngle();

    if (Math.abs(lastTargetAngle - target) > 5) {
      module().azimuthController.reset();
    }

    double error = module().getAzimuthError();
    double output = module().azimuthController.getOutput(0, error);
    module().setAzimuthSpeed(output);
    module().setDriveSpeed(module().getTargetSpeed());
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
    end();
  }

  public SwerveModule module() {
    return Robot.swerveDrive.getModule(moduleIndex);
  }

}