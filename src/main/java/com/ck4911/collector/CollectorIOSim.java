// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.collector;

import javax.inject.Inject;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import org.littletonrobotics.junction.AutoLog;

public final class CollectorIOSim implements CollectorIO {
  // TODO: modify this
  private SingleJointedArmSim armSim =
      new SingleJointedArmSim(DCMotor.getNEO(1), 50, 0.5, 0.5, 0.0, Math.PI / 2.0, true, 0.0);
  private double armAppliedVolts = 0.0;

  @Inject
  public CollectorIOSim() {
    System.out.println("[Init] Creating CollectorIOSim");
    armSim.setState(VecBuilder.fill(Math.PI / 2.0, 0.0));
  }

  @Override
  public void updateInputs(CollectorIOInputs inputs) {
    // TODO: update the sim at the configured period
    // armSim.update(Constants.loopPeriodSecs);

    // TODO: update the inputs
  }

  @Override
  public void setArmVoltage(double voltage) {
    armAppliedVolts = MathUtil.clamp(voltage, -12.0, 12.0);
    armSim.setInputVoltage(armAppliedVolts);
  }

  @Override
  public void setRollerVoltage(double voltage) {}
}
