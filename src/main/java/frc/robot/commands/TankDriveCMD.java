package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.TankDriveSubsystem;

public class TankDriveCMD extends Command {
  TankDriveSubsystem tankDriveSubsystem;
  Supplier<Double> joystickLeft;
  Supplier<Double> joystickRight;

  public TankDriveCMD(TankDriveSubsystem tankDriveSubsystem, Supplier<Double> joystickLeft, Supplier<Double> joystickRight) {
    this.tankDriveSubsystem = tankDriveSubsystem;
    this.joystickLeft = joystickLeft;
    this.joystickRight = joystickRight;
    addRequirements(tankDriveSubsystem);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    double drivePower = joystickLeft.get();
    double turnPercent = joystickRight.get();

    if (Math.abs(drivePower) < OperatorConstants.kJoystickDeadzone) {
      drivePower = 0;
    }
    if (Math.abs(turnPercent) < OperatorConstants.kJoystickDeadzone) {
      turnPercent = 0;
    }

    tankDriveSubsystem.arcadeDrive(drivePower, turnPercent);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}