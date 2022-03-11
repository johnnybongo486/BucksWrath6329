package frc.robot.Commands.Auto;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Commands.Auto.AutonomousCommandGroups.DriveThreeFeet;
import frc.robot.Commands.Auto.AutonomousCommandGroups.LeftFourBall;
import frc.robot.Commands.Auto.AutonomousCommandGroups.LeftTwoBall;
import frc.robot.Commands.Auto.AutonomousCommandGroups.RightFiveBall;
import frc.robot.Commands.Auto.AutonomousCommandGroups.RightFourBall;
import frc.robot.Commands.Auto.AutonomousCommandGroups.RightFourBallNHP;
import frc.robot.Commands.Auto.AutonomousCommandGroups.RightThreeBall;
import frc.robot.Commands.Auto.AutonomousCommandGroups.Turn90Degrees;

public class AutonomousSelector {

    private static SendableChooser<AutonomousMode> autonomousModeChooser;

    static {
        ShuffleboardTab autoTab = Shuffleboard.getTab("Auto settings");

        autonomousModeChooser = new SendableChooser<>();
        autonomousModeChooser.addOption("Drive Three Feet", AutonomousMode.Drive_Three_Feet);
        autonomousModeChooser.addOption("Turn 90 Degrees", AutonomousMode.Turn_90_Degrees);
        autonomousModeChooser.addOption("Left Two Ball", AutonomousMode.Left_Two_Ball);
        autonomousModeChooser.addOption("Left Four Ball", AutonomousMode.Left_Four_Ball);
        autonomousModeChooser.addOption("Right Three Ball", AutonomousMode.Right_Three_Ball);
        autonomousModeChooser.addOption("Right Four Ball", AutonomousMode.Right_Four_Ball);
        autonomousModeChooser.addOption("Right Four Ball No HP", AutonomousMode.Right_Four_Ball_NHP);
        autonomousModeChooser.addOption("Right Five Ball", AutonomousMode.Right_Five_Ball);
        
        autoTab.add("Mode", autonomousModeChooser);
        
    }

    public Command getCommand(){
        AutonomousMode mode = autonomousModeChooser.getSelected();

        switch (mode) {
            case Drive_Three_Feet:
                return new DriveThreeFeet();
            case Turn_90_Degrees:
                return new Turn90Degrees();
            case Left_Four_Ball:
                return new LeftFourBall();
            case Right_Five_Ball:
                return new RightFiveBall();
            case Left_Two_Ball:
                return new LeftTwoBall();
            case Right_Four_Ball:
                return new RightFourBall();
            case Right_Four_Ball_NHP:
                return new RightFourBallNHP();
            case Right_Three_Ball:
                return new RightThreeBall();
            default:
                System.out.println("ERROR: unexpected auto mode: " + mode);
                break; 
        }

        return null;
    }

    public AutonomousSelector() {
    }

    private enum AutonomousMode {
        Drive_Three_Feet,
        Turn_90_Degrees,
        Left_Four_Ball,
        Right_Five_Ball,
        Left_Two_Ball,
        Right_Four_Ball,
        Right_Three_Ball,
        Right_Four_Ball_NHP
    }

}
