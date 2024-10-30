// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.drive.Drive;

public final class DefaultDriveCommand extends Command {
    // TODO: declare variables here

    public DefaultDriveCommand(Drive driveSubsystem) {
        // TODO: assign variables here

        addRequirements(driveSubsystem);
    }

    @Override
    public void execute() {
        // TODO: get controller inputs and handle them here
    }
    
}