// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.grabber;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.util.Units;
import javax.inject.Inject;

public final class GrabberIOReal implements GrabberIO {
  private final TalonFX grabberMotor;
  private final StatusSignal<Double> velocity;

  @Inject
  public GrabberIOReal(GrabberConstants grabberConstants) {
    grabberMotor = new TalonFX(grabberConstants.armMotorId());
    velocity = grabberMotor.getVelocity();
    BaseStatusSignal.setUpdateFrequencyForAll(50.0, velocity);
  }

  @Override
  public void updateInputs(GrabberIOInputs inputs) {
    BaseStatusSignal.refreshAll(velocity);

    inputs.velocityRadPerSec = Units.rotationsPerMinuteToRadiansPerSecond(velocity.getValueAsDouble());
  }
  
  @Override
  public void setMotorOutputPercent(double outputPercent) {
    grabberMotor.set(outputPercent);
  }
}
