// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.robot;

import com.ck4911.drive.DriveModule;
import dagger.Component;
import javax.inject.Singleton;

@Component(
    modules = {
      DriveModule.class,
      RobotModule.class,
    })
@Singleton
public interface BunnyBotComponent {
  public CyberKnightsRobot robot();
}
