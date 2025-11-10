package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.TankDriveSubsystem;

public class TankDriveCMD extends Command {
    TankDriveSubsystem tankDriveSubsystem;
    Supplier<Double> joystickLeft;
    Supplier<Double> joystickRight;
    public TankDriveCMD (TankDriveSubsystem tankDriveSubsystem, Supplier<Double> joystickLeft, Supplier<Double> joystickRight) {
        this.tankDriveSubsystem = tankDriveSubsystem;
        this.joystickLeft = joystickLeft;
        this.joystickRight = joystickRight;
        addRequirements(tankDriveSubsystem);
        
    }

  @Override
  public void initialize() {}
  
  @Override
  public void execute() {
    double powerLeft = joystickLeft.get(); 
    double powerRight = joystickRight.get();

    tankDriveSubsystem.arcadeDrive(powerLeft, powerRight);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
