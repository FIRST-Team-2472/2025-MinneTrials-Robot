package frc.robot;

import frc.robot.Constants.OperatorConstants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ShooterCMD;
import frc.robot.subsystems.ShooterSubsystem;

public class RobotContainer {

  public ShooterSubsystem shooterSubsystem = new ShooterSubsystem();

  private final XboxController xboxController =
      new XboxController(OperatorConstants.kDriverControllerPort);


  public RobotContainer() {
    
    shooterSubsystem.setDefaultCommand(new ShooterCMD(shooterSubsystem,
    () -> xboxController.getAButton()));

    configureBindings();
  }

  private void configureBindings() {

  }

  public Command getAutonomousCommand() {
    return null;
  }
}
