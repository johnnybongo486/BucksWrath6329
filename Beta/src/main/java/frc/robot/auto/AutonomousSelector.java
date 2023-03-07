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
        autonomousModeChooser.addOption("2 Cone Auto", AutonomousMode.Two_Cone_Auto);
        autonomousModeChooser.addOption("2 Cone Balance Auto", AutonomousMode.Two_Cone_Balance_Auto);
        autonomousModeChooser.addOption("Center Balance Auto", AutonomousMode.Center_Balance_Auto);
        autonomousModeChooser.addOption("Cone Cube Auto", AutonomousMode.Cone_Cube_Auto);
        autonomousModeChooser.addOption("Cone Cube Balance Auto", AutonomousMode.Cone_Cube_Balance_Auto);
        autonomousModeChooser.addOption("Barrier Balance Auto", AutonomousMode.Barrier_Balance_Auto);
        autonomousModeChooser.addOption("Barrier Grab Balance Auto", AutonomousMode.Barrier_Grab_Balance_Auto);
        autonomousModeChooser.addOption("Barrier Spit Auto", AutonomousMode.Barrier_Spit_Auto);
        autonomousModeChooser.addOption("Barrier Spit Balance Auto", AutonomousMode.Barrier_Spit_Balance_Auto);
        autonomousModeChooser.addOption("Turn Test", AutonomousMode.Turn_Test);
        autonomousModeChooser.addOption("Distance Test", AutonomousMode.Distance_Test);

        autoTab.add("Mode", autonomousModeChooser);
        
    }

    public Command getCommand(Swerve s_Swerve){
        AutonomousMode mode = autonomousModeChooser.getSelected();

        switch (mode) {
            case Two_Cone_Auto:
                return new TwoConeAuto(s_Swerve);
            case Two_Cone_Balance_Auto:
                return new TwoConeBalanceAuto(s_Swerve);
            case Center_Balance_Auto:
                return new CenterBalanceAuto(s_Swerve);
            case Cone_Cube_Auto:
                return new ConeCubeAuto(s_Swerve);
            case Cone_Cube_Balance_Auto:
                return new ConeCubeBalanceAuto(s_Swerve);
            case Barrier_Balance_Auto:
                return new BarrierBalanceAuto(s_Swerve);
            case Barrier_Grab_Balance_Auto:
                return new BarrierGrabBalanceAuto(s_Swerve);
            case Barrier_Spit_Auto:
                return new BarrierSpitAuto(s_Swerve);
            case Barrier_Spit_Balance_Auto:
                return new BarrierSpitBalanceAuto(s_Swerve);
            case Turn_Test:
                return new TurnTest(s_Swerve);
            case Distance_Test:
                return new DistanceTest(s_Swerve);
            default:
                return new DefaultAuto(s_Swerve);
                //break; 
        }

        //return null;
    }

    public AutonomousSelector() {
        
    }

    private enum AutonomousMode {
        Two_Cone_Auto,
        Two_Cone_Balance_Auto,
        Center_Balance_Auto,
        Cone_Cube_Auto,
        Cone_Cube_Balance_Auto,
        Barrier_Balance_Auto,
        Barrier_Grab_Balance_Auto,
        Barrier_Spit_Auto,
        Barrier_Spit_Balance_Auto,
        Turn_Test,
        Distance_Test,
    }

}
