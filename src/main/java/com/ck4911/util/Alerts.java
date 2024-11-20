// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.util;

import com.ck4911.util.Alert.AlertType;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class Alerts {
  private Map<String, SendableAlerts> groups = new HashMap<String, SendableAlerts>();

  @Inject
  public Alerts() {}

  /**
   * Creates a new Alert in the default group - "Alerts". If this is the first to be instantiated,
   * the appropriate entries will be added to NetworkTables.
   *
   * @param text Text to be displayed when the alert is active.
   * @param type Alert level specifying urgency.
   */
  public Alert create(String text, AlertType type) {
    return create("Alerts", text, type);
  }

  /**
   * Creates a new Alert. If this is the first to be instantiated in its group, the appropriate
   * entries will be added to NetworkTables.
   *
   * @param group Group identifier, also used as NetworkTables title
   * @param text Text to be displayed when the alert is active.
   * @param type Alert level specifying urgency.
   */
  public Alert create(String group, String text, AlertType type) {
    if (!groups.containsKey(group)) {
      groups.put(group, new SendableAlerts());
      SmartDashboard.putData(group, groups.get(group));
    }

    AlertImpl alert = new AlertImpl(text, type);
    groups.get(group).alerts.add(alert);
    return alert;
  }

  private static class AlertImpl implements Alert {
    private final AlertType type;
    private boolean active = false;
    private double activeStartTime = 0.0;
    private String text;

    public AlertImpl(String text, AlertType type) {
      this.text = text;
      this.type = type;
    }

    /**
     * Sets whether the alert should currently be displayed. When activated, the alert text will
     * also be sent to the console.
     */
    @Override
    public void set(boolean active) {
      if (active && !this.active) {
        activeStartTime = Timer.getFPGATimestamp();
        switch (type) {
          case ERROR:
            DriverStation.reportError(text, false);
            break;
          case WARNING:
            DriverStation.reportWarning(text, false);
            break;
          case INFO:
            System.out.println(text);
            break;
        }
      }
      this.active = active;
    }

    /** Updates current alert text. */
    @Override
    public void setText(String text) {
      if (active && !text.equals(this.text)) {
        switch (type) {
          case ERROR:
            DriverStation.reportError(text, false);
            break;
          case WARNING:
            DriverStation.reportWarning(text, false);
            break;
          case INFO:
            System.out.println(text);
            break;
        }
      }
      this.text = text;
    }
  }

  private static class SendableAlerts implements Sendable {
    public final List<AlertImpl> alerts = new ArrayList<>();

    public String[] getStrings(AlertType type) {
      return alerts.stream()
          .filter(alert -> alert.type == type && alert.active)
          .sorted((alert1, alert2) -> (int) (alert2.activeStartTime - alert1.activeStartTime))
          .map(alert -> alert.text)
          .toArray(String[]::new);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
      builder.setSmartDashboardType("Alerts");
      builder.addStringArrayProperty("errors", () -> getStrings(AlertType.ERROR), null);
      builder.addStringArrayProperty("warnings", () -> getStrings(AlertType.WARNING), null);
      builder.addStringArrayProperty("infos", () -> getStrings(AlertType.INFO), null);
    }
  }
}
