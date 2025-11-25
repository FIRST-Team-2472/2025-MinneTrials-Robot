package frc.robot.commands;

import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.MotorPowerController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

public class AutoShootCMD extends Command {
    ShooterSubsystem shooterSubsystem;
    Timer timeoutTimer;
    MotorPowerController flywheelMotorPowerController;
    MotorPowerController indexMotorPowerController;
    double AgitatorPower = 0.2;

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
    public void execute() { //Will need to be changed based on shooter fixes
        
        shooterSubsystem.SetAgitatorPower(AgitatorPower);

        shooterSubsystem.SetflyWheelPower(flywheelMotorPowerController.calculate(1400, shooterSubsystem.getFlyWheelRPM()));
        shooterSubsystem.SetTransferWheelPower(indexMotorPowerController.calculate(200, shooterSubsystem.getTransferRPM()));
    }

    @Override
    public void end(boolean interrupted) {

        shooterSubsystem.SetflyWheelPower(flywheelMotorPowerController.calculate(0, shooterSubsystem.getFlyWheelRPM()));
        shooterSubsystem.SetTransferWheelPower(indexMotorPowerController.calculate(0, shooterSubsystem.getTransferRPM()));
    }

    @Override
    public boolean isFinished() {
        return timeoutTimer.hasElapsed(1.5);
    }
}