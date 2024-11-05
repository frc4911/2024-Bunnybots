// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.drive;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotGearing;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotMotor;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotWheelSize;
import javax.inject.Inject;

public final class DriveIOSim implements DriveIO {
  private static final double KP = 0.2;
  private static final double KD = 0.0;

  private final DriveConstants constants;
  private DifferentialDrivetrainSim sim =
      DifferentialDrivetrainSim.createKitbotSim(
          KitbotMotor.kDualCIMPerSide, KitbotGearing.k10p71, KitbotWheelSize.kSixInch, null);
  private double leftAppliedVolts = 0.0;
  private double rightAppliedVolts = 0.0;
  private boolean closedLoop = false;
  private PIDController leftPID = new PIDController(KP, 0.0, KD);
  private PIDController rightPID = new PIDController(KP, 0.0, KD);
  private double leftFFVolts = 0.0;
  private double rightFFVolts = 0.0;

  @Inject
  public DriveIOSim(DriveConstants constants) {
    this.constants = constants;
  }

  @Override
  public void updateInputs(DriveIOInputs inputs) {
    if (closedLoop) {
      leftAppliedVolts =
          MathUtil.clamp(
              leftPID.calculate(sim.getLeftVelocityMetersPerSecond() / constants.wheelRadius())
                  + leftFFVolts,
              -12.0,
              12.0);
      rightAppliedVolts =
          MathUtil.clamp(
              leftPID.calculate(sim.getRightVelocityMetersPerSecond() / constants.wheelRadius())
                  + rightFFVolts,
              -12.0,
              12.0);
      sim.setInputs(leftAppliedVolts, rightAppliedVolts);
    }

    sim.update(0.02);
    inputs.leftPositionRad = sim.getLeftPositionMeters() / constants.wheelRadius();
    inputs.leftVelocityRadPerSec = sim.getLeftVelocityMetersPerSecond() / constants.wheelRadius();
    inputs.leftAppliedVolts = leftAppliedVolts;
    inputs.leftCurrentAmps = new double[] {sim.getLeftCurrentDrawAmps()};

    inputs.rightPositionRad = sim.getRightPositionMeters() / constants.wheelRadius();
    inputs.rightVelocityRadPerSec = sim.getRightVelocityMetersPerSecond() / constants.wheelRadius();
    inputs.rightAppliedVolts = rightAppliedVolts;
    inputs.rightCurrentAmps = new double[] {sim.getRightCurrentDrawAmps()};

    inputs.gyroYaw = sim.getHeading();
  }

  @Override
  public void setVoltage(double leftVolts, double rightVolts) {
    closedLoop = false;
    leftAppliedVolts = MathUtil.clamp(leftVolts, -12.0, 12.0);
    rightAppliedVolts = MathUtil.clamp(rightVolts, -12.0, 12.0);
    sim.setInputs(leftAppliedVolts, rightAppliedVolts);
  }

  @Override
  public void setVelocity(
      double leftRadPerSec, double rightRadPerSec, double leftFFVolts, double rightFFVolts) {
    closedLoop = true;
    leftPID.setSetpoint(leftRadPerSec);
    rightPID.setSetpoint(rightRadPerSec);
    this.leftFFVolts = leftFFVolts;
    this.rightFFVolts = rightFFVolts;
  }
}
