package frc.robot;

import frc.robot.Constants.OperatorConstants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {

  public subsystem shooterSubsystem = new subsystem;

  private final XboxController m_driverController =
      new XboxController(OperatorConstants.kDriverControllerPort);


  public RobotContainer() {
    
    shooterSubsystem.setDefaultCommand(new ShooterCMD(motorSubsystem, () -> XboxController.getLeftY()));

    configureBindings();
  }

  private void configureBindings() {

  }

  public Command getAutonomousCommand() {
    return null;
  }
}
