package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterCMD extends Command{
    ShooterSubsystem shooterSubsystem;
    Supplier<Double> leftTrigger;
    Supplier<Double> rightTrigger;

    public ShooterCMD(ShooterSubsystem shooterSubsystem, Supplier<Double> leftTrigger, Supplier<Double> rightTrigger){
        this.shooterSubsystem = shooterSubsystem;
    }

  @Override
  public void initialize() {}
  
  @Override
  public void execute() {
    
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}