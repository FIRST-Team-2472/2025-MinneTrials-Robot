package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.MotorSubsystem;

public class MotorCMD extends Command {
    MotorSubsystem motorSubsystem;
    public MotorCMD(MotorSubsystem motorSubsystem){
        
    }
}