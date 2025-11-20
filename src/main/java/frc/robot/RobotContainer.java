package frc.robot;

import frc.robot.Constants.OperatorConstants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.TankDriveSubsystem;
import frc.robot.commands.ShooterCMD;
import frc.robot.commands.TankDriveCMD;
import frc.robot.subsystems.ShooterSubsystem;


public class RobotContainer {

  private final XboxController XboxController =
      new XboxController(OperatorConstants.kXboxControllerPort);
  TankDriveSubsystem tankDriveSubsystem = new TankDriveSubsystem(); 
  ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
   private final Joystick joystickLeft = // left joystick is for up/down
      new Joystick(OperatorConstants.kLeftJoystickPort);
      private final Joystick joystickRight = // right joystick is for turning
      new Joystick(OperatorConstants.kRightJoystickPort);


      public RobotContainer() {
        tankDriveSubsystem.setDefaultCommand(new TankDriveCMD(tankDriveSubsystem,
            () -> joystickLeft.getY(), () -> joystickRight.getX()));
        shooterSubsystem.setDefaultCommand(new ShooterCMD(shooterSubsystem,
            () -> XboxController.getLeftTriggerAxis() > 0.5, () -> XboxController.getRightTriggerAxis() > 0.5));
        configureBindings();
      }

  private void configureBindings() {

  }

  public Command getAutonomousCommand() {
    return null;
  }
}