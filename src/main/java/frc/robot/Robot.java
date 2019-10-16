package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.util.SpartanReporter;
import frc.robot.util.SpartanRunner;

public class Robot extends TimedRobot {

  public static SwerveDrive swerveDrive;
  public static OI m_oi;
  public static SpartanRunner moduleRunner;

  public Watchdog w;

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  @Override
  public void robotInit() {

    moduleRunner = new SpartanRunner(20 /* Custom loop time for runner */);
    swerveDrive = new SwerveDrive();

    m_oi = new OI();
    SmartDashboard.putData("Auto mode", m_chooser);
    
    w = new Watchdog(100, null);
  }

  @Override
  public void robotPeriodic() {
    for (int i = 0; i < swerveDrive.getModules().length; i++) swerveDrive.getModules()[i].updateSmartDashboard();
  }

  @Override
  public void disabledInit() {
    //if (SpartanReporter.hasInstance()) SpartanReporter.getInstance().Close();
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }

    //SpartanReporter.getInstance().AddToQueue("Starting");
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    //SpartanReporter.getInstance().AddToQueue("Starting");
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
  }
}
