// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.toyota;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.util.Units;
import javax.inject.Inject;

public final class ToyotaIOReal implements ToyotaIO {
  private final TalonFX toyotaMotor;
  private final StatusSignal<Double> velocity;

  @Inject
  public ToyotaIOReal(ToyotaConstants toyotaConstants) {
    toyotaMotor = new TalonFX(toyotaConstants.rollerMotorId());
    velocity = toyotaMotor.getVelocity();
    BaseStatusSignal.setUpdateFrequencyForAll(50.0, velocity);
  }

  @Override
  public void updateInputs(ToyotaIOInputs inputs) {
    BaseStatusSignal.refreshAll(velocity);

    inputs.rollerVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(velocity.getValueAsDouble());
  }

  @Override
  public void setArmVoltage(double volts) {
    // TODO: pass this on to the motor
  }

  @Override
  public void setRollerVoltage(double volts) {
    // TODO: pass this on to the motor
  }

  @Override
  public void setBrakeMode(boolean armBrake, boolean rollerBrake) {
    // TODO: pass this on to the motors
  }

  @Override
  public void setMotorOutputPercent(double outputPercent) {
    toyotaMotor.set(outputPercent);
  }
}
