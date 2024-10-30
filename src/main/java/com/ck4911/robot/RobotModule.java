// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.robot;

import com.ck4911.Constants.Mode;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import javax.inject.Named;

@Module
public interface RobotModule {

  @Provides
  public static @Named("TuningMode") boolean providesTuningMode() {
    return true;
  }

  @Provides
  public static @Named("LoopPeriod") double providesLoopPeriod() {
    return 0.02;
  }

  @Provides
  public static Mode providesMode() {
    return Mode.REAL;
  }

  @Provides
  public static CommandScheduler providesCommandScheduler() {
    return CommandScheduler.getInstance();
  }
}
