package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {

  private SparkMax GateMotor = new SparkMax(Constants.ShooterConstants.kGateMotorID, MotorType.kBrushless);
  private SparkMax CannonMotor = new SparkMax(Constants.ShooterConstants.kCannonMotorID, MotorType.kBrushless);

  public ShooterSubsystem() {
    SparkMaxConfig config = new SparkMaxConfig();
    config.smartCurrentLimit(35);
    config.idleMode(IdleMode.kBrake);
    GateMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    CannonMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  public void setGatePower(double powerGate) {
    GateMotor.set(powerGate);
  }

  public void setCannonPower(double powerCannon) {
    CannonMotor.set(powerCannon);
  }

  public double getGateRpm() {
    return GateMotor.getEncoder().getVelocity()/3;
  }
  public double getCannonRpm() {
    return CannonMotor.getEncoder().getVelocity()/3;
  }
}
