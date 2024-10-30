// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.commands;

import com.ck4911.drive.Drive;
import edu.wpi.first.wpilibj2.command.Command;

public final class Autos {
  /** Example static factory for an autonomous command. */
  public static Command exampleAuto(Drive subsystem) {
    // return Commands.sequence(subsystem.exampleMethodCommand(), new ExampleCommand(subsystem));
    return null;
  }

  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}
