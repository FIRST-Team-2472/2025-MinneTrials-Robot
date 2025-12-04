package frc.robot.commands;

import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.TankDriveSubsystem;
import frc.robot.MotorPowerController;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

public class AutoShootCMD extends Command {
    ShooterSubsystem shooterSubsystem;
    TankDriveSubsystem tankDriveSubsystem;
    Timer timeoutTimer;
    Timer driveTimer;
    Timer revUpTimer;
    MotorPowerController flywheelMotorPowerController;
    MotorPowerController indexMotorPowerController;
    double AgitatorPower = 0.2;
    double drivePower = 0.2;

    public AutoShootCMD(ShooterSubsystem shooterSubsystem, TankDriveSubsystem tankDriveSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
        this.tankDriveSubsystem = tankDriveSubsystem;
        timeoutTimer = new Timer();
        driveTimer = new Timer();
        revUpTimer = new Timer();
        addRequirements(tankDriveSubsystem);
        addRequirements(shooterSubsystem);

        flywheelMotorPowerController = new MotorPowerController(0.00014, 0.8, 0.01, 0.5, 0.67, 0, 300);
        indexMotorPowerController = new MotorPowerController(0.0001, 0.3, 0.005, 0.2, 0.67, 0, 200);
    }

    @Override
    public void initialize() {
        timeoutTimer.restart();
        driveTimer.restart();
        revUpTimer.restart();
    }

    @Override
    public void execute() {

        if (driveTimer.hasElapsed(0.2)) {
            drivePower *= -1;
            tankDriveSubsystem.setMotorPower(drivePower, drivePower);
            driveTimer.reset();
        }

        shooterSubsystem.SetAgitatorPower(AgitatorPower);

        shooterSubsystem.SetflyWheelPower(flywheelMotorPowerController.calculate(1400, shooterSubsystem.getFlyWheelRPM()));
        if (revUpTimer.hasElapsed(0.5)) {
            shooterSubsystem.SetTransferWheelPower(indexMotorPowerController.calculate(200, shooterSubsystem.getTransferRPM()));
        }
    }

    @Override
    public void end(boolean interrupted) {
        tankDriveSubsystem.setMotorPower(0, 0);
        shooterSubsystem.SetflyWheelPower(flywheelMotorPowerController.calculate(0, shooterSubsystem.getFlyWheelRPM()));
        shooterSubsystem.SetTransferWheelPower(indexMotorPowerController.calculate(0, shooterSubsystem.getTransferRPM()));
    }

    @Override
    public boolean isFinished() {
        return timeoutTimer.hasElapsed(6.5);
    }
}