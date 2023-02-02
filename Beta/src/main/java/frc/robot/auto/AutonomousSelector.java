package frc.robot.auto;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Swerve;

public class AutonomousSelector {

    private static SendableChooser<AutonomousMode> autonomousModeChooser;
    private Swerve s_Swerve;

    static {
        ShuffleboardTab autoTab = Shuffleboard.getTab("Auto settings");

        autonomousModeChooser = new SendableChooser<>();
        autonomousModeChooser.addOption("Drive Three Feet", AutonomousMode.Drive_One_Meter);
        autonomousModeChooser.addOption("Turn 90 Degrees", AutonomousMode.Turn_90_Degrees);
        autonomousModeChooser.addOption("ExampleAuto", AutonomousMode.Example_Auto);
        autonomousModeChooser.addOption("First Auto Command Group", AutonomousMode.First_Auto_Command_Group);


        
        autoTab.add("Mode", autonomousModeChooser);
        
    }

    public Command getCommand(){
        AutonomousMode mode = autonomousModeChooser.getSelected();

        switch (mode) {
            case Drive_One_Meter:
                return new DriveOneMeter(s_Swerve);
            case Turn_90_Degrees:
                return new Turn90Degrees(s_Swerve);
            case Example_Auto:
                return new ExampleAuto(s_Swerve);
            case First_Auto_Command_Group:
                return new FirstAutoComandGroup();
            default:
                System.out.println("ERROR: unexpected auto mode: " + mode);
                break; 
        }

        return null;
    }

    public AutonomousSelector() {
    }

    private enum AutonomousMode {
        Drive_One_Meter,
        Turn_90_Degrees,
        Example_Auto,
        First_Auto_Command_Group
    }

}
