package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class AUTOAHH extends CommandGroup {

  public AUTOAHH() {
    addSequential(new AutoTestAngle(1, 0));
    addSequential(new WaitCommand(1));
    addSequential(new AutoTestAngle(0, 1));
    addSequential(new WaitCommand(1));
    addSequential(new AutoTestAngle(0, -1));
    addSequential(new WaitCommand(1));
    addSequential(new AutoTestAngle(-1, 0));
    addSequential(new WaitCommand(1));
  }

}