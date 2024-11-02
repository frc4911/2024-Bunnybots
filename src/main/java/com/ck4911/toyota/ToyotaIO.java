// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.toyota;

import org.littletonrobotics.junction.AutoLog;

public interface ToyotaIO {
  @AutoLog
  public static class ToyotaIOInputs {
    public double armAbsolutePositionRad = 0.0;
    public double armRelativePositionRad = 0.0;
    public double armInternalPositionRad = 0.0;
    public double armRelativeVelocityRadPerSec = 0.0;
    public double armInternalVelocityRadPerSec = 0.0;
    public double armAppliedVolts = 0.0;
    public double[] armCurrentAmps = new double[] {};
    public double[] armTempCelcius = new double[] {};

    public double rollerAppliedVolts = 0.0;
    public double[] rollerCurrentAmps = new double[] {};
  }

  /** Updates the set of loggable inputs. */
  public default void updateInputs(ToyotaIOInputs inputs) {}

  /** Set the arm motor voltage */
  public default void setArmVoltage(double volts) {}

  /** Set the intake roller voltage */
  public default void setRollerVoltage(double volts) {}

  /** Enable or disable brake mode on the motors. */
  public default void setBrakeMode(boolean armBrake, boolean rollerBrake) {}
}
