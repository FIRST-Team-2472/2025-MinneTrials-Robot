package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.MotorPowerController;
import frc.robot.extras.AccelLimiter;
import frc.robot.extras.PathingNode;
import frc.robot.subsystems.TankDriveSubsystem;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Timer;

public class PathfindingCommand extends Command {
    PathingNode[] pathingPoses = new PathingNode[11];
    int currentPathNode = 0;

    MotorPowerController yawController;
    MotorPowerController speedController;
    TankDriveSubsystem tankDriveSubsystem;
    AccelLimiter speedLimiter;
    AccelLimiter yawLimiter;

    Timer timeoutTimer = new Timer();
    double allowedWaypointDistance = 0.2;

    public PathfindingCommand(TankDriveSubsystem tankDriveSubsystem) {
        this.tankDriveSubsystem = tankDriveSubsystem;
        addRequirements(tankDriveSubsystem);

        speedLimiter = new AccelLimiter(0.02, 0.1);
        yawLimiter = new AccelLimiter(0.03, 0.03);
        speedController = new MotorPowerController(0.33, 0.5, 0, 0.5, 0.67, 0, 1);
        yawController = new MotorPowerController(0.0222, 0, 0.1, 0, 0, 0, 0);
    }

    private void setPathingPoses() {
        pathingPoses[0] = new PathingNode(1.72085, 3.00355, 3);
        pathingPoses[1] = new PathingNode(1.3589, 2.86258, 2.34);
        pathingPoses[2] = new PathingNode(1.20142, 2.7686, 2.1);
        pathingPoses[3] = new PathingNode(1.07646, 2.66954, 1.86);
        pathingPoses[4] = new PathingNode(0.98044, 2.57048, 1.68);
        pathingPoses[5] = new PathingNode(0.94488, 2.5273, 1.62);
        pathingPoses[6] = new PathingNode(0.9144, 2.48666, 1.62);
        pathingPoses[7] = new PathingNode(0.82804, 2.34696, 1.44);
        pathingPoses[8] = new PathingNode(0.762, 2.2098, 1.26);
        pathingPoses[9] = new PathingNode(0.70358, 2.05486, 1.08);
        pathingPoses[10] = new PathingNode(0.6223, 1.7653, 67);
        if (!tankDriveSubsystem.IsOnRed()) { // Detects Blue Alliance
            flipCoords();
        }
    }

    @Override
    public void initialize() {
        timeoutTimer.restart();
        setPathingPoses();
        tankDriveSubsystem.setPose(pathingPoses[0].getX(), pathingPoses[0].getY(), new Rotation2d().fromDegrees(270));
    }

    @Override
    public void execute() {

        if (getDistanceFromWaypoint() < allowedWaypointDistance && currentPathNode < pathingPoses.length) {
            currentPathNode++;
        }

        if (currentPathNode < pathingPoses.length) {
            double targetAngleDegrees = Math.atan2(yDifference(), xDifference()) * (180 / Math.PI);
            Rotation2d targetAngle = new Rotation2d().fromDegrees(targetAngleDegrees);
            Rotation2d angleDifference = tankDriveSubsystem.getPose().getRotation().minus(targetAngle);
            double rotationValue = yawLimiter.calculate(yawController.calculate(0,
                    angleDifference.getDegrees()));
            double speed = speedLimiter.calculate(speedController.calculate(pathingPoses[currentPathNode].getSpeed(),
                    tankDriveSubsystem.getSpeed()));

            tankDriveSubsystem.arcadeDrive(-speed, -rotationValue);
        } else {
            double speed = speedLimiter.calculate(speedController.calculate(getDistanceFromWaypoint() / 2,
                    tankDriveSubsystem.getSpeed()));
            tankDriveSubsystem.setMotorPower(speed, speed);
        }
    }

    @Override
    public void end(boolean interrupted) {
        tankDriveSubsystem.setMotorPower(0, 0);
    }

    @Override
    public boolean isFinished() {
        return timeoutTimer.hasElapsed(10) || getDistanceFromWaypoint() < allowedWaypointDistance;
    }

    public double getDistanceFromWaypoint() {
        double xDif = tankDriveSubsystem.getPose().getX() - pathingPoses[currentPathNode].getX();
        double yDif = tankDriveSubsystem.getPose().getY() - pathingPoses[currentPathNode].getY();
        return Math.sqrt(xDif * xDif + yDif * yDif);
    }

    public double xDifference() {
        return tankDriveSubsystem.getPose().getX() - pathingPoses[currentPathNode].getX();
    }

    public double yDifference() {
        return tankDriveSubsystem.getPose().getY() - pathingPoses[currentPathNode].getY();
    }

    public void flipCoords() {
        for (int i = 0; i < pathingPoses.length; i++) {
            pathingPoses[i].teamFlip();
        }
    }
}