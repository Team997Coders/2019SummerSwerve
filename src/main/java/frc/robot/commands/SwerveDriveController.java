package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.util.SwerveMixerData;

public class SwerveDriveController extends Command {

  public SwerveDriveController() { System.out.println("HJASHJ"); requires(Robot.swerveDrive); }

  public static boolean a = false;

  @Override
  protected void initialize() {
    System.out.println("Initalized");
  }

  @Override
  protected void execute() {
    double forward = -Robot.m_oi.getAxis(RobotMap.Ports.LeftYJoystick);
    double strafe = Robot.m_oi.getAxis(RobotMap.Ports.LeftXJoystick);
    double rotation = Robot.m_oi.getAxis(RobotMap.Ports.RightXJoystick);

    SwerveMixerData smd = Robot.swerveDrive.SwerveMixer(forward, strafe, rotation, false);
    Robot.swerveDrive.setSwerveInput(smd);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    System.out.println("jdhkfjdasfhjfdsf");
  }

  @Override
  protected void interrupted() {
    System.out.println("I HATE THIS");
  }

}