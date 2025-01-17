package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.math.controller.PIDController;

public class Gripper {
    static Gripper self;

    //Motors
    TalonSRX leftIntake = new TalonSRX(Utilities.kLeftIntake);
    TalonSRX rightIntake = new TalonSRX(Utilities.kRightIntake);
    TalonSRX wrist = new TalonSRX(Utilities.kWrist);
    Servo claw = new Servo(Utilities.kClawPWM);

    //Control
    PIDController thetaPID;
    Encoder theta = new Encoder(0, 0); //TODO: Put real values here

    //States
    enum ObjectState {CONE, CUBE}
    enum IntakeState {INTAKE, RELEASE, HOLD, REST}
    ObjectState gamepiece;
    IntakeState mode;
    boolean intaking = false;
    boolean releasing = false;

    public static Gripper getGripper () {
        if (self == null) {
            self = new Gripper();
        }
        return self;
    }

    private Gripper () {}

    public void run () {
        switch (gamepiece) {
            case CONE:
                claw.set(Utilities.kConeClawPosition);
                break;
            case CUBE:
                claw.set(Utilities.kCubeClawPosition);
                break;
        }
        switch (mode) {
            case INTAKE:
                setIntake(-.5);
                break;
            case RELEASE:
                setIntake(.25);
                break;
            case HOLD:
                setIntake(-.1);
                break;
            case REST:
                setIntake(0);
                break;
        }

        //Resets intaking/releasing mode before the next loop, operator
        //controls override this.
        if (intaking) {
            mode = IntakeState.HOLD;
        }
        if (releasing) {
            mode = IntakeState.REST;
        }
        intaking = false;
        releasing = false;
    }

    private void setIntake (double speed) {
        leftIntake.set(ControlMode.PercentOutput,speed);
        rightIntake.set(ControlMode.PercentOutput,speed);
    }

    public void coneMode () {
        gamepiece = ObjectState.CONE;
        mode = IntakeState.REST;
    }

    public void cubeMode () {
        gamepiece = ObjectState.CUBE;
        mode = IntakeState.REST;
    }

    public void intake () {
        mode = IntakeState.INTAKE;
        intaking = true;
    }

    public void release () {
        mode = IntakeState.RELEASE;
        releasing = true;
    }
}
