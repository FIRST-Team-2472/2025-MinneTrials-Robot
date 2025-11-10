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
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

import com.ctre.phoenix6.hardware.Pigeon2;

public class TankDriveSubsystem extends SubsystemBase {

  private static final int kLeftDriveMotorID = 1;
  private static final int kRightDriveMotorID = 1;
  private static final int kPigeonID = 0;
  private final double kWheelCircumferenceMeters = Units.inchesToMeters(1);
  
  private SparkMax leftDriveMotor= new SparkMax(kLeftDriveMotorID, MotorType.kBrushless);
  private SparkMax rightDriveMotor = new SparkMax(kRightDriveMotorID, MotorType.kBrushless);
  
  private SparkAbsoluteEncoder leftEncoder = leftDriveMotor.getAbsoluteEncoder();
  private SparkAbsoluteEncoder rightEncoder = rightDriveMotor.getAbsoluteEncoder();

  private Pigeon2 gyro = new Pigeon2(kPigeonID);

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
    
    if(turnPercent < 0){
      powerLeft = powerLeft - Math.abs(turnPercent);
      powerRight = powerRight + Math.abs(turnPercent);
    } 
    if(turnPercent > 0){
      powerLeft = powerLeft + Math.abs(turnPercent);
      powerRight = powerRight - Math.abs(turnPercent);
    }
    
    setMotorPower(powerLeft, powerRight);
  }

  @Override
  public void periodic() {

  }

  @Override
  public void simulationPeriodic() {}
}