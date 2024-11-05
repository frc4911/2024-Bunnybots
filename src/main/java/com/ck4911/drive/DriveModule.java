// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.drive;

import com.ck4911.Constants.Mode;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType;
import dagger.Module;
import dagger.Provides;
import javax.inject.Provider;

@Module
public interface DriveModule {

  @Provides
  @Singleton
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
  @Singleton
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

  @Provides
  @Singleton
  public static DriveIOInputsAutoLogged provideDriveIOInputsAutoLogged() {
    return new DriveIOInputsAutoLogged();
  }

  @Provides
  public static DifferentialDriveOdometry provideDifferentialDriveOdometry() {
    return new DifferentialDriveOdometry(new Rotation2d(), 0.0, 0.0);
  }

  @Provides
  @Singleton
  public static DifferentialDriveKinematics provideDifferentialDriveKinematics(
      DriveConstants constants) {
    return new DifferentialDriveKinematics(constants.trackWidth());
  }

  @Provides
  @Singleton
  public static Pigeon2 providePigeon2(DriveConstants constants) {
    return new Pigeon2(constants.gyroId());
  }

  @Provides
  @Location(Corner.FRONT_LEFT)
  @Singleton
  public static CANSparkFlex provideFrontLeftMotor(DriveConstants constants) {
    return new CANSparkFlex(constants.frontLeftId(), MotorType.kBrushless);
  }

  @Provides
  @Location(Corner.FRONT_RIGHT)
  @Singleton
  public static CANSparkFlex provideFrontRightMotor(DriveConstants constants) {
    return new CANSparkFlex(constants.frontRightId(), MotorType.kBrushless);
  }

  @Provides
  @Location(Corner.BACK_LEFT)
  @Singleton
  public static CANSparkFlex provideBackLeftMotor(DriveConstants constants) {
    return new CANSparkFlex(constants.backLeftId(), MotorType.kBrushless);
  }

  @Provides
  @Location(Corner.BACK_RIGHT)
  @Singleton
  public static CANSparkFlex provideBackRightMotor(DriveConstants constants) {
    return new CANSparkFlex(constants.backRightId(), MotorType.kBrushless);
  }
}
