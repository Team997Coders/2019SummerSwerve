package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.subsystems.modules.SwerveModule;

public class UpdateModule extends Command {

  private int moduleIndex;

  private double lastTargetAngle = 0;

  public UpdateModule(int index, Subsystem s) {
    moduleIndex = index;
    System.out.println("JKFSAHBKGDSFHK");
    requires(s);
  }

  @Override
  protected void initialize() {
    Robot.swerveDrive.getModule(moduleIndex).azimuthController.reset();
    System.out.println("Init");
  }

  @Override
  protected void execute() {

    double target = module().getTargetAngle();
    System.out.println("Target: " + target);

    if (Math.abs(lastTargetAngle - target) > 5) {
      module().azimuthController.reset();
    }

    double error = module().getAzimuthError();
    double output = module().azimuthController.getOutput(0, error);
    module().setAzimuthSpeed(output);
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