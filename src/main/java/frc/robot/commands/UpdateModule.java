package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.modules.SwerveModule;

public class UpdateModule extends Command {

  private final int ALIGNMENT_TIMEOUT = 1250; // Milliseconds until I start complaining
  private final double ALIGNMENT_TOLERANCE = 2.5; // Tolerance in degrees

  private int moduleIndex;

  // private double lastTargetAngle = 0;
  private double lastGoodAlignment;

  public UpdateModule(int index, Subsystem s) {
    moduleIndex = index;
    requires(s);
  }

  @Override
  protected void initialize() {
    Robot.swerveDrive.getModule(moduleIndex).azimuthController.reset();
    lastGoodAlignment = System.currentTimeMillis();
  }

  @Override
  protected void execute() {

    module().update();

    /* double target = module().getTargetAngle();
    double actual = module().getAngle();
    if (Math.abs(target - actual) <= ALIGNMENT_TOLERANCE) {
      lastGoodAlignment = System.currentTimeMillis();
      SmartDashboard.putBoolean("[" + moduleIndex + "] Module Alignment Warning", true);
    } else {
      if (lastGoodAlignment + ALIGNMENT_TIMEOUT < System.currentTimeMillis()) {
        SmartDashboard.putBoolean("[" + moduleIndex + "] Module Alignment Warning", false);
      }
    } */

    //System.out.println("Target: " + target);

    /*if (Math.abs(lastTargetAngle - target) > 3) {
      module().azimuthController.reset();
      lastTargetAngle = target;
    }*/

    /* double error = module().getAzimuthError();
    double output = module().azimuthController.getOutput(0, error);
    module().setAzimuthSpeed(output);
    module().setDriveSpeed(module().getTargetSpeed() * module().mod); */
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    // module().setAzimuthSpeed(0);
    // module().setTargetSpeed(0);
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