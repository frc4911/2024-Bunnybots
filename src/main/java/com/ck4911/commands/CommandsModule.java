// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.commands;

import dagger.multibindings.IntoSet;
import dagger.Module;
import dagger.Provides;

@Module
public interface CommandsModule {

  @Provides
  @IntoSet
  public static VirtualSubsystem providesDefault() {
    return new VirtualSubsystem() {
      @Overrride
      public void periodic() {}
    };
  }
}