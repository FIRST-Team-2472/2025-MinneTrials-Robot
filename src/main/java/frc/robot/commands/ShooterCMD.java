package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterCMD extends Command {
  ShooterSubsystem shooterSubsystem;
  Supplier<Boolean> leftTrigger;
  Supplier<Boolean> rightTrigger;
  double AgitatorPower = 0.2;

  Timer directionSwitchTimer = new Timer();

  public ShooterCMD(ShooterSubsystem shooterSubsystem, Supplier<Boolean> leftTrigger, Supplier<Boolean> rightTrigger) {
    this.shooterSubsystem = shooterSubsystem;
    this.leftTrigger = leftTrigger;
    this.rightTrigger = rightTrigger;
    addRequirements(shooterSubsystem);
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
      shooterSubsystem.SetflyWheelPower(1);
    } else {
      shooterSubsystem.SetflyWheelPower(0);
    }

    if (rightTrigger.get()) {
      shooterSubsystem.SetTransferWheelPower(0.25);
    } else {
      shooterSubsystem.SetTransferWheelPower(0);
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