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
        autonomousModeChooser.addOption("1 Cube Auto", AutonomousMode.One_Cube_Auto);
        autonomousModeChooser.addOption("Carrier Cube Auto", AutonomousMode.Carrier_Cube_Auto);
        autonomousModeChooser.addOption("Center Balance Auto", AutonomousMode.Center_Balance_Auto);
        autonomousModeChooser.addOption("Balance Test", AutonomousMode.Balance_Test);

        autoTab.add("Mode", autonomousModeChooser);
        
    }

    public Command getCommand(Swerve s_Swerve){
        AutonomousMode mode = autonomousModeChooser.getSelected();

        switch (mode) {
            case One_Cube_Auto:
                return new DefaultAuto(s_Swerve);
            case Carrier_Cube_Auto:
                return new CarrierCubeAuto(s_Swerve);
            case Center_Balance_Auto:
                return new CenterBalanceAuto(s_Swerve);
            case Balance_Test:
                return new BalanceTest(s_Swerve);
            default:
                return new DefaultAuto(s_Swerve);
        }
    }

    public AutonomousSelector() {
        
    }

    private enum AutonomousMode {
        One_Cube_Auto,
        Center_Balance_Auto,
        Balance_Test,
        Carrier_Cube_Auto
    }

}
