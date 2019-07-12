package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.subsystems.modules.ProtoModule;

/**
 * 0: Front Right 1: Front Left 2: Back Left 3: Back Right
 */
public class SwerveDrive extends Subsystem {

  private ProtoModule[] modules;
  private AHRS navx;

  public SwerveDrive() {
    navx = new AHRS(Port.kUSB1);
    navx.reset();

    modules = new ProtoModule[4];

    modules[0] = new ProtoModule(RobotMap.Ports.FrontRightAzi, RobotMap.Ports.FrontRightDrive);
    modules[0] = new ProtoModule(RobotMap.Ports.FrontLeftAzi, RobotMap.Ports.FrontLeftDrive);
    modules[0] = new ProtoModule(RobotMap.Ports.BackLeftAzi, RobotMap.Ports.BackLeftDrive);
    modules[0] = new ProtoModule(RobotMap.Ports.BackRightAzi, RobotMap.Ports.BackRightDrive);
  }

  public void SwerveMixer(double forward, double strafe, double rotation, boolean isFieldOriented) {
    if (isFieldOriented) {
      double angleRad = Math.toRadians(getGyroAngle());
      double temp = forward * Math.cos(angleRad) + strafe * Math.sin(angleRad);
      strafe = -forward * Math.sin(angleRad) + strafe * Math.cos(angleRad);
      forward = temp;
    }

    double a = strafe - rotation * (RobotMap.Values.WHEELBASE / RobotMap.Values.TRACKWIDTH);
    double b = strafe + rotation * (RobotMap.Values.WHEELBASE / RobotMap.Values.TRACKWIDTH);
    double c = forward - rotation * (RobotMap.Values.TRACKWIDTH / RobotMap.Values.WHEELBASE);
    double d = forward + rotation * (RobotMap.Values.TRACKWIDTH / RobotMap.Values.WHEELBASE);

    double[] angles = new double[] { Math.atan2(b, c) * 180 / Math.PI, Math.atan2(b, d) * 180 / Math.PI,
        Math.atan2(a, d) * 180 / Math.PI, Math.atan2(a, c) * 180 / Math.PI };

    double[] speeds = new double[] { Math.sqrt(b * b + c * c), Math.sqrt(b * b + d * d), Math.sqrt(a * a + d * d),
        Math.sqrt(a * a + c * c) };

    double max = speeds[0];

    for (double speed : speeds) {
      if (speed > max) {
        max = speed;
      }
    }

    for (int i = 0; i < 4; i++) {
      if (Math.abs(forward) > 0.05 || Math.abs(strafe) > 0.05 || Math.abs(rotation) > 0.05) {
        modules[i].setAzimuthAngle(angles[i] + 180);
      } else {
        modules[i].setAzimuthAngle(modules[i].getTargetAngle());
      }
      modules[i].setTargetSpeed(speeds[i]);
    }
  }

  public double getRawGyroAngle() {
    double angle = navx.getAngle();
    angle %= 360;
    if (angle < 0)
      angle += 360;

    return angle;
  }

  @Override
  public void initDefaultCommand() {

  }
}
