package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class SwerveDriveController extends Command {

  public SwerveDriveController() { }

  @Override
  protected void execute() {
    double forward = -Robot.m_oi.getAxis(RobotMap.Ports.LeftYJoystick);
    double strafe = Robot.m_oi.getAxis(RobotMap.Ports.LeftXJoystick);
    double rotation = Robot.m_oi.getAxis(RobotMap.Ports.RightXJoystick);

    Robot.swerveDrive.SwerveMixer(forward, strafe, rotation, true);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

}