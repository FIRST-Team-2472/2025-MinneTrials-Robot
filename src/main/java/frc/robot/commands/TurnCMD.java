package frc.robot.commands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Acceleration;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.MotorPowerController;
import frc.robot.extras.AccelLimiter;
import frc.robot.subsystems.TankDriveSubsystem;

public class TurnCMD extends Command {
    TankDriveSubsystem tankDriveSubsystem;
    MotorPowerController yawController;
    AccelLimiter accelLimiter;
    Timer timeoutTimer;
    double targetDirection = 270;

    public TurnCMD(TankDriveSubsystem tankDriveSubsystem){
        this.tankDriveSubsystem = tankDriveSubsystem;
        addRequirements(tankDriveSubsystem);

        timeoutTimer = new Timer();

        accelLimiter = new AccelLimiter(0.02, 0.1);
        yawController = new MotorPowerController(0.015, 0, 0.2, 0, 0, 0, 0);
    }

      @Override
    public void initialize() {
       timeoutTimer.restart();
    }

    @Override
    public void execute() {
        Rotation2d targetAngle = new Rotation2d().fromDegrees(targetDirection);
        double speedNeeded = accelLimiter.calculate(yawController.calculate(0, tankDriveSubsystem.getPose().getRotation().minus(targetAngle).getDegrees()));
        tankDriveSubsystem.arcadeDrive(0, -speedNeeded);
    }

    @Override
    public void end(boolean interrupted) {
        tankDriveSubsystem.setMotorPower(0, 0);
    }

    @Override
    public boolean isFinished() {
        return timeoutTimer.hasElapsed(3) || Math.abs(tankDriveSubsystem.getPose().getRotation().getDegrees() - targetDirection) < 3;
    }
}