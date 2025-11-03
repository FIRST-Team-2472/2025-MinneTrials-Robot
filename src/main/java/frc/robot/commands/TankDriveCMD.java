package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.TankDriveSubsystem;

public class TankDriveCMD extends Command {
    TankDriveSubsystem tankDriveSubsystem;
    Supplier<Double> joystickY;
    Supplier<Double> joystickX;
    public TankDriveCMD (TankDriveSubsystem tankDriveSubsystem, Supplier<Double> joystickY, Supplier<Double> joystickX) {
        this.tankDriveSubsystem = tankDriveSubsystem;
        this.joystickY = joystickY;
        this.joystickX = joystickX;
        addRequirements(tankDriveSubsystem);
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
