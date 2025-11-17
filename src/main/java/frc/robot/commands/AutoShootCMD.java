package frc.robot.commands;

import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

public class AutoShootCMD extends Command {
    ShooterSubsystem shooterSubsystem;
    Timer timeoutTimer;

    public AutoShootCMD(ShooterSubsystem shooterSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
        timeoutTimer = new Timer();
        addRequirements(shooterSubsystem);
    }

    @Override
    public void initialize() {
        timeoutTimer.restart();
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {
        
    }

    @Override
    public boolean isFinished() {
        return timeoutTimer.hasElapsed(1.5);
    }
}