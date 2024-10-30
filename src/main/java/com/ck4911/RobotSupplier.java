// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911;

import edu.wpi.first.wpilibj.RobotBase;
import java.util.function.Supplier;

/** Supplier implementation for creating a new Robot. */
public final class RobotSupplier implements Supplier<RobotBase> {
  @Override
  public RobotBase get() {
    return new Robot();
  }
}
