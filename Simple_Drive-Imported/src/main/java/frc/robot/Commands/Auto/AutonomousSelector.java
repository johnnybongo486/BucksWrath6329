package frc.robot.Commands.Auto;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Commands.Auto.AutonomousCommandGroups.DriveThreeFeet;

public class AutonomousSelector {

    private static SendableChooser<AutonomousMode> autonomousModeChooser;

    static {
        ShuffleboardTab autoTab = Shuffleboard.getTab("Auto settings");

        autonomousModeChooser = new SendableChooser<>();
        autonomousModeChooser.addOption("Drive Three Feet", AutonomousMode.Drive_Three_Feet);
        //autonomousModeChooser.addOption("Test Straight", AutonomousMode.TEST_STRAIGHT_PATH);
        //autonomousModeChooser.addOption("Center Three Back", AutonomousMode.CENTER_THREE_BACK);
        //autonomousModeChooser.addOption("Center Three Front", AutonomousMode.CENTER_THREE_FRONT);
        //autonomousModeChooser.addOption("Left Six Far", AutonomousMode.LEFT_SIX_FAR);
        //autonomousModeChooser.addOption("Left Six Near", AutonomousMode.LEFT_SIX_NEAR);
        autoTab.add("Mode", autonomousModeChooser);
        
    }

    public Command getCommand(){
        AutonomousMode mode = autonomousModeChooser.getSelected();

        switch (mode) {
            case Drive_Three_Feet:
                return new DriveThreeFeet();
            //case TEST_STRAIGHT_PATH:
            //    return new TestStraightPath(drivetrain);
            //case CENTER_THREE_BACK:
            //    return new CenterThreeBack(drivetrain);
            //case CENTER_THREE_FRONT:
            //    return new CenterThreeFront(drivetrain);
            //case LEFT_SIX_FAR:
            //    return new LeftSixFarMode(drivetrain);
            //case LEFT_SIX_NEAR:
            //    return new LeftSixNearMode(drivetrain);
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
        TEST_STRAIGHT_PATH,
        CENTER_THREE_BACK,
        CENTER_THREE_FRONT,
        LEFT_SIX_FAR,
        LEFT_SIX_NEAR
    }

}
