// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.grabber;

import com.ctre.phoenix6.hardware.TalonFX;
import javax.inject.Inject;

public final class GrabberIOReal implements GrabberIO {
  private final TalonFX arm;

  @Inject
  public GrabberIOReal(GrabberConstants grabberConstants) {
    arm = new TalonFX(grabberConstants.armMotorId());
  }

  @Override
  public void updateInputs(GrabberIOInputs inputs) {
    // TODO: set all the values on inputs
  }
}
