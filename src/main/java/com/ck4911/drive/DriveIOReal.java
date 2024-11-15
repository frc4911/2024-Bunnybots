// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.drive;

import com.ck4911.drive.Location.Corner;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import edu.wpi.first.math.util.Units;
import javax.inject.Inject;

public class DriveIOReal implements DriveIO {

  private final CANSparkFlex leftLeader;
  private final CANSparkFlex rightLeader;
  private final CANSparkFlex leftFollower;
  private final CANSparkFlex rightFollower;
  private final RelativeEncoder leftEncoder;
  private final RelativeEncoder rightEncoder;
  private final SparkPIDController leftPID;
  private final SparkPIDController rightPID;
  private final DriveConstants constants;

  @Inject
  public DriveIOReal(
      DriveConstants constants,
      @Location(Corner.FRONT_LEFT) CANSparkFlex leftLeader,
      @Location(Corner.FRONT_RIGHT) CANSparkFlex rightLeader,
      @Location(Corner.BACK_LEFT) CANSparkFlex leftFollower,
      @Location(Corner.BACK_RIGHT) CANSparkFlex rightFollower) {
    this.leftLeader = leftLeader;
    this.rightLeader = rightLeader;
    this.leftFollower = leftFollower;
    this.rightFollower = rightFollower;
    this.constants = constants;

    leftEncoder = leftLeader.getEncoder();
    rightEncoder = rightLeader.getEncoder();
    leftPID = leftLeader.getPIDController();
    rightPID = rightLeader.getPIDController();

    leftLeader.restoreFactoryDefaults();
    rightLeader.restoreFactoryDefaults();
    leftFollower.restoreFactoryDefaults();
    rightFollower.restoreFactoryDefaults();

    leftLeader.setCANTimeout(250);
    rightLeader.setCANTimeout(250);
    leftFollower.setCANTimeout(250);
    rightFollower.setCANTimeout(250);

    leftLeader.setInverted(false);
    rightLeader.setInverted(true);
    leftFollower.follow(leftLeader, false);
    rightFollower.follow(rightLeader, false);

    leftLeader.enableVoltageCompensation(12.0);
    rightLeader.enableVoltageCompensation(12.0);
    leftLeader.setSmartCurrentLimit(60);
    rightLeader.setSmartCurrentLimit(60);

    leftLeader.burnFlash();
    rightLeader.burnFlash();
    leftFollower.burnFlash();
    rightFollower.burnFlash();
  }

  @Override
  public void updateInputs(DriveIOInputs inputs) {
    inputs.leftPositionRad = Units.rotationsToRadians(leftEncoder.getPosition() / constants.gearRatio());
    inputs.leftVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(leftEncoder.getVelocity() / constants.gearRatio());
    inputs.leftAppliedVolts = leftLeader.getAppliedOutput() * leftLeader.getBusVoltage();
    inputs.leftCurrentAmps =
        new double[] {leftLeader.getOutputCurrent(), leftFollower.getOutputCurrent()};

    inputs.rightPositionRad = Units.rotationsToRadians(rightEncoder.getPosition() / constants.gearRatio());
    inputs.rightVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(rightEncoder.getVelocity() / constants.gearRatio());
    inputs.rightAppliedVolts = rightLeader.getAppliedOutput() * rightLeader.getBusVoltage();
    inputs.rightCurrentAmps =
        new double[] {rightLeader.getOutputCurrent(), rightFollower.getOutputCurrent()};
  }

  @Override
  public void setPid(double kP, double kI, double kD) {
    leftPID.setP(kP);
    leftPID.setD(kD);
    rightPID.setP(kP);
    rightPID.setD(kD);
  }

  @Override
  public void setVoltage(double leftVolts, double rightVolts) {
    leftLeader.setVoltage(leftVolts);
    rightLeader.setVoltage(rightVolts);
  }

  @Override
  public void setVelocity(
      double leftRadPerSec, double rightRadPerSec, double leftFFVolts, double rightFFVolts) {
    leftPID.setReference(
        Units.radiansPerSecondToRotationsPerMinute(leftRadPerSec * constants.gearRatio()),
        ControlType.kVelocity,
        0,
        leftFFVolts);
    rightPID.setReference(
        Units.radiansPerSecondToRotationsPerMinute(rightRadPerSec * constants.gearRatio()),
        ControlType.kVelocity,
        0,
        rightFFVolts);
  }
}
