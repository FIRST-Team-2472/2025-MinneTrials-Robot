package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.MotorPowerController;
import frc.robot.extras.PathingNode;
import frc.robot.subsystems.TankDriveSubsystem;
import edu.wpi.first.wpilibj.Timer;

public class PathfindingCommand extends Command {
    PathingNode[] pathingPoses = new PathingNode[11];
    int currentPathNode = 0;

    MotorPowerController yawController;
    MotorPowerController speedController;
    TankDriveSubsystem tankDriveSubsystem;
    Timer timeoutTimer = new Timer();
    double allowedWaypointDistance = 0.1;

    public PathfindingCommand(TankDriveSubsystem tankDriveSubsystem) {
        this.tankDriveSubsystem = tankDriveSubsystem;
        addRequirements(tankDriveSubsystem);

        speedController = new MotorPowerController(0.01, 0.1, 0.1, 0.5, 0.67, 0, 34.9);
        yawController = new MotorPowerController(0.01, 0.1, 0.1, 0.5, 0.67, 0, 34.9);
    }

    private void setPathingPoses() {
        pathingPoses[0] = new PathingNode(1.72085, 3.00355, 0.5);
        pathingPoses[1] = new PathingNode(1.3589, 2.86258, 0.39);
        pathingPoses[2] = new PathingNode(1.20142, 2.7686, 0.35);
        pathingPoses[3] = new PathingNode(1.07646, 2.66954, 0.31);
        pathingPoses[4] = new PathingNode(0.98044, 2.57048, 0.28);
        pathingPoses[5] = new PathingNode(0.94488, 2.5273, 0.27);
        pathingPoses[6] = new PathingNode(0.9144, 2.48666, 0.27);
        pathingPoses[7] = new PathingNode(0.82804, 2.34696, 0.24);
        pathingPoses[8] = new PathingNode(0.762, 2.2098, 0.21);
        pathingPoses[9] = new PathingNode(0.70358, 2.05486, 0.18);
        pathingPoses[10] = new PathingNode(0.6223, 1.7653, 67);
    }

    @Override
    public void initialize() {
        timeoutTimer.restart();
        setPathingPoses();
    }

    @Override
    public void execute() {

        if (getDistanceFromWaypoint() < allowedWaypointDistance && currentPathNode < pathingPoses.length) {
            currentPathNode++;
        }

        if (currentPathNode < pathingPoses.length) {
            double targetAngle = Math.atan2(yDifference(), xDifference()) * (180 / Math.PI);
            double rotationValue = yawController.calculate(targetAngle,
                    tankDriveSubsystem.getPose().getRotation().getDegrees());
            double speed = speedController.calculate(pathingPoses[currentPathNode].getSpeed(),
                    tankDriveSubsystem.getSpeed());

            tankDriveSubsystem.arcadeDrive(speed, rotationValue);
        } else {
            double speed = speedController.calculate(getDistanceFromWaypoint()/2,
                    tankDriveSubsystem.getSpeed());
            tankDriveSubsystem.setMotorPower(speed, speed);
        }
    }

    @Override
    public void end(boolean interrupted) {
        tankDriveSubsystem.setMotorPower(0,0);
    }

    @Override
    public boolean isFinished() {
        return timeoutTimer.hasElapsed(5) || getDistanceFromWaypoint() < allowedWaypointDistance;
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
}