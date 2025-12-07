package frc.robot;

import frc.robot.Constants.OperatorConstants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.DriveForwardCMD;
import frc.robot.commands.PathfindingCommand;
import frc.robot.commands.AutoShootCMD;
import frc.robot.commands.ShooterCMD;
import frc.robot.commands.TankDriveCMD;
import frc.robot.commands.TurnCMD;
import frc.robot.subsystems.TankDriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class RobotContainer {
  private final String driveForward = "Drive forward",
      autoShoot = "Shoot",
      pathFindingCommand = "Drive to kettle and shoot",
      turnAuto = "Turn to 270 degrees";

  private final String defaultAuto = "Default Auto";

  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  TankDriveSubsystem tankDriveSubsystem = new TankDriveSubsystem();
  private final XboxController XboxController = new XboxController(OperatorConstants.kXboxControllerPort);
  ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
  DriveForwardCMD driveForwardCMD = new DriveForwardCMD(tankDriveSubsystem);

  private final Joystick joystickLeft = // left joystick is for up/down
      new Joystick(OperatorConstants.kLeftJoystickPort);
  private final Joystick joystickRight = // right joystick is for turning
      new Joystick(OperatorConstants.kRightJoystickPort);

  public RobotContainer() {

    m_chooser.addOption(driveForward, driveForward);
    m_chooser.addOption(autoShoot, autoShoot);
    m_chooser.addOption(pathFindingCommand, pathFindingCommand);
    m_chooser.addOption(turnAuto, turnAuto);
    m_chooser.addOption(defaultAuto, defaultAuto);

    ShuffleboardTab driverBoard = Shuffleboard.getTab("Driver Board");
    driverBoard.add("Auto choices", m_chooser).withWidget(BuiltInWidgets.kComboBoxChooser);

    tankDriveSubsystem.setDefaultCommand(new TankDriveCMD(tankDriveSubsystem,
        () -> joystickLeft.getY() * 0.75, () -> joystickRight.getX() * 0.55));
    shooterSubsystem.setDefaultCommand(new ShooterCMD(shooterSubsystem,
        () -> XboxController.getLeftTriggerAxis() > 0.5, () -> XboxController.getRightTriggerAxis() > 0.5));
    configureBindings();
  }

  private void configureBindings() {

  }

  public Command getAutonomousCommand() {

    switch (m_chooser.getSelected()) {
      case driveForward:
        return new DriveForwardCMD(tankDriveSubsystem);
      case autoShoot:
        return new AutoShootCMD(shooterSubsystem, tankDriveSubsystem);
      case pathFindingCommand:
        return new SequentialCommandGroup(new PathfindingCommand(tankDriveSubsystem),
            new TurnCMD(tankDriveSubsystem),
            new AutoShootCMD(shooterSubsystem, tankDriveSubsystem));
      case turnAuto:
        return new TurnCMD(tankDriveSubsystem);
      default:
        System.err.println("Auto selection null or not recognized");
        break;
    }
    return null;
  }
}