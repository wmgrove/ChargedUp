package frc.robot;

public class Utilities {

    //PIDs
    //values are stored in order P-I-D
    public static final double[] DRIVEPID = {0,0,0};
    public static final double DRIVETOLERANCE = 2;
    public static final double[] TURNPID = {0,0,0};
    public static final double TURNTOLERANCE = 2;

    //Motors
    public static final int[] LEFTMOTORS = {};
    public static final int[] RIGHTMOTORS = {};
    public static final int DRIVEMOTORCOUNT = 3;

    //Drivebase
    public static final double ACCEL = 0;

    //Controllers
    public static final int DRIVERPORT = 0;
    public static final double DRIVERDEADBAND = 0.2;

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
    
}
