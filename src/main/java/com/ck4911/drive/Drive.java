// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.drive;

import static edu.wpi.first.units.Units.Volts;

import com.ck4911.commands.FeedForwardCharacterization;
import com.ck4911.commands.StaticCharacterization.StaticCharacterizationFactory;
import com.ck4911.util.LoggedTunableNumber;
import com.ck4911.util.LoggedTunableNumber.TunableNumbers;
import com.ck4911.robot.Mode;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import java.util.concurrent.atomic.AtomicReference;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;

@Singleton
public final class Drive extends SubsystemBase {
  private final LoggedTunableNumber KP;
  private final LoggedTunableNumber KD;
  private final GyroIO gyroIo;
  private final GyroIOInputsAutoLogged gyroInputs;
  private final DriveIO driveIo;
  private final DriveConstants constants;
  private final DriveIOInputsAutoLogged driveInputs;
  private final DifferentialDriveOdometry odometry;
  private final DifferentialDriveKinematics kinematics;
  private final SimpleMotorFeedforward feedforward;
  private final double ks;
  private final double kv;
  private final SysIdRoutine sysId;
  private final Command staticCharacterization;
  private final Command feedForwardCharacterization;

  @Inject
  public Drive(
      Mode mode,
      TunableNumbers tunableNumbers,
      GyroIO gyroIo,
      GyroIOInputsAutoLogged gyroInputs,
      DriveIO driveIo,
      DriveConstants constants,
      DriveIOInputsAutoLogged driveInputs,
      DifferentialDriveOdometry odometry,
      DifferentialDriveKinematics kinematics,
      StaticCharacterizationFactory staticCharacterizationFactory) {
    this.gyroIo = gyroIo;
    this.gyroInputs = gyroInputs;
    this.driveIo = driveIo;
    this.constants = constants;
    this.driveInputs = driveInputs;
    this.odometry = odometry;
    this.kinematics = kinematics;

    KP = tunableNumbers.create("Drive/KP", 1.0); // TODO: MUST BE TUNED, consider using REV Hardware Client
    KD = tunableNumbers.create("Drive/KD", 0.0); // TODO: MUST BE TUNED, consider using REV Hardware Client

    // TODO: NON-SIM FEEDFORWARD GAINS MUST BE TUNED
    // Consider using SysId routines
    ks = mode == Mode.SIM ? 0.0 : 0.0;
    kv = mode == Mode.SIM ? 0.227 : 0.0;
    feedforward = new SimpleMotorFeedforward(ks, kv);

    staticCharacterization =
        staticCharacterizationFactory.create(
            this, this::driveVolts, this::getCharacterizationVelocity);

    feedForwardCharacterization =
        new FeedForwardCharacterization(this, this::driveVolts, this::getCharacterizationVelocity);

    // Configure SysId
    sysId =
        new SysIdRoutine(
            new SysIdRoutine.Config(
                null,
                null,
                null,
                (state) -> Logger.recordOutput("Drive/SysIdState", state.toString())),
            new SysIdRoutine.Mechanism(
                (voltage) -> driveVolts(voltage.in(Volts), voltage.in(Volts)), null, this));
  }

  @Override
  public void periodic() {
    driveIo.updateInputs(driveInputs);
    gyroIo.updateInputs(gyroInputs);

    Logger.processInputs("Drive", driveInputs);
    Logger.processInputs("Gyro", gyroInputs);

    // Update odometry
    odometry.update(gyroInputs.yawPosition, getLeftPositionMeters(), getRightPositionMeters());

    LoggedTunableNumber.ifChanged(
        hashCode(), () -> driveIo.setPid(KP.get(), 0, KD.get()), KP, KD);
  }

  /** Run open loop at the specified voltage. */
  public void driveVolts(double volts) {
    driveIo.setVoltage(volts, volts);
  }

  /** Run open loop at the specified voltage. */
  public void driveVolts(double leftVolts, double rightVolts) {
    driveIo.setVoltage(leftVolts, rightVolts);
  }

  /** Run closed loop at the specified voltage. */
  public void driveVelocity(double leftMetersPerSec, double rightMetersPerSec) {
    Logger.recordOutput("Drive/LeftVelocitySetpointMetersPerSec", leftMetersPerSec);
    Logger.recordOutput("Drive/RightVelocitySetpointMetersPerSec", rightMetersPerSec);
    double leftRadPerSec = leftMetersPerSec / constants.wheelRadius();
    double rightRadPerSec = rightMetersPerSec / constants.wheelRadius();
    driveIo.setVelocity(
        leftRadPerSec,
        rightRadPerSec,
        feedforward.calculate(leftRadPerSec),
        feedforward.calculate(rightRadPerSec));
  }

  /** Run open loop based on stick positions. */
  public void driveArcade(double xSpeed, double zRotation) {
    var speeds = DifferentialDrive.arcadeDriveIK(xSpeed, zRotation, true);
    driveIo.setVoltage(speeds.left * 12.0, speeds.right * 12.0);
  }

  /** Stops the drive. */
  public void stop() {
    driveIo.setVoltage(0.0, 0.0);
  }

  /** Returns a command to run a quasistatic test in the specified direction. */
  public Command sysIdQuasistatic(SysIdRoutine.Direction direction) {
    return sysId.quasistatic(direction);
  }

  /** Returns a command to run a dynamic test in the specified direction. */
  public Command sysIdDynamic(SysIdRoutine.Direction direction) {
    return sysId.dynamic(direction);
  }

  /** Returns the current odometry pose in meters. */
  /** TODO: Update AdvantageKit or manually log @AutoLogOutput(key = "Odometry/Robot") */
  public Pose2d getPose() {
    return odometry.getPoseMeters();
  }

  public Command staticCharacterization() {
    return staticCharacterization;
  }

  public Command feedForwardCharacterization() {
    return feedForwardCharacterization;
  }

  public Command measureDistance(Command command) {
    AtomicReference<Double> leftStart = new AtomicReference<>();
    AtomicReference<Double> rightStart = new AtomicReference<>();
    return Commands.runOnce(
            () -> {
              leftStart.set(driveInputs.leftPositionRad);
              rightStart.set(driveInputs.rightPositionRad);
            })
        .andThen(command)
        .andThen(
            Commands.runOnce(
                () -> {
                  double leftDistanceTraveled = driveInputs.leftPositionRad - leftStart.get();
                  double rightDistanceTraveled = driveInputs.rightPositionRad - rightStart.get();
                  double averageDistance = (rightDistanceTraveled + leftDistanceTraveled) / 2;
                  System.out.println("Distance traveled = " + averageDistance + "! 💀");
                }));
  }

  /** Resets the current odometry pose. */
  public void setPose(Pose2d pose) {
    odometry.resetPosition(
        gyroInputs.yawPosition, getLeftPositionMeters(), getRightPositionMeters(), pose);
  }

  public ChassisSpeeds getSpeeds() {
    return kinematics.toChassisSpeeds(
        new DifferentialDriveWheelSpeeds(
            getLeftVelocityMetersPerSec(), getRightVelocityMetersPerSec()));
  }

  public void setSpeeds(ChassisSpeeds speeds) {
    var wheelSpeeds = kinematics.toWheelSpeeds(speeds);
    driveVelocity(wheelSpeeds.leftMetersPerSecond, wheelSpeeds.rightMetersPerSecond);
  }

  /** Returns the position of the left wheels in meters. */
  @AutoLogOutput
  public double getLeftPositionMeters() {
    return driveInputs.leftPositionRad * constants.wheelRadius();
  }

  /** Returns the position of the right wheels in meters. */
  @AutoLogOutput
  public double getRightPositionMeters() {
    return driveInputs.rightPositionRad * constants.wheelRadius();
  }

  /** Returns the velocity of the left wheels in meters/second. */
  @AutoLogOutput
  public double getLeftVelocityMetersPerSec() {
    return driveInputs.leftVelocityRadPerSec * constants.wheelRadius();
  }

  /** Returns the velocity of the right wheels in meters/second. */
  @AutoLogOutput
  public double getRightVelocityMetersPerSec() {
    return driveInputs.rightVelocityRadPerSec * constants.wheelRadius();
  }

  /** Returns the average velocity in radians/second. */
  public double getCharacterizationVelocity() {
    return (driveInputs.leftVelocityRadPerSec + driveInputs.rightVelocityRadPerSec) / 2.0;
  }
}
