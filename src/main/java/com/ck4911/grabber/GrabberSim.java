// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.grabber;

import javax.inject.Inject;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import org.littletonrobotics.junction.AutoLog;

public final class GrabberIOSim implements GrabberIO {

  @Inject
  public GrabberIOSim() {
    System.out.println("[Init] Creating GrabberIOSim");
  }

  @Override
  public void updateInputs(GrabberIOInputs inputs) {
    // TODO: update the inputs
  }
}
