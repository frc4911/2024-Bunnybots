// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.control;

import dagger.multibindings.IntoSet;
import dagger.Binds;
import dagger.Module;

@Module
public interface ControlModule {
  @Binds
  @IntoSet
  public VirtualSubsystem bindsControllerBinding(ControllerBinding controllerBinding);
}
