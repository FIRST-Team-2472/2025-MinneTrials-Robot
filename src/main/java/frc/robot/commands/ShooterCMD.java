package frc.robot.commands;

import frc.robot.subsystems.ShooterSubsystem;
import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.MotorPowerController;

public class ShooterCMD extends Command {
    Supplier<Boolean> xboxAPressed;
    ShooterSubsystem shooterSubsystem;
    MotorPowerController gatePowerController;
    MotorPowerController cannonPowerController;

    public ShooterCMD(ShooterSubsystem shooterSubsystem, Supplier<Boolean> xboxAPressed) {
        this.shooterSubsystem = shooterSubsystem;
        this.xboxAPressed = xboxAPressed;
        gatePowerController = new MotorPowerController(0.01, 0.2, 0.1, 0.5, 0, 0, 25);
        cannonPowerController = new MotorPowerController(1/400.0, 0.5, 0.1, 0.5, 0, 0, 200);
        addRequirements(shooterSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if (xboxAPressed.get()) {
            shooterSubsystem.setGatePower(gatePowerController.calculateMotorPowerController(200, shooterSubsystem.getGateRpm()));
            shooterSubsystem.setCannonPower(cannonPowerController.calculateMotorPowerController(1400, shooterSubsystem.getCannonRpm()));
        } else {
            shooterSubsystem.setGatePower(gatePowerController.calculateMotorPowerController(0, shooterSubsystem.getGateRpm()));
            shooterSubsystem.setCannonPower(cannonPowerController.calculateMotorPowerController(0, shooterSubsystem.getCannonRpm()));
        }
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}