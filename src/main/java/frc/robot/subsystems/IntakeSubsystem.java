package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;


public class IntakeSubsystem extends SubsystemBase {

private final int shaftMotorID = 1; // lower subsystem motor  
private final int beltMotorID = 1; // upper subsystem motor
private SparkMax shaftMotor = new SparkMax(shaftMotorID, MotorType.kBrushless);
private SparkMax beltMotor = new SparkMax(beltMotorID, MotorType.kBrushless);

public IntakeSubsystem() {
    // Motor setup
    SparkMaxConfig config = new SparkMaxConfig();
    config.smartCurrentLimit(35);
    config.idleMode(IdleMode.kBrake);
    shaftMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    beltMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

// Methods that set the power of the motors
    public void setShaftPower(double intakepower) {
    shaftMotor.set(intakepower);
    }
    public void setBeltPower(double intakePower) {
        beltMotor.set (intakePower);
    }
}