package ca.warp7.frc2020.subsystems.drivetrain;

import ca.warp7.frc2020.lib.control.PID;

/**
 * makes it easy to swap drive train motors (or simulate them)
 */
public interface DriveTrainVariant {

    void neutralOutput();

    void setPercentOutput(double leftPercent, double rightPercent);

    void setVelocityPID(
            double leftVelocityRotationsPerSecond, double rightVelocityRotationsPerSecond,
            double leftVoltage, double rightVoltage
    );

    void setPositionPID(double leftDistanceRotations, double rightDistanceRotations);

    void configurePID(PID pid);

    void configureRampRate(double secondsFromNeutralToFull);

    void setEncoderPosition(double leftRotations, double rightRotations);

    void setBrake();

    void setCoast();

    double getLeftPositionRotations();

    double getRightPositionRotations();

    double getLeftVelocityRPS();

    double getRightVelocityRPS();

    double getLeftVoltage();

    double getRightVoltage();

    double getLeftPIDErrorRotations();

    double getRightPIDErrorRotations();
}
