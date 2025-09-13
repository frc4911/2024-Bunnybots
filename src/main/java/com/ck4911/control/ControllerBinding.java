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
import com.ck4911.toyota.Toyota;
import com.ck4911.trinity.Trinity;
import com.ck4911.util.Alert;
import com.ck4911.util.Alert.AlertType;
import com.ck4911.util.Alerts;
import com.ck4911.util.LoggedTunableNumber;
import com.ck4911.util.LoggedTunableNumber.TunableNumbers;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Commands;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class ControllerBinding implements VirtualSubsystem {
  private final Alert driverDisconnected;
  private final Alert operatorDisconnected;

  private final CyberKnightsController driver;
  private final CyberKnightsController operator;
  private final Drive drive;
  private final Trinity trinity;
  private final Toyota toyota;

  private final LoggedTunableNumber driveSpeedLimiter;

  @Inject
  public ControllerBinding(
      Drive drive,
      Trinity trinity,
      Toyota toyota,
      @Controller(Role.DRIVER) CyberKnightsController driver,
      @Controller(Role.OPERATOR) CyberKnightsController operator,
      Alerts alerts,
      TunableNumbers tunableNumbers) {
    this.drive = drive;
    this.toyota = toyota;
    this.driver = driver;
    this.trinity = trinity;
    this.operator = operator;

    this.driveSpeedLimiter = tunableNumbers.create("Controls/DriveSpeedLimiter", 0.7);

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
        Commands.run(
            () ->
                drive.driveArcade(
                    -driver.getLeftY() * driveSpeedLimiter.get(),
                    -driver.getRightX() * driveSpeedLimiter.get()),
            drive));

    driver
        .rightTrigger()
        .onTrue(Commands.run(() -> trinity.setMotorOutputPercent(.1)))
        .onFalse(Commands.run(() -> trinity.setMotorOutputPercent(0)));

    driver
        .leftTrigger()
        .onTrue(Commands.run(() -> trinity.setMotorOutputPercent(-.1)))
        .onFalse(Commands.run(() -> trinity.setMotorOutputPercent(0)));
    driver
        .rightBumper()
        .onTrue(Commands.run(() -> toyota.setMotorOutputPercent(.1)))
        .onFalse(Commands.run(() -> toyota.setMotorOutputPercent(0)));
    driver
        .leftTrigger()
        .onTrue(Commands.run(() -> toyota.setMotorOutputPercent(-.1)))
        .onFalse(Commands.run(() -> toyota.setMotorOutputPercent(0)));
  }

  public void setDriverRumble(boolean enabled) {
    driver.getHID().setRumble(RumbleType.kBothRumble, enabled ? 1 : 0);
  }

  public void setOperatorRumble(boolean enabled) {
    operator.getHID().setRumble(RumbleType.kBothRumble, enabled ? 1 : 0);
  }
}
