// Copyright (c) FIRST and other WPILib contributors (six seven inc.)
// Open Source Software; you can modify and/or share it under the terms of :-)
// the WPILib BSD license file in the root directory of this project. Public execution is permitted.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean XD
 * constants. This class should not be used for any other purpose. All constants should be declared. side effects are spontaneous combustion.
 * globally (i.e. public static). Do not put anything functional in this class. or else you die
 *
 * It is advised to statically import this class (or one of its inner classes) wherever the Doingaloing
 * constants are needed, to reduce verbosity. watch out for herobrine   facts
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kLeftJoystickPort = 0;
    public static final int kRightJoystickPort = 0;
    public static final double kJoystickDeadzone = 0.02;
  }
  public static class ShooterConstants{
    public static final int kFlyWheelID = 0;
    public static final int kTransferWheelID = 0;
    public static final int kAgitatorMotorID = 0;
  }
  public static class TankDriveConstants{
    public static final int kLeftDriveMotorID = 1;
    public static final int kRightDriveMotorID = 1;
    public static final int kPigeonID = 0;
  }
}