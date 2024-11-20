// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.util;

/** Class for managing persistent alerts to be sent over NetworkTables. */
public interface Alert {

  /**
   * Sets whether the alert should currently be displayed. When activated, the alert text will also
   * be sent to the console.
   */
  public void set(boolean active);

  /** Updates current alert text. */
  public void setText(String text);

  /** Represents an alert's level of urgency. */
  public static enum AlertType {
    /**
     * High priority alert - displayed first on the dashboard with a red "X" symbol. Use this type
     * for problems which will seriously affect the robot's functionality and thus require immediate
     * attention.
     */
    ERROR,

    /**
     * Medium priority alert - displayed second on the dashboard with a yellow "!" symbol. Use this
     * type for problems which could affect the robot's functionality but do not necessarily require
     * immediate attention.
     */
    WARNING,

    /**
     * Low priority alert - displayed last on the dashboard with a green "i" symbol. Use this type
     * for problems which are unlikely to affect the robot's functionality, or any other alerts
     * which do not fall under "ERROR" or "WARNING".
     */
    INFO
  }
}
