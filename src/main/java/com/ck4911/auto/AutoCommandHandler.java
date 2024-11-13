// Copyright (c) 2024 FRC 4911
// https://github.com/frc4911
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package com.ck4911.auto;

import com.ck4911.commands.VirtualSubsystem;
import com.ck4911.drive.Drive;
import com.ck4911.util.LocalADStarAK;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.pathfinding.Pathfinding;
import com.pathplanner.lib.util.PathPlannerLogging;
import com.pathplanner.lib.util.ReplanningConfig;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

@Singleton
public final class AutoCommandHandler implements VirtualSubsystem {
  private final LoggedDashboardChooser<Command> chooser;
  private final Drive drive;
  private double autoStart;
  private boolean autoMessagePrinted;
  private Command currentAutoCommand;

  @Inject
  public AutoCommandHandler(Drive drive, LoggedDashboardChooser<Command> chooser) {
    this.drive = drive;
    this.chooser = chooser;

    // Configure AutoBuilder for PathPlanner
    AutoBuilder.configureRamsete(
        drive::getPose,
        drive::setPose,
        drive::getSpeeds,
        drive::setSpeeds,
        new ReplanningConfig(),
        () ->
            DriverStation.getAlliance().isPresent()
                && DriverStation.getAlliance().get() == Alliance.Red,
        drive);

    Pathfinding.setPathfinder(new LocalADStarAK());
    PathPlannerLogging.setLogActivePathCallback(
        (activePath) -> {
          Logger.recordOutput(
              "Odometry/Trajectory", activePath.toArray(new Pose2d[activePath.size()]));
        });
    PathPlannerLogging.setLogTargetPoseCallback(
        (targetPose) -> {
          Logger.recordOutput("Odometry/TrajectorySetpoint", targetPose);
        });

    setupAutos();
  }

  @Override
  public void periodic() {
    // Print auto duration
    if (currentAutoCommand != null) {
      if (!currentAutoCommand.isScheduled() && !autoMessagePrinted) {
        if (DriverStation.isAutonomousEnabled()) {
          System.out.printf(
              "*** Auto finished in %.2f secs ***%n", Timer.getFPGATimestamp() - autoStart);
        } else {
          System.out.printf(
              "*** Auto cancelled in %.2f secs ***%n", Timer.getFPGATimestamp() - autoStart);
        }
        autoMessagePrinted = true;
      }
    }
  }

  private void setupAutos() {
    chooser.addDefaultOption("Nothing", Commands.none());

    addCharacterizations();
  }

  private void addCharacterizations() {
    chooser.addOption("Drive Static Characterization", drive.staticCharacterization());
    chooser.addOption("Drive FF Characterization", drive.feedForwardCharacterization());
  }

  private void addSysIds() {
    chooser.addOption(
        "Drive SysId (Quasistatic Forward)",
        drive.sysIdQuasistatic(SysIdRoutine.Direction.kForward));
    chooser.addOption(
        "Drive SysId (Quasistatic Reverse)",
        drive.sysIdQuasistatic(SysIdRoutine.Direction.kReverse));
    chooser.addOption(
        "Drive SysId (Dynamic Forward)", drive.sysIdDynamic(SysIdRoutine.Direction.kForward));
    chooser.addOption(
        "Drive SysId (Dynamic Reverse)", drive.sysIdDynamic(SysIdRoutine.Direction.kReverse));
    chooser.addOption("FIRST AUTO", new PathPlannerAuto("New Auto"));
  }

  public void startCurrentCommand() {
    stopCurrentCommand();
    autoStart = Timer.getFPGATimestamp();
    currentAutoCommand = chooser.get();
    if (currentAutoCommand != null) {
      currentAutoCommand.schedule();
    }
  }

  public void stopCurrentCommand() {
    if (currentAutoCommand != null) {
      currentAutoCommand.cancel();
    }
  }
}
