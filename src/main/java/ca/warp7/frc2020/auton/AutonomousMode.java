package ca.warp7.frc2020.auton;

import ca.warp7.frc2020.auton.commands.*;
import ca.warp7.frc2020.commands.FlywheelSpeedCommand;
import ca.warp7.frc2020.commands.IntakingCommand;
import ca.warp7.frc2020.commands.SingleFunctionCommand;
import ca.warp7.frc2020.subsystems.Flywheel;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

@SuppressWarnings("unused")
public class AutonomousMode {

    private static Command getShootCellsCommand(int numBalls) {
        return new WaitForShotsCommand(numBalls).deadlineWith(
                new FlywheelSpeedCommand(Flywheel::getOptimalCloseShotRPS),
                new AutoFeedCommand(() -> true)
        );
    }

    public static Command shootThreeBalls() {
        return new ShootBallsCloseCommand(3);
    }

    public static Command shoot3_backup() {
        return new SequentialCommandGroup(
                SingleFunctionCommand
                        .getResetAutonomousDrive(),
                new RobotStateCommand(new Pose2d()),
                getShootCellsCommand(3),
                AutonomousPath.getOneMetreForward()
        );
    }

    public static Command opposite_intake2_shoot5() {
        return new SequentialCommandGroup(
                SingleFunctionCommand
                        .getResetAutonomousDrive(),
                new RobotStateCommand(AutonomousPath.kLeftInitLine),
                AutonomousPath.getOpponentTrenchTwoBalls()
                        .deadlineWith(
                                IntakingCommand.fullPower(),
                                new AutoFeedCommand(() -> false)
                        ),
                AutonomousPath.getOpponentTrechTwoBallsToShoot()
                        .deadlineWith(
                                IntakingCommand.neutral(),
                                new AutoFeedCommand(() -> false)
                        ),

                getShootCellsCommand(3)
                        .deadlineWith(
                                new VisionAlignCommand(() -> 0.0)
                        )
        );
    }

    public static Command shoot3_intake3_shoot3() {
        return new SequentialCommandGroup(
                SingleFunctionCommand.getResetAutonomousDrive(),
                new RobotStateCommand(AutonomousPath.kRightSideFacingOuterGoal),
                SingleFunctionCommand.getFlywheelSetHoodCloseCommand(),
                getShootCellsCommand(3),

                AutonomousPath.getTrenchThreeBalls()
                        .deadlineWith(
                                IntakingCommand.fullPower(),
                                new AutoFeedCommand(() -> false)
                        ),
                AutonomousPath.getTrenchThreeBallsToCorner()
                        .deadlineWith(
                                IntakingCommand.neutral(),
                                new AutoFeedCommand(() -> false)
                        ),

                getShootCellsCommand(5)
                        .deadlineWith(
                                new VisionAlignCommand(() -> 0.0),
                                new IntakingCommand(() -> 0.5)
                        )
        );
    }

    public static Command intakeThreeBalls() {
        return new SequentialCommandGroup(
                SingleFunctionCommand.getResetAutonomousDrive(),
                new RobotStateCommand(AutonomousPath.kRightSideFacingOuterGoal),
                new ParallelDeadlineGroup(
                        AutonomousPath.getTrenchThreeBalls(),
                        new IntakingCommand(() -> 1.0)
                ),
                new ParallelDeadlineGroup(
                        AutonomousPath.getTrenchThreeBallsToCorner(),
                        new IntakingCommand(() -> 0.0)
                )
        );
    }

    public static Command driveLowGearCharacterization() {
        return new SequentialCommandGroup(
                SingleFunctionCommand.getResetAutonomousDrive(),
                SingleFunctionCommand.getStopCompressor(),
                new DriveCharacterizationCommand()
        );
    }

    public static Command driveHighGearCharacterization() {
        return new SequentialCommandGroup(
                SingleFunctionCommand.getResetAutonomousDrive(),
                SingleFunctionCommand.getSetDriveHighGear(),
                SingleFunctionCommand.getStopCompressor(),
                new DriveCharacterizationCommand()
        );
    }

    public static Command simplePath() {
        return new SequentialCommandGroup(
                SingleFunctionCommand.getResetAutonomousDrive(),
                new RobotStateCommand(AutonomousPath.kRightSideFacingOuterGoal),
                AutonomousPath.getTrenchOneBall()
        );
    }
}
