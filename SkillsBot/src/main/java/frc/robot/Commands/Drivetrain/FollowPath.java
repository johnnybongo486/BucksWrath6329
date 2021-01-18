package frc.robot.Commands.Drivetrain;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.team319.trajectory.Path;
import com.team319.trajectory.Path.SegmentValue;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Models.CustomTalonFX;
import frc.robot.Subsystems.Drivetrain;

public class FollowPath extends Command {

    private CustomTalonFX rightTalon = Robot.Drivetrain.rightLead;
    private CustomTalonFX leftTalon = Robot.Drivetrain.leftLead;

    private int distancePidSlot = Drivetrain.DRIVE_PROFILE;
    private int rotationPidSlot = Drivetrain.ROTATION_PROFILE;

    private int kMinPointsInTalon = 5;

    private boolean isFinished = false;

    private Path pathToFollow = null;

    private MotionProfileStatus status = new MotionProfileStatus();

    private boolean hasPathStarted;

    /**
     * this is only either Disable, Enable, or Hold. Since we'd never want one side
     * to be enabled while the other is disabled, we'll use the same status for both
     * sides.
     */
    private SetValueMotionProfile setValue = SetValueMotionProfile.Disable;

    private class BufferLoader implements java.lang.Runnable {
        private int lastPointSent = 0;
        private CustomTalonFX talon;
        private Path prof;
        private final boolean flipped;
        private double startPosition = 0;

        public BufferLoader(CustomTalonFX rightTalon, Path prof, boolean flipped, double startPosition) {
            this.talon = rightTalon;
            this.prof = prof;
            this.flipped = flipped;
            this.startPosition = startPosition;
        }

        public void run() {
            talon.processMotionProfileBuffer();

            if (lastPointSent >= prof.getSegmentCount()) {
                return;
            }

            if (!talon.isMotionProfileTopLevelBufferFull() && lastPointSent < prof.getSegmentCount()) {
                TrajectoryPoint point = new TrajectoryPoint();
                /* for each point, fill our structure and pass it to API */
                point.position = prof.getValue(lastPointSent, SegmentValue.CENTER_POSITION) + startPosition;
                point.velocity = prof.getValue(lastPointSent, SegmentValue.CENTER_VELOCITY);
                point.timeDur = (int) (prof.getValue(lastPointSent, SegmentValue.TIME_STAMP) * 1000);
                point.auxiliaryPos = (flipped ? -1 : 1) * 10
                        * Math.toDegrees(prof.getValue(lastPointSent, SegmentValue.HEADING));
                point.profileSlotSelect0 = distancePidSlot;
                point.profileSlotSelect1 = rotationPidSlot;
                point.zeroPos = false;
                point.isLastPoint = false;
                if ((lastPointSent + 1) == prof.getSegmentCount()) {
                    point.isLastPoint = true; /** set this to true on the last point */
                }

                talon.pushMotionProfileTrajectory(point);
                lastPointSent++;
                hasPathStarted = true;
            }
        }
    }

    // Runs the runnable
    private Notifier loadLeftBuffer;

    public FollowPath(Path pathToFollow) {
        // addRequirements(Robot.Drivetrain);
        this.pathToFollow = pathToFollow;
    }

    // Called just before this Command runs the first time
    public void initialize() {

        setUpTalon(leftTalon);
        setUpTalon(rightTalon);

        setValue = SetValueMotionProfile.Disable;

        rightTalon.set(ControlMode.MotionProfileArc, setValue.value);
        leftTalon.follow(rightTalon, FollowerType.AuxOutput1);

        loadLeftBuffer = new Notifier(
                new BufferLoader(rightTalon, pathToFollow, false, Robot.Drivetrain.getDistance()));

        loadLeftBuffer.startPeriodic(.005);
    }

    // Called repeatedly when this Command is scheduled to run
    public void execute() {
        rightTalon.getMotionProfileStatus(status);

        if (status.isUnderrun) {
            // if either MP has underrun, stop both
            System.out.println("Motion profile has underrun!");
            setValue = SetValueMotionProfile.Disable;
        } else if (status.btmBufferCnt > kMinPointsInTalon) {
            // if we have enough points in the talon, go.
            setValue = SetValueMotionProfile.Enable;
        } else if (status.activePointValid && status.isLast) {
            // if both profiles are at their last points, hold the last point
            setValue = SetValueMotionProfile.Hold;
        }

        rightTalon.set(ControlMode.MotionProfileArc, setValue.value);
    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        if (!hasPathStarted) {
            return false;
        }
        boolean leftComplete = status.activePointValid && status.isLast;
        boolean trajectoryComplete = leftComplete;
        if (trajectoryComplete) {
            System.out.println("Finished trajectory");
        }
        return trajectoryComplete || isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
        loadLeftBuffer.stop();
        resetTalon(rightTalon, ControlMode.PercentOutput, 0);
        resetTalon(leftTalon, ControlMode.PercentOutput, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        loadLeftBuffer.stop();
        resetTalon(rightTalon, ControlMode.PercentOutput, 0);
        resetTalon(leftTalon, ControlMode.PercentOutput, 0);
    }

    // set up the talon for motion profile control
    private void setUpTalon(CustomTalonFX leftTalon2) {
        leftTalon2.clearMotionProfileTrajectories();
        leftTalon2.changeMotionControlFramePeriod(5);
        leftTalon2.clearMotionProfileHasUnderrun(10);
    }

    // set the to the desired controlMode
    // used at the end of the motion profile
    private void resetTalon(CustomTalonFX rightTalon2, ControlMode controlMode, double setValue) {
        rightTalon2.clearMotionProfileTrajectories();
        rightTalon2.clearMotionProfileHasUnderrun(10);
        rightTalon2.changeMotionControlFramePeriod(10);
        rightTalon2.set(controlMode, setValue);
    }
}