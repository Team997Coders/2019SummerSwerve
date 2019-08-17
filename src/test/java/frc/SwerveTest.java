package frc;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import frc.robot.subsystems.SwerveDrive;
import frc.robot.util.SwerveMixerData;

public class SwerveTest {

  @Test
  public void mixerTest() {
    SwerveDrive sd = new SwerveDrive(true);
    SwerveMixerData smd = sd.SwerveMixer(1, 1, 0, false);
    double[] angles = {
      45, 45, 45, 45
    };
    double[] speeds = {
      1, 1, 1, 1
    };
    if (!TestCombo(smd, angles, speeds)) {
      assertTrue("Angle0 => " + smd.getAngles()[0] + "\nAngle1 => " + smd.getAngles()[1]
        + "\nAngle2 => " + smd.getAngles()[2] + "\nAngle3 => " + smd.getAngles()[3]
        + "\nSpeed0 => " + smd.getSpeeds()[0] + "\nSpeed1 => " + smd.getSpeeds()[1]
        + "\nSpeed2 => " + smd.getSpeeds()[0] + "\nSpeed3 => " + smd.getSpeeds()[3], false);
    }
    smd = sd.SwerveMixer(0, 0, 1, false);
    angles = new double[]{
      135, 45, 315, 225
    };
    speeds = new double[]{
      1, 1, 1, 1
    };
    if (!TestCombo(smd, angles, speeds)) {
      assertTrue("Angle0 => " + smd.getAngles()[0] + "\nAngle1 => " + smd.getAngles()[1]
        + "\nAngle2 => " + smd.getAngles()[2] + "\nAngle3 => " + smd.getAngles()[3]
        + "\nSpeed0 => " + smd.getSpeeds()[0] + "\nSpeed1 => " + smd.getSpeeds()[1]
        + "\nSpeed2 => " + smd.getSpeeds()[0] + "\nSpeed3 => " + smd.getSpeeds()[3], false);
    }
  }

  public boolean TestCombo(SwerveMixerData smd, double[] angles, double[] speeds) {
    for (int i = 0; i < 4; i++) {
      if (smd.getAngles()[i] != angles[i]) return false;
    }

    for (int i = 0; i < 4; i++) {
      if (smd.getSpeeds()[i] != speeds[i]) return false;
    }

    return true;
  }

}