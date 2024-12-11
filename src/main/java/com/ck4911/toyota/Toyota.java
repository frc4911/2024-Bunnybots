// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.toyota;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class Toyota extends SubsystemBase {

  private final ToyotaIO io;

  @Inject
  public Toyota(ToyotaIO io) {
    this.io = io;
  }

  public void setMotorOutputPercent(double outputPercent) {
    io.setMotorOutputPercent(outputPercent);
  }
}
