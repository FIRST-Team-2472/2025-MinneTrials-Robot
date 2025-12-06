package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.MotorPowerController;
import frc.robot.extras.AccelLimiter;
import frc.robot.extras.PathingNode;
import frc.robot.subsystems.TankDriveSubsystem;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PathfindingCommand extends Command {
    PathingNode[] pathingPoses = new PathingNode[12];
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
        yawController = new MotorPowerController(0.015, 0, 0.1, 0, 0, 0, 0);
    }

    private void setPathingPoses() {
        pathingPoses[0] = new PathingNode(1.721, 3.524, 1);
        pathingPoses[1] = new PathingNode(1.651, 3.515, 0.78);
        pathingPoses[2] = new PathingNode(1.575, 3.493, 0.78);
        pathingPoses[3] = new PathingNode(1.499, 3.452, 0.7);
        pathingPoses[4] = new PathingNode(1.422, 3.393, 0.62);
        pathingPoses[5] = new PathingNode(1.295, 3.261, 0.56);
        pathingPoses[6] = new PathingNode(1.168, 3.084, 0.54);
        pathingPoses[7] = new PathingNode(1.016, 2.802, 0.54);
        pathingPoses[8] = new PathingNode(0.914, 2.576, 0.48);
        pathingPoses[9] = new PathingNode(0.813, 2.324, 0.42);
        pathingPoses[10] = new PathingNode(0.622, 1.842, 0.36);
        pathingPoses[11] = new PathingNode(0.622, 1.829, 67);
        if (!tankDriveSubsystem.IsOnRed()) { // Detects Blue Alliance
            flipCoords();
        }
    }

    @Override
    public void initialize() {
        timeoutTimer.restart();
        setPathingPoses();
        if(tankDriveSubsystem.IsOnRed()){
            tankDriveSubsystem.setPose(pathingPoses[0].getX(), pathingPoses[0].getY(), new Rotation2d().fromDegrees(180));
        } else {
            tankDriveSubsystem.setPose(pathingPoses[0].getX(), pathingPoses[0].getY(), new Rotation2d().fromDegrees(0));
        }
    }

    @Override
    public void execute() {

        if (getDistanceFromWaypoint() < allowedWaypointDistance && currentPathNode < pathingPoses.length-1) {
            currentPathNode++;
        }

        if (currentPathNode < pathingPoses.length) {
            double targetAngleDegrees = Math.atan2(yDifference(), xDifference()) * (180 / Math.PI);
            SmartDashboard.putNumber("target angle", targetAngleDegrees);
            SmartDashboard.putNumber("target X", pathingPoses[currentPathNode].getX());
            SmartDashboard.putNumber("target Y", pathingPoses[currentPathNode].getY());
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
        return timeoutTimer.hasElapsed(10) || getDistanceFromFinalWaypoint() < allowedWaypointDistance;
    }

    public double getDistanceFromWaypoint() {
        double xDif = tankDriveSubsystem.getPose().getX() - pathingPoses[currentPathNode].getX();
        double yDif = tankDriveSubsystem.getPose().getY() - pathingPoses[currentPathNode].getY();
        return Math.sqrt(xDif * xDif + yDif * yDif);
    }

    public double getDistanceFromFinalWaypoint() {
        double xDif = tankDriveSubsystem.getPose().getX() - pathingPoses[11].getX();
        double yDif = tankDriveSubsystem.getPose().getY() - pathingPoses[11].getY();
        return Math.sqrt(xDif * xDif + yDif * yDif);
    }

    public double xDifference() {
        return pathingPoses[currentPathNode].getX() - tankDriveSubsystem.getPose().getX();
    }

    public double yDifference() {
        return pathingPoses[currentPathNode].getY() - tankDriveSubsystem.getPose().getY();
    }

    public void flipCoords() {
        for (int i = 0; i < pathingPoses.length; i++) {
            pathingPoses[i].teamFlip();
        }
    }
}