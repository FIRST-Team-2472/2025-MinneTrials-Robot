package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.OperatorConstants;
import frc.robot.MotorPowerController;
import frc.robot.extras.AccelLimiter;
import frc.robot.subsystems.TankDriveSubsystem;

public class TankDriveCMD extends Command {
  TankDriveSubsystem tankDriveSubsystem;
  Supplier<Double> joystickLeft;
  Supplier<Double> joystickRight;
  MotorPowerController speedController;
  AccelLimiter speedLimiter;

  public TankDriveCMD(TankDriveSubsystem tankDriveSubsystem, Supplier<Double> joystickLeft, Supplier<Double> joystickRight) {
    this.tankDriveSubsystem = tankDriveSubsystem;
    this.joystickLeft = joystickLeft;
    this.joystickRight = joystickRight;
    addRequirements(tankDriveSubsystem);
    speedLimiter = new AccelLimiter(0.02, 0.1);
    speedController = new MotorPowerController(0.33, 0.8, 0, 0.5, 0.67, 0, 1);
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
    SmartDashboard.putNumber("desired speed", drivePower*3.2);
    if (drivePower == 0){
      speedController.calculate(drivePower*3.2, -tankDriveSubsystem.getTrueSpeed());
    } else {
      drivePower = speedController.calculate(drivePower*3.2, -tankDriveSubsystem.getTrueSpeed());
    }

    drivePower = speedLimiter.calculate(drivePower);
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