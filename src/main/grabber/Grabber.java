// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.grabber;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import javax.inject.Inject;

public final class Grabber extends SubsystemBase {

  private final GrabberIO io;
  
  @Inject
  public Collector(GrabberIO io) {
    this.io = io;
  }
}
