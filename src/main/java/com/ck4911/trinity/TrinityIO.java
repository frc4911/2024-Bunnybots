// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.trinity;

import org.littletonrobotics.junction.AutoLog;

public interface TrinityIO {
  @AutoLog
  public static class TrinityIOInputs {
    public boolean isMotorConnected = true;
    public double positionRads = 0.0;
    public double velocityRadsPerSec = 0.0;
    public double[] appliedVolts = new double[] {};
    public double[] torqueCurrentAmps = new double[] {};
    public double[] tempCelcius = new double[] {};
  }

  /** Updates the set of loggable inputs. */
  public default void updateInputs(TrinityIOInputs inputs) {}

  /** Run to setpoint angle in radians */
  default void runSetpoint(double setpointRads, double feedforward) {}

  /** Run motors at volts */
  default void runVolts(double volts) {}

  /** Run motors at current */
  default void runCurrent(double amps) {}

  /** Set brake mode enabled */
  default void setBrakeMode(boolean enabled) {}

  /** Set PID values */
  default void setPID(double p, double i, double d) {}
  
  public default void setMotorOutputPercent(double outputPercent) {
  }
}
