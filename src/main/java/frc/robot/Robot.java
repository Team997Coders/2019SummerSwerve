package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.SwerveDriveController;
import frc.robot.subsystems.SwerveDrive;

public class Robot extends TimedRobot {

  public static SwerveDrive swerveDrive;
  public static OI m_oi;

  public Watchdog w;

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  @Override
  public void robotInit() {
    swerveDrive = new SwerveDrive();
    m_oi = new OI();
    SmartDashboard.putData("Auto mode", m_chooser);
    
    w = new Watchdog(100, null);
  }

  @Override
  public void robotPeriodic() {
    swerveDrive.getModule(0).update();
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    //Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
   
    //if (m_autonomousCommand != null) {
      //m_autonomousCommand.cancel();
    //}
    //while (true) {
      //System.out.println("sketch");
      //teleopPeriodic();
    //}
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    System.out.println("Uhhhhhh good ?");
  }

  @Override
  public void testPeriodic() {
  }
}
