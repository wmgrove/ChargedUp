package frc.robot;

import edu.wpi.first.math.controller.PIDController;

public class Utilities {

    //PIDs
    //values are stored in order P-I-D
    public static final double[] kDrivePID = {0,0,0};
    public static final double kDriveTolerance = 2;
    public static final double[] kTurnPID = {0,0,0};
    public static final double kTurnTolerance = 2;
    public static final double[] kExtensionPID = {0,0,0};
    public static final double[] kElevationPID = {0,0,0};
    public static final double[] kSlidePID = {0,0,0};

    //Motors
    public static final int[] kLeftMotors = {};
    public static final int[] kRightMotors = {};
    public static final int kDriveMotorCount = 3;
    public static final int kLeftIntake = 0;
    public static final int kRightIntake = 0;
    public static final int kWrist = 0;
    public static final int kElevation = 0;
    public static final int kExtension = 0;
    public static final int kSlide = 0;


    //Drivebase
    public static final double kAccel = 0;

    //Driver controller
    public static final int kDriverPort = 0;
    public static final double kDriverDeadband = 0.2;

    //Operator Controller
    public static final int kOperatorPort = 0;
    public static final int kTrigger = 1;
    public static final int kDown = 2;
    public static final int kUp = 3;
    public static final int kLeft = 4;
    public static final int kRight = 5;
    public static final int kLeftFar = 6;
    public static final int kLeftNear = 7;
    public static final int kCenterLeft = 8;
    public static final int kCenterRight = 9;
    public static final int kNearRight = 10;
    public static final int kFarRight = 11;

    //Gripper
    public static final int kClawPWM = 0;
    public static final double kConeClawPosition = 0;
    public static final double kCubeClawPosition = 0;

    //Arm
    public static final double kExtensionAccel = 0;
    public static final double kElevationAccel = 0;
    public static final int kLeftLimitDIO = 0;
    public static final int kRightLimitDIO = 0;
    public static final int kSlidePot = 0;

    //Sensors
    public static final int kCoastDIO = 0;

    /**
     * Sets a limit to the minimum non-zero value of an input. Values below
     * this limit become zero
     * 
     * <p>This is useful for things like joysticks where there may be excess
     * unwanted noise around zero
     * 
     * @param input The value to be processed
     * @param limit The minimum non-zero value
     * @return Returns input when |input| > |limit| and otherwise returns 0
     */
    public static double deadband (double input, double limit) {
        if (Math.abs(input) < Math.abs(limit)) {
            input = 0;
        }
        return input;
    }

    /**
     * Sets a limit to the maximum value of an input.
     * 
     * <p> useful as a speed limiter.
     * @param input The value to be processed
     * @param limit The maximum value
     * @return Returns input when |input| < |limit|, and otherwise returns
     * limit with the same direction as the input
     */
    public static double clamp (double input, double limit) {
        if(Math.abs(input) > Math.abs(limit)) {
            //Dividing a value by its absolute value normalizes it to 1 or -1,
            //preserving directionality
            input = (input/Math.abs(input)) * Math.abs(limit);
        }
        return input;
    }

    /**
     * Construct a PID Controller from an array
     * 
     * @param pidArray a double array of length 3 in the order P, I, D that
     * defines the PID Controller
     * @return A PID controller defined by the array
     * @throws IndexOutOfBoundsException throws error if array of incorrect length is given
     */
    public static PIDController pidInitializer(double[] pidArray) {
        if (pidArray.length != 3) {
            throw new IndexOutOfBoundsException("Array must be of length 3");
        }
        return new PIDController(pidArray[0], pidArray[1], pidArray[2]);
    }
    
}
