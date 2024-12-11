// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.drive;

import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import javax.inject.Inject;

public final class GyrIOSim implements GyroIO {
  private final DifferentialDrivetrainSim sim;

  @Inject
  public GyrIOSim(DifferentialDrivetrainSim sim) {
    this.sim = sim;
  }

  @Override
  public void updateInputs(GyroIOInputs inputs) {
    inputs.yawPosition = sim.getHeading();
  }
}
