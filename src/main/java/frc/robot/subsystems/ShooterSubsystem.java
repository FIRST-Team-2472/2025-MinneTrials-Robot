package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {

    private SparkMax flyWheel = new SparkMax(Constants.ShooterConstants.kFlyWheelID, MotorType.kBrushless);
    private SparkMax transferWheel = new SparkMax(Constants.ShooterConstants.kTransferWheelID, MotorType.kBrushless);
    private SparkMax agitator = new SparkMax(Constants.ShooterConstants.kAgitatorMotorID, MotorType.kBrushless);

    public ShooterSubsystem() {
        SparkMaxConfig config = new SparkMaxConfig();
        config.smartCurrentLimit(35);
        config.idleMode(IdleMode.kBrake);
        flyWheel.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        agitator.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        transferWheel.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void SetflyWheelPower(double power) {
        flyWheel.set(power);
    }

    public void SetTransferWheelPower(double power) {
        transferWheel.set(power);
    }

    public void SetAgitatorPower(double power) {
        agitator.set(power);
    }

    @Override
    public void periodic() {

    }
}