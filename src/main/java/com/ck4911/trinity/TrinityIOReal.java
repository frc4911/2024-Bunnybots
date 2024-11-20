// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.trinity;

import javax.inject.Inject;

public final class TrinityIOReal implements TrinityIO {

  @Inject
  public TrinityIOReal(TrinityConstants trinityConstants) {
    // TODO: set up motors and encoders here
  }

  @Override
  public void updateInputs(TrinityIOInputs inputs) {
    // TODO: set all the values on inputs
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
}
