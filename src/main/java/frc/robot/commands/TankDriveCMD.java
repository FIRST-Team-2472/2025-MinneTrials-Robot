package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.MotorSubsystem;

public class TankDriveCMD extends Command {
    MotorSubsystem motorSubsystem;
    public TankDriveCMD (MotorSubsystem tankDriveSubsystem, Supplier <Double> leftSpeed, Supplier<Double> rightSpeed) {
        this.motorSubsystem = tankDriveSubsystem;
       addRequirements(tankDriveSubsystem);
    }

    

  public TankDriveCMD(Subsystem tankDriveSubsystem, Supplier<Double> supplier) {
        //TODO Auto-generated constructor stub
    }



@Override
  public void initialize() {}
  
  @Override
  public void execute() {
    // insert the thing that gets the power here
  }
  
  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
