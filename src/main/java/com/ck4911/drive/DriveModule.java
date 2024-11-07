// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.drive;

import com.ck4911.drive.Location.Corner;
import com.ck4911.robot.Mode;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotGearing;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotMotor;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotWheelSize;
import javax.inject.Provider;
import javax.inject.Singleton;

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
  public static DriveIO providesDriveIO(
      Mode mode, Provider<DriveIOReal> realProvider, Provider<DriveIOSim> simProvider) {
    switch (mode) {
      case REAL:
        // TODO: enable this when motors are set up
        return realProvider.get();
        // return new DriveIO() {};
      case SIM:
        return simProvider.get();
      default:
        return new DriveIO() {};
    }
  }

  @Provides
  public static DriveIOInputsAutoLogged provideDriveIOInputsAutoLogged() {
    return new DriveIOInputsAutoLogged();
  }

  @Provides
  public static DifferentialDriveOdometry provideDifferentialDriveOdometry() {
    return new DifferentialDriveOdometry(new Rotation2d(), 0.0, 0.0);
  }

  @Provides
  public static DifferentialDriveKinematics provideDifferentialDriveKinematics(
      DriveConstants constants) {
    return new DifferentialDriveKinematics(constants.trackWidth());
  }

  @Provides
  public static GyroIO provideGyro(
      Mode mode, Provider<GyroIOReal> realProvider, Provider<GyrIOSim> simProvider) {
    switch (mode) {
      case REAL:
        // TODO: when the pigeon is installed, enable this next line.
        // return realProvider.get();
        return new GyroIO() {};
      case SIM:
        return simProvider.get();
      default:
        return new GyroIO() {};
    }
  }

  @Provides
  public static GyroIOInputsAutoLogged provideGyroIOInputsAutoLogged() {
    return new GyroIOInputsAutoLogged();
  }

  @Provides
  @Location(Corner.FRONT_LEFT)
  public static CANSparkFlex provideFrontLeftMotor(DriveConstants constants) {
    return new CANSparkFlex(constants.frontLeftId(), MotorType.kBrushless);
  }

  @Provides
  @Location(Corner.FRONT_RIGHT)
  public static CANSparkFlex provideFrontRightMotor(DriveConstants constants) {
    return new CANSparkFlex(constants.frontRightId(), MotorType.kBrushless);
  }

  @Provides
  @Location(Corner.BACK_LEFT)
  public static CANSparkFlex provideBackLeftMotor(DriveConstants constants) {
    return new CANSparkFlex(constants.backLeftId(), MotorType.kBrushless);
  }

  @Provides
  @Location(Corner.BACK_RIGHT)
  public static CANSparkFlex provideBackRightMotor(DriveConstants constants) {
    return new CANSparkFlex(constants.backRightId(), MotorType.kBrushless);
  }

  @Provides
  @Singleton
  public static DifferentialDrivetrainSim provideDifferentialDrivetrainSim() {
    // TODO: directly invoke DifferentialDrivetrainSim constructor using correct motors, etc.
    return DifferentialDrivetrainSim.createKitbotSim(
        KitbotMotor.kDualCIMPerSide, KitbotGearing.k10p71, KitbotWheelSize.kSixInch, null);
  }
}
