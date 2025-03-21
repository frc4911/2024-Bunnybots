// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.drive;

import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record DriveConstants(
    int frontRightId,
    int backRightId,
    int frontLeftId,
    int backLeftId,
    int gyroId,
    double gearRatio,
    double wheelRadius,
    double trackWidth) {}
