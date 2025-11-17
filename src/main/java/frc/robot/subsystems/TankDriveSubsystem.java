package frc.robot.subsystems;

import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

import com.ctre.phoenix6.hardware.Pigeon2;

public class TankDriveSubsystem extends SubsystemBase {
  private final double kWheelCircumferenceMeters = Units.inchesToMeters(1);

  private SparkMax leftDriveMotor = new SparkMax(Constants.TankDriveConstants.kLeftDriveMotorID, MotorType.kBrushless);
  private SparkMax rightDriveMotor = new SparkMax(Constants.TankDriveConstants.kRightDriveMotorID, MotorType.kBrushless);

  private Pigeon2 gyro = new Pigeon2(Constants.TankDriveConstants.kPigeonID);

  DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(Units.inchesToMeters(27.0));

  public TankDriveSubsystem() {
    SparkMaxConfig config = new SparkMaxConfig();
    config.smartCurrentLimit(35);
    config.idleMode(IdleMode.kBrake);
    leftDriveMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightDriveMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  public void setMotorPower(double powerLeft, double powerRight) {
    leftDriveMotor.set(powerLeft);
    rightDriveMotor.set(powerRight);
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

  }

  @Override
  public void simulationPeriodic() {
  }
}