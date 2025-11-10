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

    private SparkMax flyWheel = new SparkMax(Constants.OperatorConstants.flyWheelID, MotorType.kBrushless);
    private SparkMax transferWheel = new SparkMax(Constants.OperatorConstants.transferWheelID, MotorType.kBrushless);

    public ShooterSubsystem(){
        SparkMaxConfig config = new SparkMaxConfig();
        config.smartCurrentLimit(35);
    
    }
}