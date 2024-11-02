// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.toyota;

import javax.inject.Inject;

public final class ToyotaIOReal implements ToyotaIO {

  @Inject
  public ToyotaIOReal() {
    // TODO: set up motors and encoders here
  }

  @Override
  public void updateInputs(ToyotaIOInputs inputs) {
    // TODO: set all the values on inputs
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
}
