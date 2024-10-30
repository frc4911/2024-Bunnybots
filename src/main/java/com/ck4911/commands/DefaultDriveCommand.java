// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.commands;

import com.ck4911.drive.Drive;
import edu.wpi.first.wpilibj2.command.Command;

public final class DefaultDriveCommand extends Command {
  // TODO: declare variables here

  public DefaultDriveCommand(Drive driveSubsystem) {
    // TODO: assign variables here

    addRequirements(driveSubsystem);
  }

  @Override
  public void execute() {
    // TODO: get controller inputs and handle them here
  }
}
