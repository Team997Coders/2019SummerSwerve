package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
// import frc.robot.RobotMap;

/**
 * @deprecated Just for verifiying that the module spins
 */
public class TestBedController extends Command {

  public TestBedController() { requires(Robot.swerveDrive); }

  @Override
  protected void execute() {
    // double drive = -Robot.m_oi.getAxis(RobotMap.Ports.LeftYJoystick);
    // double spin = Robot.m_oi.getAxis(RobotMap.Ports.RightXJoystick);

    // Robot.swerveDrive.getModule(0).setAzimuthSpeed(spin);
    // Robot.swerveDrive.getModule(0).setDriveSpeed(drive);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

}