package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

import java.util.Optional;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.wpilibj.DriverStation;

public class TankDriveSubsystem extends SubsystemBase {

  private SparkMax leftDriveMotor = new SparkMax(Constants.TankDriveConstants.kLeftDriveMotorID, MotorType.kBrushless);
  private SparkMax rightDriveMotor = new SparkMax(Constants.TankDriveConstants.kRightDriveMotorID,
      MotorType.kBrushless);

  private Pigeon2 gyro = new Pigeon2(Constants.TankDriveConstants.kPigeonID);
  DifferentialDrivePoseEstimator differentialDrivePoseEstimator;

  DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(Units.inchesToMeters(20.273));

  public TankDriveSubsystem() {
    SparkMaxConfig config = new SparkMaxConfig();
    config.smartCurrentLimit(35);
    config.idleMode(IdleMode.kBrake);
    leftDriveMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightDriveMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    differentialDrivePoseEstimator = new DifferentialDrivePoseEstimator(kinematics, gyro.getRotation2d(),
        Units.inchesToMeters(getLeftEncoder()), Units.inchesToMeters(getRightEncoder()), new Pose2d());
  }

  public boolean IsOnRed() {
    Optional<Alliance> ally = DriverStation.getAlliance();
    if (ally.isPresent()) {
      if (ally.get() == Alliance.Red) {
        return true;
      }
      if (ally.get() == Alliance.Blue) {
        return false;
      }
    } else {
      return false;
    }
  }

  private double getLeftEncoder() {
    return leftDriveMotor.getEncoder().getPosition() * 4 * Math.PI;
  }

  private double getRightEncoder() {
    return rightDriveMotor.getEncoder().getPosition() * 4 * Math.PI;
  }

  public void setMotorPower(double powerLeft, double powerRight) {
    leftDriveMotor.set(powerLeft);
    rightDriveMotor.set(powerRight * -1);
  }

  public void arcadeDrive(double power, double turnPercent) {
    double powerLeft = power;
    double powerRight = power;

    powerLeft -= turnPercent;
    powerRight += turnPercent;

    setMotorPower(powerLeft, powerRight);
  }

  @Override
  public void periodic() {
    differentialDrivePoseEstimator.update(gyro.getRotation2d(), getLeftEncoder(), getRightEncoder());
  }

  public Pose2d getPose() {
    return differentialDrivePoseEstimator.getEstimatedPosition();
  }

  public void setPose(double x, double y, Rotation2d rotation) {
    differentialDrivePoseEstimator.resetPose(new Pose2d(x, y, rotation));
  }

  public double getSpeed() {
    double leftV = leftDriveMotor.getEncoder().getVelocity();
    double rightV = leftDriveMotor.getEncoder().getVelocity();
    return (leftV + rightV) / 2 * 0.00531976356; // Averages and converts to meters per second
  }

  @Override
  public void simulationPeriodic() {
  }
}