// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.control;

import com.ck4911.commands.VirtualSubsystem;
import com.ck4911.control.Controller.Role;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import javax.inject.Singleton;

@Module
public interface ControlModule {
  @Singleton
  @Provides
  @Controller(Role.DRIVER)
  public static CommandXboxController provideDriverController() {
    return new CommandXboxController(0);
  }

  @Singleton
  @Provides
  @Controller(Role.OPERATOR)
  public static CommandXboxController provideOperatorController() {
    return new CommandXboxController(1);
  }

  @Binds
  @IntoSet
  public VirtualSubsystem bindsControllerBinding(ControllerBinding controllerBinding);
}
