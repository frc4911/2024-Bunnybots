// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.trinity;

import com.ck4911.grabber.GrabberConstants;
import com.ck4911.grabber.GrabberConstantsBuilder;
import com.ck4911.robot.Mode;
import dagger.Module;
import dagger.Provides;
import javax.inject.Provider;

@Module
public interface TrinityModule {

  @Provides
  public static GrabberConstants provideGrabberConstants() {
    return GrabberConstantsBuilder.builder().armMotorId(31).build();
  }

  @Provides
  public static TrinityIO providesHopperIO(
      Mode mode, Provider<TrinityIOReal> realProvider, Provider<TrinityIOSim> simProvider) {
    switch (mode) {
      case REAL:
        return realProvider.get();
      case SIM:
        return simProvider.get();
      default:
        return new TrinityIO() {};
    }
  }
}
