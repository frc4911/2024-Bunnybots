// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.grabber;

import javax.inject.Inject;

public final class GrabberIOSim implements GrabberIO {

  @Inject
  public GrabberIOSim() {
    System.out.println("[Init] Creating GrabberIOSim");
  }

  @Override
  public void updateInputs(GrabberIOInputs inputs) {
    // TODO: update the inputs
  }
}
