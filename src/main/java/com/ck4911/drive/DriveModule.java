// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.drive;

import com.ck4911.Constants.Mode;
import dagger.Module;
import dagger.Provides;
import javax.inject.Provider;

@Module
public interface DriveModule {

  @Provides
  public static DriveConstants provideDriveConstants() {
    return DriveConstantsBuilder.builder()
        .gyroId(1)
        .frontLeftId(3)
        .frontRightId(1)
        .backLeftId(4)
        .backRightId(2)
        .build();
  }

  @Provides
  public static DriveIO providesDriveIO(
      Mode mode, Provider<DriveIOReal> realProvider, Provider<DriveIOSim> simProvider) {
    switch (mode) {
      case REAL:
        // TODO: enable this when motors are set up
        // return realProvider.get();
        return new DriveIO() {};
      case SIM:
        return simProvider.get();
      default:
        return new DriveIO() {};
    }
  }
}
