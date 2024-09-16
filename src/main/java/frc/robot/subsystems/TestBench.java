// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.utils.Utils;

import java.util.Map;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TestBench extends SubsystemBase implements StateSubsystem, CheckableSubsystem{

  private boolean status = false;
  private boolean initialized = false;
  private CANSparkMax brushMotor;
  private CANSparkMax brushlessMotor;
  private static TestBench m_Instance;
  private TestBenchStates desiredState, currentState = TestBenchStates.IDLE;
  private GenericEntry speed;

  /** Creates a new TestBench. */
  private TestBench() {
    brushMotor = new CANSparkMax(1, MotorType.kBrushed);
    brushlessMotor = new CANSparkMax(2, MotorType.kBrushless);

    speed = Shuffleboard.getTab("Main").add("Intake speed", 0.0)
        .withWidget(BuiltInWidgets.kNumberSlider)
        .withProperties(Map.of("min", -1, "max", 1)) // specify widget properties here
        .getEntry();

    Shuffleboard.getTab("Main").addDouble("Applied output speed - Brush", () -> brushMotor.getAppliedOutput());
    Shuffleboard.getTab("Main").addDouble("Applied output speed - Brushless", () -> brushlessMotor.getAppliedOutput());

    initialized = true;
  }

  public static TestBench getInstance() {
    if(m_Instance == null) {
      m_Instance = new TestBench();
    }
    return m_Instance;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void stop() {
    brushMotor.stopMotor();
    brushlessMotor.stopMotor();
  }

  public void setSpeed(double speed) {
    speed = Utils.normalize(speed);
    brushMotor.set(speed);
    brushlessMotor.set(speed);
  }

  @Override
  public boolean checkSubsystem() {
    status = true;
    return status;
  }

  @Override
  public boolean getInitialized() {
    return initialized;
  }

  @Override
  public void update() {
    switch(currentState) {
      case IDLE:
        break;
      case RUNNING:
        break;
    
      default:
        break;
    }
  }

  @Override
  public void handleStateTransition() {
    switch(desiredState) {
      case IDLE:
        setSpeed(0);
        break;
      case RUNNING:
        setSpeed(speed.get().getDouble());
        break;
    
      default:
        break;
    }

    currentState = desiredState;
  }

  public void setDesiredState(TestBenchStates state) {
    if(this.desiredState != state) {
      desiredState = state;
      handleStateTransition();
    }
  }

  public TestBenchStates getState() {
    return currentState;
  }

  public enum TestBenchStates {
    IDLE,
    RUNNING;
  }
}
