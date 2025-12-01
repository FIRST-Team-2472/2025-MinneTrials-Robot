package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.MotorPowerController;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterCMD extends Command {
    ShooterSubsystem shooterSubsystem;
    MotorPowerController flywheelMotorPowerController;
    MotorPowerController indexMotorPowerController;
    Supplier<Boolean> leftTrigger;
    Supplier<Boolean> rightTrigger;
    double AgitatorPower = 0.2;

    Timer directionSwitchTimer = new Timer();

    public ShooterCMD(ShooterSubsystem shooterSubsystem, Supplier<Boolean> leftTrigger, Supplier<Boolean> rightTrigger) {
        this.shooterSubsystem = shooterSubsystem;
        this.leftTrigger = leftTrigger;
        this.rightTrigger = rightTrigger;
        addRequirements(shooterSubsystem);
        flywheelMotorPowerController = new MotorPowerController(0.00014, 0.8, 0.01, 0.5, 0.67, 0, 300);
        indexMotorPowerController = new MotorPowerController(0.0001, 0.3, 0.005, 0.2, 0.67, 0, 200);
    }

    @Override
    public void initialize() {
        directionSwitchTimer.start();
    }

    @Override
    public void execute() {

        SmartDashboard.putNumber("RPM", shooterSubsystem.getFlyWheelRPM());

        shooterSubsystem.SetAgitatorPower(AgitatorPower);

        if (directionSwitchTimer.hasElapsed(0.75) || (AgitatorPower < 0 && directionSwitchTimer.hasElapsed(.25))) {
            AgitatorPower *= -1;
            directionSwitchTimer.reset();
        }

        if (leftTrigger.get()) {
            shooterSubsystem.SetflyWheelPower(flywheelMotorPowerController.calculate(1400, shooterSubsystem.getFlyWheelRPM()));
        } else {
            shooterSubsystem.SetflyWheelPower(flywheelMotorPowerController.calculate(0, shooterSubsystem.getFlyWheelRPM()));
        }

        if (rightTrigger.get()) {
            double transferPower = indexMotorPowerController.calculate(200, shooterSubsystem.getTransferRPM());
            shooterSubsystem.SetTransferWheelPower(transferPower);
            SmartDashboard.putNumber("transfer Power", transferPower);
        } else {
            double transferPower = indexMotorPowerController.calculate(0, shooterSubsystem.getTransferRPM());
            shooterSubsystem.SetTransferWheelPower(transferPower);
            SmartDashboard.putNumber("transfer Power", transferPower);
        }
        SmartDashboard.putNumber("transferRPM", shooterSubsystem.getTransferRPM());
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