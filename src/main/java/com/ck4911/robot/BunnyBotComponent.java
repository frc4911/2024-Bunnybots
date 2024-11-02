// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.robot;

import com.ck4911.auto.AutoModule;
import com.ck4911.commands.CommandsModule;
import com.ck4911.control.ControlModule;
import com.ck4911.drive.DriveModule;
import com.ck4911.grabber.GrabberModule;
import com.ck4911.leds.LedModule;
import com.ck4911.toyota.ToyotaModule;
import com.ck4911.trinity.TrinityModule;
import dagger.Component;
import javax.inject.Singleton;

@Component(
    modules = {
      AutoModule.class,
      ToyotaModule.class,
      ControlModule.class,
      CommandsModule.class,
      DriveModule.class,
      GrabberModule.class,
      LedModule.class,
      TrinityModule.class,
      RobotModule.class,
    })
@Singleton
public interface BunnyBotComponent {
  public CyberKnightsRobot robot();
}
