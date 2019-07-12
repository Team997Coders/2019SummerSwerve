package frc.robot;

public class RobotMap {

  public class Ports {

    public static final int
      FrontRightAzi = 0,
      FrontLeftAzi = 1,
      BackLeftAzi = 2,
      BackRightAzi = 3,

      FrontRightDrive = 0,
      FrontLeftDrive = 1,
      BackLeftDrive = 2,
      BackRightDrive = 3,

      FrontRightEncoder = 0,
      FrontLeftEncoder = 1,
      BackLeftEncoder = 2,
      BackRightEncoder = 3,

      FrontRightZero = 0,
      FrontLeftZero = 0,
      BackLeftZero = 0,
      BackRightZero = 0,

      LeftYJoystick = 0,
      LeftXJoystick = 0,
      RightXJoystick = 0;
  }

  public class Values {

    public static final double
      WHEELBASE = 1,
      TRACKWIDTH = 1,

      AzimuthP = 0.006,
      AzimuthI = 0,
      AzimuthD = 0;
  }

}
