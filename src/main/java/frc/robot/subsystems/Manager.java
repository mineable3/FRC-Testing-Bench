// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.TestBench.TestBenchStates;

public class Manager extends SubsystemBase implements StateSubsystem, CheckableSubsystem{

  private boolean status = false;
  private boolean initialized = false;

  private ManagerStates desiredState, currentState = ManagerStates.IDLE;

  /** Creates a new Manager. */
  public Manager() {
    TestBench.getInstance();
  }

  @Override
  public boolean getInitialized() {
    return initialized;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void stop() {
    TestBench.getInstance().stop();
  }

  @Override
  public boolean checkSubsystem() {
    status = TestBench.getInstance().checkSubsystem();
    return status;
  }

  @Override
  public void update() {
    TestBench.getInstance().update();

    switch(currentState) {
      case IDLE:
        break;
      case RUNNING:
        break;
    
      default:
        break;
    }
  }

  public void setDesiredState(ManagerStates state) {
    if(this.desiredState != state) {
      desiredState = state;
      handleStateTransition();
    }
  }

  public ManagerStates getState() {
    return currentState;
  }

  @Override
  public void handleStateTransition() {
    switch(desiredState) {
      case IDLE:
        TestBench.getInstance().setDesiredState(TestBenchStates.IDLE);
        break;
      case RUNNING:
        TestBench.getInstance().setDesiredState(TestBenchStates.RUNNING);
        break;

      default:
        break;
    }

    currentState = desiredState;
  }

  public enum ManagerStates {
    IDLE,
    RUNNING;
  }
}
