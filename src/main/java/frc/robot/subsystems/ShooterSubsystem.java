package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
    private SparkMax GateMotor = new SparkMax(0, MotorType.kBrushless);
    private SparkMax ShooterMotor = new SparkMax(0, MotorType.kBrushless);

    public ShooterSubsystem() {
        SparkMaxConfig config = new SparkMaxConfig();
        config.smartCurrentLimit(35);
        config.idleMode(IdleMode.kBrake);
        GateMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        ShooterMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void setGatePower(double powerGate) {
        GateMotor.set(powerGate);
      }
      public void setShooterPower(double powerShooter) {
        ShooterMotor.set(powerShooter);
      }
}
