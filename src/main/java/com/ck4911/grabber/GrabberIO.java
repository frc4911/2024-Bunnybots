// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.grabber;

import org.littletonrobotics.junction.AutoLog;

public interface GrabberIO {
  @AutoLog
  public static class GrabberIOInputs {
    public double velocityRadPerSec = 0.0;
    // TODO: add fields here similar to those in TrinityIOInputs
  }

  /** Updates the set of loggable inputs. */
  public default void updateInputs(GrabberIOInputs inputs) {
  }
  
  public default void setMotorOutputPercent(double outputPercent) {}
}
