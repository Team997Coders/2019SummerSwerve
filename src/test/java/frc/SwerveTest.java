package frc;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import frc.robot.subsystems.SwerveDrive;
import frc.robot.util.Pair;
import frc.robot.util.SwerveMixerData;

public class SwerveTest {

  @Test
  public void mixerTest() {
    SwerveDrive sd = new SwerveDrive(true);
    SwerveMixerData smd = sd.SwerveMixer(1, 0, 1, false);
    /*double[] angles = new double[]{
      135, 45, 315, 225
    };
    double[] speeds = new double[]{
      1, 1, 1, 1
    };
    if (!TestCombo(smd, angles, speeds)) {
      /*assertTrue("Angle0 => " + smd.getAngles()[0] + "\nAngle1 => " + smd.getAngles()[1]
        + "\nAngle2 => " + smd.getAngles()[2] + "\nAngle3 => " + smd.getAngles()[3]
        + "\nSpeed0 => " + smd.getSpeeds()[0] + "\nSpeed1 => " + smd.getSpeeds()[1]
        + "\nSpeed2 => " + smd.getSpeeds()[2] + "\nSpeed3 => " + smd.getSpeeds()[3], false);
    }*/
    sd.close();

    Pair<Double, Double> v = new Pair<Double,Double>(0.0, 0.0);
    v = add(v, getVector(smd.getAngles()[0], smd.getSpeeds()[0]));
    v = add(v, getVector(smd.getAngles()[1], smd.getSpeeds()[1]));
    v = add(v, getVector(smd.getAngles()[2], smd.getSpeeds()[2]));
    v = add(v, getVector(smd.getAngles()[3], smd.getSpeeds()[3]));

    Pair<Double, Double> avg = new Pair<Double, Double>(v.x / 4, v.y / 4);
    double theta = 180 * (Math.atan2(avg.x, avg.y) / Math.PI);
    assertTrue("\nAngle0 => " + smd.getAngles()[0] + "\nAngle1 => " + smd.getAngles()[1]
    + "\nAngle2 => " + smd.getAngles()[2] + "\nAngle3 => " + smd.getAngles()[3]
    + "\nSpeed0 => " + smd.getSpeeds()[0] + "\nSpeed1 => " + smd.getSpeeds()[1]
    + "\nSpeed2 => " + smd.getSpeeds()[2] + "\nSpeed3 => " + smd.getSpeeds()[3]
    + "\nVector0 => X: " + getVector(smd.getAngles()[0], smd.getSpeeds()[0]).x + ", Y: " + getVector(smd.getAngles()[0], smd.getSpeeds()[0]).y
    + "\nVector1 => X: " + getVector(smd.getAngles()[1], smd.getSpeeds()[1]).x + ", Y: " + getVector(smd.getAngles()[1], smd.getSpeeds()[1]).y
    + "\nVector2 => X: " + getVector(smd.getAngles()[2], smd.getSpeeds()[2]).x + ", Y: " + getVector(smd.getAngles()[2], smd.getSpeeds()[2]).y
    + "\nVector3 => X: " + getVector(smd.getAngles()[3], smd.getSpeeds()[3]).x + ", Y: " + getVector(smd.getAngles()[3], smd.getSpeeds()[3]).y
    + "\nX: " + avg.x + ", Y: " + avg.y + ", Speed: " + Math.sqrt(Math.pow(avg.x, 2) + Math.pow(avg.y, 2)) + ", Theta: " + theta, 1 == 0);
  }

  public Pair<Double, Double> add(Pair<Double, Double> a, Pair<Double, Double> b) {
    return new Pair<Double,Double>(a.x + b.x, a.y + b.y);
  }

  public Pair<Double, Double> getVector(double theta, double vel) {
    double x = vel * Math.sin((theta * Math.PI) / 180);
    double y = vel * Math.cos((theta * Math.PI) / 180);

    return new Pair<Double,Double>(x, y);
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