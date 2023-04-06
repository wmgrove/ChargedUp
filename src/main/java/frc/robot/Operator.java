package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Operator {

    static Operator self;

    Joystick controller = new Joystick(0);

    Gripper gripper = Gripper.getGripper();

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
    }

    private boolean c_intake () {
        return controller.getRawButton(Utilities.kLeftNear);
    }

    private boolean c_release () {
        return controller.getRawButton(Utilities.kLeftFar);
    }
}
