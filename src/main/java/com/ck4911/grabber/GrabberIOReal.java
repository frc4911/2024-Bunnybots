// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.grabber;

import javax.inject.Inject;
import com.ctre.phoenix6.hardware.TalonFX;

public final class GrabberIOReal implements GrabberIO {
  private final TalonFX axle;
  private final TalonFX roller;

  @Inject
  public GrabberIOReal() {
    axle = new TalonFX(GrabberConstants.AXLE_ID);
    roller = new TalonFX(GrabberConstants.ROLLER_ID);
  }

  @Override
  public void updateInputs(GrabberIOInputs inputs) {
    // TODO: set all the values on inputs
  }
}
