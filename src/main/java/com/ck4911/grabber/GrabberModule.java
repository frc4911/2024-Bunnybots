// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.grabber;

import com.ck4911.robot.Mode;
import dagger.Module;
import dagger.Provides;
import javax.inject.Provider;

@Module
public interface GrabberModule {

  @Provides
  public static GrabberConstants provideGrabberConstants() {
    return GrabberConstantsBuilder.builder().armMotorId(31).build();
  }

  @Provides
  public static GrabberIO providesGrabberIO(
      Mode mode, Provider<GrabberIOReal> realProvider, Provider<GrabberIOSim> simProvider) {
    switch (mode) {
      case REAL:
        return realProvider.get();
      case SIM:
        return simProvider.get();
      default:
        return new GrabberIO() {};
    }
  }
}
