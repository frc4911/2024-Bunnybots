// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.trinity;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.util.Units;
import javax.inject.Inject;

public final class TrinityIOReal implements TrinityIO {
  private final TalonFX trinityMotor;
  private final StatusSignal<Double> velocity;

  @Inject
  public TrinityIOReal(TrinityConstants trinityConstants) {
    trinityMotor = new TalonFX(trinityConstants.rollerMotorId());
    velocity = trinityMotor.getVelocity();
    BaseStatusSignal.setUpdateFrequencyForAll(50.0, velocity);
  }

  @Override
  public void updateInputs(TrinityIOInputs inputs) {
    BaseStatusSignal.refreshAll(velocity);
    inputs.velocityRadsPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(velocity.getValueAsDouble());
  }

  @Override
  public void runSetpoint(double setpointRads, double feedforward) {}

  @Override
  public void runVolts(double volts) {
    // TODO: pass this on to the motor
  }

  @Override
  public void setBrakeMode(boolean armBrake) {
    // TODO: pass this on to the motor
  }

  @Override
  public void setMotorOutputPercent(double outputPercent) {
    trinityMotor.set(outputPercent);
  }
}
