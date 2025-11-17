package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.TankDriveSubsystem;

public class DriveForwardCMD extends Command {
    TankDriveSubsystem tankDriveSubsystem;
    Timer timeoutTimer;
    

    public DriveForwardCMD(TankDriveSubsystem tankDriveSubsystem) {
        this.tankDriveSubsystem = tankDriveSubsystem;
        timeoutTimer = new Timer();
        addRequirements(tankDriveSubsystem);
    }

    @Override
    public void initialize() {
        timeoutTimer.restart();
    }

    @Override
    public void execute() {
        tankDriveSubsystem.setMotorPower(0.5, 0.5);
    }

    @Override
    public void end(boolean interrupted) {
        tankDriveSubsystem.setMotorPower(0, 0);
    }

    @Override
    public boolean isFinished() {
        return timeoutTimer.hasElapsed(1.5);
    }
}