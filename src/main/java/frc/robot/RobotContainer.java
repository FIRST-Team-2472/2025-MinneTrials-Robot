package frc.robot;

import frc.robot.Constants.OperatorConstants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.TankDriveSubsystem;
import frc.robot.commands.TankDriveCMD;

public class RobotContainer {
  TankDriveSubsystem tankDriveSubsystem = new TankDriveSubsystem();
  private final Joystick joystickLeft = // left joystick is for up/down
      new Joystick(OperatorConstants.kLeftJoystickPort);
  private final Joystick joystickRight = // right joystick is for turning
      new Joystick(OperatorConstants.kRightJoystickPort);
  public RobotContainer() {
    tankDriveSubsystem.setDefaultCommand(new TankDriveCMD(tankDriveSubsystem,
        () -> joystickLeft.getY(), () -> joystickRight.getX()));
    configureBindings();
  }

  private void configureBindings() {

  }

  public Command getAutonomousCommand() {
    return null;
  }
}
