package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Operator {

    static Operator self;

    Joystick controller = new Joystick(0);

    Gripper gripper = Gripper.getGripper();
    Arm arm = Arm.getArm();

    public static Operator getSelf () {
        if (self == null) {
            self = new Operator();
        }
        return self;
    }

    private Operator () {}

    public void run() {
        if (c_intake()) {
            gripper.intake();
        }
        if (c_release()) {
            gripper.release();
        }
        //TODO: Real values
        if (c_slideLeft()) {
            arm.slide(1);
        }
        else if (c_slideRight()) {
            arm.slide(-1);
        }

        if (c_extend()) {
            arm.extend(1);
        }
        if (c_retract()) {
            arm.extend(-1);
        }

        arm.elevate(Utilities.deadband(-controller.getY(), 0.2));
    }

    private boolean c_intake () {
        return controller.getRawButton(Utilities.kLeftNear);
    }

    private boolean c_release () {
        return controller.getRawButton(Utilities.kLeftFar);
    }

    private boolean c_slideLeft () {
        return controller.getRawButton(Utilities.kLeft);
    }

    private boolean c_slideRight () {
        return controller.getRawButton(Utilities.kRight);
    }

    private boolean c_extend () {
        return controller.getRawButton(Utilities.kUp);
    }

    private boolean c_retract () {
        return controller.getRawButton(Utilities.kDown);
    }
}
