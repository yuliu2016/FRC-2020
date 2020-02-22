package ca.warp7.frc2020.commands;

import ca.warp7.frc2020.auton.vision.Limelight;
import ca.warp7.frc2020.subsystems.Climber;
import ca.warp7.frc2020.subsystems.DriveTrain;
import ca.warp7.frc2020.subsystems.Flywheel;
import ca.warp7.frc2020.subsystems.Intake;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;

import static ca.warp7.frc2020.Constants.*;

@SuppressWarnings("unused")
public class SingleFunctionCommand {
    public static Command getSetDriveHighGear() {
        DriveTrain driveTrain = DriveTrain.getInstance();
        return new InstantCommand(() -> {
            driveTrain.configureRampRate(kHighGearRampRate);
            driveTrain.configurePID(kTeleopHighGearVelocityPID);
            driveTrain.setHighGear(true);
        });
    }

    public static Command  getSetDriveLowGear() {
        DriveTrain driveTrain = DriveTrain.getInstance();
        return new InstantCommand(() -> {
            driveTrain.configureRampRate(kLowGearRampRate);
            driveTrain.configurePID(kTeleopLowGearVelocityPID);
            driveTrain.setHighGear(false);
        });
    }

    public static Command getSetDriveAutonomousLowGear() {
        DriveTrain driveTrain = DriveTrain.getInstance();
        return new InstantCommand(() -> {
            driveTrain.configurePID(kAutonLowGearVelocityPID);
            driveTrain.setHighGear(false);
        });
    }

    public static Command getSetDriveBrakeMode() {
        DriveTrain driveTrain = DriveTrain.getInstance();
        return new InstantCommand(driveTrain::setBrake);
    }

    public static Command getSetDriveCoastMode() {
        DriveTrain driveTrain = DriveTrain.getInstance();
        return new InstantCommand(driveTrain::setCoast);
    }

    public static Command getClimbLockToggle() {
        Climber climber = Climber.getInstance();
        return new InstantCommand(climber::toggleLock);
    }
    
    public static Command getZeroYaw() {
        DriveTrain driveTrain = DriveTrain.getInstance();
        return new InstantCommand(driveTrain::zeroYaw);
    }

    public static Command getResetRobotState() {
        DriveTrain driveTrain = DriveTrain.getInstance();
        return new InstantCommand(driveTrain::resetRobotState);
    }

    public static Command getRobotStateEstimation() {
        DriveTrain driveTrain = DriveTrain.getInstance();
        return new RunCommand(driveTrain::updateRobotStateEstimation);
    }

    public static Command getReportRobotState() {
        DriveTrain driveTrain = DriveTrain.getInstance();
        return new RunCommand(() -> System.out.println("Robot State: " + driveTrain.getRobotState()));
    }

    public static Command getIntakeExtensionToggle() {
        Intake intake = Intake.getInstance();
        return new InstantCommand(intake::toggle);
    }

    public static Command getFlywheelHoodToggle() {
        Flywheel flywheel = Flywheel.getInstance();
        return new InstantCommand(flywheel::toggleHood);
    }

    public static Command getLimelightGetPoseCommand() {
        Limelight limelight = Limelight.getInstance();
        return new RunCommand(limelight::getRobotToTarget);
    }

    public static Command getStartCompressor() {
        Compressor compressor = CompressorSingleton.getInstance();
        return new InstantCommand(compressor::start);
    }

    public static Command getStopCompressor() {
        Compressor compressor = CompressorSingleton.getInstance();
        return new InstantCommand(compressor::stop);
    }

    /**
     * Holds a single compressor object, to be used by start and stop
     * compressor commands.
     */
    private static class CompressorSingleton {
        private static final Compressor instance = new Compressor();

        public static Compressor getInstance() {
            return instance;
        }
    }
}
