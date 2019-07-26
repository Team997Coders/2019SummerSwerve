package frc.robot;

public class RobotMap {

  public class Ports {

    public static final int
      FrontRightAzi = 1,
      FrontLeftAzi = 3,
      BackLeftAzi = 5,
      BackRightAzi = 7,

      FrontRightDrive = 2,
      FrontLeftDrive = 4,
      BackLeftDrive = 6,
      BackRightDrive = 8,

      FrontRightEncoder = 0,
      FrontLeftEncoder = 1,
      BackLeftEncoder = 2,
      BackRightEncoder = 3,

      FrontRightZero = 0,
      FrontLeftZero = 0,
      BackLeftZero = 0,
      BackRightZero = 0,

      LeftYJoystick = 1,
      LeftXJoystick = 0,
      RightXJoystick = 4;
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
