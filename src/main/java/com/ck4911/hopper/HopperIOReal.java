// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.hopper;

import javax.inject.Inject;

public final class HopperIOReal implements HopperIO {

  @Inject
  public HopperIOReal() {
    // TODO: set up motors and encoders here
  }

  @Override
  public void updateInputs(HopperIOInputs inputs) {
    // TODO: set all the values on inputs
  }

  @Override
  public void setArmVoltage(double volts) {
    // TODO: pass this on to the motor
  }

  @Override
  public void setBrakeMode(boolean armBrake) {
    // TODO: pass this on to the motor
  }
}
