// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.control;

import com.ck4911.commands.VirtualSubsystem;
import com.ck4911.control.Controller.Role;
import com.ck4911.drive.Drive;
import com.ck4911.util.Alert;
import com.ck4911.util.Alert.AlertType;
import com.ck4911.util.Alerts;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class ControllerBinding implements VirtualSubsystem {
  private final Alert driverDisconnected;
  private final Alert operatorDisconnected;

  private final CommandXboxController driver;
  private final CommandXboxController operator;
  private final Drive drive;

  @Inject
  public ControllerBinding(
      Drive drive,
      @Controller(Role.DRIVER) CommandXboxController driver,
      @Controller(Role.OPERATOR) CommandXboxController operator,
      Alerts alerts) {
    this.drive = drive;
    this.driver = driver;
    this.operator = operator;

    driverDisconnected =
        alerts.create("Driver controller disconnected (port 0).", AlertType.WARNING);
    operatorDisconnected =
        alerts.create("Operator controller disconnected (port 1).", AlertType.WARNING);

    setupControls();
  }

  @Override
  public void periodic() {
    driverDisconnected.set(
        !DriverStation.isJoystickConnected(driver.getHID().getPort())
            || !DriverStation.getJoystickIsXbox(driver.getHID().getPort()));
    operatorDisconnected.set(
        !DriverStation.isJoystickConnected(operator.getHID().getPort())
            || !DriverStation.getJoystickIsXbox(operator.getHID().getPort()));
  }

  private void setupControls() {
    drive.setDefaultCommand(
        Commands.run(() -> drive.driveArcade(-driver.getLeftY(), -driver.getRightX()), drive));
  }

  public void setDriverRumble(boolean enabled) {
    driver.getHID().setRumble(RumbleType.kBothRumble, enabled ? 1 : 0);
  }

  public void setOperatorRumble(boolean enabled) {
    operator.getHID().setRumble(RumbleType.kBothRumble, enabled ? 1 : 0);
  }
}
