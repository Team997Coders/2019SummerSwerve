package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Subsystem;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.modules.SwerveModule;
import frc.robot.util.SpartanAction;

public class UpdateModule extends SpartanAction {

  // private final int ALIGNMENT_TIMEOUT = 1250; // Milliseconds until I start complaining
  // private final double ALIGNMENT_TOLERANCE = 2.5; // Tolerance in degrees

  private int moduleIndex;

  // private double lastTargetAngle = 0;
  // private double lastGoodAlignment;

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
    // lastGoodAlignment = System.currentTimeMillis();
  }

  @Override
  protected void exec() {
    module().update();
  }

  @Override
  protected boolean isDone() {
    return false;
  }

  @Override
  protected void end() {
    // module().setAzimuthSpeed(0);
    // module().setTargetSpeed(0);
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