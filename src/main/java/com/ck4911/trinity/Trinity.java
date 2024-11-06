// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.trinity;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import javax.inject.Inject;

public final class Trinity extends SubsystemBase {

  private final TrinityIO io;

  @Inject
  public Trinity(TrinityIO io) {
    this.io = io;
  }
}
