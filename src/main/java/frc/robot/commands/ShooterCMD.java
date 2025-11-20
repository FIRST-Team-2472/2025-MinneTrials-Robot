package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.MotorPowerController;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterCMD extends Command {
  ShooterSubsystem shooterSubsystem;
  MotorPowerController flywheelMotorPowerController;
  MotorPowerController indexMotorPowerController;
  Supplier<Boolean> leftTrigger;
  Supplier<Boolean> rightTrigger;
  double AgitatorPower = 0.2;

  Timer directionSwitchTimer = new Timer();

  public ShooterCMD(ShooterSubsystem shooterSubsystem, Supplier<Boolean> leftTrigger, Supplier<Boolean> rightTrigger) {
    this.shooterSubsystem = shooterSubsystem;
    this.leftTrigger = leftTrigger;
    this.rightTrigger = rightTrigger;
    addRequirements(shooterSubsystem);
    flywheelMotorPowerController = new MotorPowerController(0.0014, 0.5, 0, 0.5, 0.67, 0, 100);
    indexMotorPowerController = new MotorPowerController(0.01, 0.1, 0.1, 0.5, 0.67, 0, 34.9);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    shooterSubsystem.SetAgitatorPower(AgitatorPower);

    if (directionSwitchTimer.hasElapsed(0.5)) {
      AgitatorPower *= -1;
      directionSwitchTimer.reset();
    }
    
    if (leftTrigger.get()) {
      shooterSubsystem.SetflyWheelPower(flywheelMotorPowerController.calculate(1400, shooterSubsystem.getFlyWheelRPM()));
    } else {
      shooterSubsystem.SetflyWheelPower(flywheelMotorPowerController.calculate(0, shooterSubsystem.getFlyWheelRPM()));
    }

    if (rightTrigger.get()) {
      shooterSubsystem.SetTransferWheelPower(indexMotorPowerController.calculate(200, shooterSubsystem.getTransferRPM()));
    } else {
      shooterSubsystem.SetTransferWheelPower(indexMotorPowerController.calculate(0, shooterSubsystem.getTransferRPM()));
    }
  }

  @Override
  public void end(boolean interrupted) {
    shooterSubsystem.SetTransferWheelPower(0);
    shooterSubsystem.SetflyWheelPower(0);
    shooterSubsystem.SetAgitatorPower(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

}