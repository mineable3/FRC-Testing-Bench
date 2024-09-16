// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.Manager;
import frc.robot.subsystems.Manager.ManagerStates;

public class RobotContainer {

  private final Manager m_Manager = new Manager();
  private final XboxController m_Controller = new XboxController(0);

  public RobotContainer() {
    m_Manager.setDesiredState(ManagerStates.IDLE);
    configureBindings();
  }

  private void configureBindings() {
    Trigger x = new Trigger(m_Controller::getXButton);
    x.onTrue(new InstantCommand(() -> m_Manager.setDesiredState(ManagerStates.RUNNING)));
    x.onFalse(new InstantCommand(() -> m_Manager.setDesiredState(ManagerStates.IDLE)));

    /* Note: If you want to write less code, you could do the following
     * and skip creating a Manager for your Test Bench.
     * 
     * x.onTrue(new InstantCommand(() -> TestBench.getInstance().setDesiredState(TestBenchStates.RUNNING)));
     * x.onFalse(new InstantCommand(() -> TestBench.getInstance().setDesiredState(TestBenchStates.IDLE)));
     */

    
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
