// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.auto;

import com.ck4911.commands.VirtualSubsystem;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import edu.wpi.first.wpilibj2.command.Command;
import javax.inject.Singleton;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

@Module
public interface AutoModule {

  @Provides
  @Singleton
  public static LoggedDashboardChooser<Command> provideChooser() {
    return new LoggedDashboardChooser<Command>("Auto Routine");
  }

  @Binds
  @IntoSet
  public VirtualSubsystem bindsAutoCommandHandler(AutoCommandHandler autoCommandHandler);
}
