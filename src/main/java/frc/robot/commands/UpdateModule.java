package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.modules.SwerveModule;
import frc.robot.util.SpartanAction;
import frc.robot.util.SpartanReporter;

public class UpdateModule extends SpartanAction {

  private final int ALIGNMENT_TIMEOUT = 1250; // Milliseconds until I start complaining
  private final double ALIGNMENT_TOLERANCE = 2.5; // Tolerance in degrees

  private int moduleIndex;

  // private double lastTargetAngle = 0;
  private double lastGoodAlignment;

  public UpdateModule(int index, Subsystem s) {
    moduleIndex = index;
    requires(s);
  }

  public UpdateModule(int index) {
    moduleIndex = index;
  }

  @Override
  protected void init() {
    Robot.swerveDrive.getModule(moduleIndex).azimuthController.reset();
    lastGoodAlignment = System.currentTimeMillis();
  }

  @Override
  protected void exec() {
    double target = module().getTargetAngle();
    double actual = module().getAngle();
    if (Math.abs(target - actual) <= ALIGNMENT_TOLERANCE) {
      lastGoodAlignment = System.currentTimeMillis();
      SmartDashboard.putBoolean("[" + moduleIndex + "] Module Alignment Warning", true);
      SpartanReporter.getInstance().AddToQueue("Module " + moduleIndex + " is miss aligned past timeout");
    } else {
      if (lastGoodAlignment + ALIGNMENT_TIMEOUT < System.currentTimeMillis()) {
        SmartDashboard.putBoolean("[" + moduleIndex + "] Module Alignment Warning", false);
      }
    }

    //System.out.println("Target: " + target);

    /*if (Math.abs(lastTargetAngle - target) > 3) {
      module().azimuthController.reset();
      lastTargetAngle = target;
    }*/

    double error = module().getAzimuthError();
    double output = module().azimuthController.getOutput(0, error);
    module().setAzimuthSpeed(output);
    module().setDriveSpeed(module().getTargetSpeed());
  }

  @Override
  protected boolean isDone() {
    return false;
  }

  @Override
  protected void end() {
    module().setAzimuthSpeed(0);
    module().setTargetSpeed(0);
  }

  @Override
  protected void interrupt() {
    System.out.println("[" + moduleIndex + "] Interrupted");
    end();
  }

  public SwerveModule module() {
    return Robot.swerveDrive.getModule(moduleIndex);
  }

}