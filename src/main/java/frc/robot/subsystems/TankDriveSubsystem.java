package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TankDriveSubsystem extends SubsystemBase {
    //Defines the motors
    private SparkMax leftMotor = new SparkMax(0, MotorType.kBrushless);
    private SparkMax rightMotor = new SparkMax(0, MotorType.kBrushless);
    
    //Configures the motors
    public TankDriveSubsystem() {
        SparkMaxConfig config = new SparkMaxConfig();
        config.smartCurrentLimit(35);
        config.idleMode(IdleMode.kBrake);
        leftMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        rightMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
      }
    
    //Methods that set the power of each motor
    public void setMotorPower(double leftPower, double rightPower){
        leftMotor.set(leftPower);
        rightMotor.set(rightPower);
    }
}

