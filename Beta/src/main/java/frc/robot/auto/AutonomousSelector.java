package frc.robot.auto;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Swerve;

public class AutonomousSelector {

    private static SendableChooser<AutonomousMode> autonomousModeChooser;

    static {
        ShuffleboardTab autoTab = Shuffleboard.getTab("Auto settings");

        autonomousModeChooser = new SendableChooser<>();
        autonomousModeChooser.addOption("Red 2 Cone Auto", AutonomousMode.Red_2_Cone_Auto);
        autonomousModeChooser.addOption("Red 2 Cone Balance Auto", AutonomousMode.Red_2_Cone_Balance_Auto);
        autonomousModeChooser.addOption("Blue 2 Cone Auto", AutonomousMode.Blue_2_Cone_Auto);
        autonomousModeChooser.addOption("Blue 2 Cone Balance Auto", AutonomousMode.Blue_2_Cone_Balance_Auto);
        autonomousModeChooser.addOption("Red Cone Cube Auto", AutonomousMode.Red_Cone_Cube_Auto);
        autonomousModeChooser.addOption("Blue Cone Backoff Auto", AutonomousMode.Blue_Cone_Backoff_Auto);
        autonomousModeChooser.addOption("Turn Test", AutonomousMode.Turn_Test);
        autonomousModeChooser.addOption("Distance Test", AutonomousMode.Distance_Test);

        autoTab.add("Mode", autonomousModeChooser);
        
    }

    public Command getCommand(Swerve s_Swerve){
        AutonomousMode mode = autonomousModeChooser.getSelected();

        switch (mode) {
            case Red_2_Cone_Auto:
                return new Red2ConeAuto(s_Swerve);
            case Red_2_Cone_Balance_Auto:
                return new Red2ConeBalanceAuto(s_Swerve);
            case Blue_2_Cone_Auto:
                return new Blue2ConeAuto(s_Swerve);
            case Blue_2_Cone_Balance_Auto:
                return new Blue2ConeBalanceAuto(s_Swerve);
            case Red_Cone_Cube_Auto:
                return new RedConeCubeAuto(s_Swerve);
            case Blue_Cone_Backoff_Auto:
                return new BlueConeBackoffAuto(s_Swerve);
            case Turn_Test:
                return new TurnTest(s_Swerve);
            case Distance_Test:
                return new DistanceTest(s_Swerve);
            default:
                System.out.println("ERROR: unexpected auto mode: " + mode);
                break; 
        }

        return null;
    }

    public AutonomousSelector() {
    }

    private enum AutonomousMode {
        Red_2_Cone_Auto,
        Red_2_Cone_Balance_Auto,
        Blue_2_Cone_Auto,
        Blue_2_Cone_Balance_Auto,
        Red_Cone_Cube_Auto,
        Blue_Cone_Backoff_Auto,
        Turn_Test,
        Distance_Test,
    }

}
