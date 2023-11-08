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
        autonomousModeChooser.addOption("1 Cone Auto", AutonomousMode.One_Cone_Auto);
        autonomousModeChooser.addOption("Center Balance Auto", AutonomousMode.Center_Balance_Auto);
        autonomousModeChooser.addOption("Cone Cube Cube Auto", AutonomousMode.Cone_Cube_Cube_Auto);
        autonomousModeChooser.addOption("Cone Cube Cone Auto", AutonomousMode.Cone_Cube_Cone_Auto);
        autonomousModeChooser.addOption("Cone Cube Balance Auto", AutonomousMode.Cone_Cube_Balance_Auto);
        autonomousModeChooser.addOption("Three Piece Auto", AutonomousMode.Three_Piece_Auto);
        autonomousModeChooser.addOption("Barrier Spit Cone Auto", AutonomousMode.Barrier_Spit_Cone_Auto);
        autonomousModeChooser.addOption("Barrier Spit Auto", AutonomousMode.Barrier_Spit_Auto);
        autonomousModeChooser.addOption("Barrier Spit Balance Auto", AutonomousMode.Barrier_Spit_Balance_Auto);
        autonomousModeChooser.addOption("Barrier Cone Cube Auto", AutonomousMode.Barrier_Cone_Cube_Auto);
        autonomousModeChooser.addOption("Balance Test", AutonomousMode.Balance_Test);

        autoTab.add("Mode", autonomousModeChooser);
        
    }

    public Command getCommand(Swerve s_Swerve){
        AutonomousMode mode = autonomousModeChooser.getSelected();

        switch (mode) {
            case One_Cone_Auto:
                return new DefaultAuto(s_Swerve);
            case Center_Balance_Auto:
                return new CenterBalanceAuto(s_Swerve);
            case Cone_Cube_Cube_Auto:
                return new ConeCubeCubeAuto(s_Swerve);
            case Cone_Cube_Cone_Auto:
                return new ConeCubeConeAuto(s_Swerve);
            case Cone_Cube_Balance_Auto:
                return new ConeCubeBalanceAuto(s_Swerve);
            case Three_Piece_Auto:
                return new ThreePieceHHMAuto(s_Swerve);
            case Barrier_Spit_Cone_Auto:
                return new BarrierSpitConeAuto(s_Swerve);
            case Barrier_Spit_Auto:
                return new BarrierSpitAuto(s_Swerve);
            case Barrier_Spit_Balance_Auto:
                return new BarrierSpitBalanceAuto(s_Swerve);
            case Barrier_Cone_Cube_Auto:
                return new BarrierConeCubeAuto(s_Swerve);
            case Balance_Test:
                return new BalanceTest(s_Swerve);
            default:
                return new DefaultAuto(s_Swerve);
        }
    }

    public AutonomousSelector() {
        
    }

    private enum AutonomousMode {
        One_Cone_Auto,
        Center_Balance_Auto,
        Cone_Cube_Cube_Auto,
        Cone_Cube_Cone_Auto,
        Cone_Cube_Balance_Auto,
        Barrier_Spit_Auto,
        Barrier_Spit_Balance_Auto,
        Barrier_Spit_Cone_Auto,
        Barrier_Cone_Cube_Auto,
        Balance_Test,
        Three_Piece_Auto
    }

}
