// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.hopper;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import javax.inject.Inject;

public final class HopperIOSim implements HopperIO {
  // TODO: modify this
  private SingleJointedArmSim armSim =
      new SingleJointedArmSim(DCMotor.getNEO(1), 50, 0.5, 0.5, 0.0, Math.PI / 2.0, true, 0.0);
  private double armAppliedVolts = 0.0;

  @Inject
  public HopperIOSim() {
    System.out.println("[Init] Creating HopperIOSim");
    armSim.setState(VecBuilder.fill(Math.PI / 2.0, 0.0));
  }

  @Override
  public void updateInputs(HopperIOInputs inputs) {
    // TODO: update the sim at the configured period
    // armSim.update(Constants.loopPeriodSecs);

    // TODO: update the inputs
  }

  @Override
  public void setArmVoltage(double voltage) {
    armAppliedVolts = MathUtil.clamp(voltage, -12.0, 12.0);
    armSim.setInputVoltage(armAppliedVolts);
  }
}
