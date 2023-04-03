package frc.robot;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;

public class Drivebase {

    static Drivebase self = null;

    //Drive Motor controllers
    CANSparkMax[] leftMotors = new CANSparkMax[Utilities.DRIVEMOTORCOUNT];
    CANSparkMax[] rightMotors = new CANSparkMax[Utilities.DRIVEMOTORCOUNT];

    //Sensor data
    double odometerOrigin = 0;
    double odometer = 0;
    double directionOrigin;
    Sensors sensors = Sensors.getSensors();

    //Autonomous data
    double autodriveTarget = 0;
    boolean isAutodriveActive = false;
    boolean isAutodrivePowerful = false;

    double autoturnTarget = 0;
    boolean isAutoturnActive = false;

    PIDController drivePID = new PIDController(Utilities.DRIVEPID[0], Utilities.DRIVEPID[1], Utilities.DRIVEPID[2]);
    PIDController turnPID = new PIDController(Utilities.TURNPID[0], Utilities.TURNPID[1], Utilities.TURNPID[2]);

    //Miscellaneous utilities
    SlewRateLimiter acceleration = new SlewRateLimiter(Utilities.ACCEL);

    /**
     * Provides the singleton for the drivebase
     * 
     * @return Drivebase singleton
    */
    public static Drivebase getDrivebase() {
        if (self == null) {
            self = new Drivebase();
        }
        return self;
    }

    /**
     * Constructor
     * 
     * <p>Sets motor controller CAN IDs, idle mode, and brake mode. Defines
     * direction origin
     */
    private Drivebase () {
        for (int i = 0; i < Utilities.DRIVEMOTORCOUNT; i++) {
            leftMotors[i] = new CANSparkMax(Utilities.LEFTMOTORS[i], MotorType.kBrushless);
            rightMotors[i] = new CANSparkMax(Utilities.RIGHTMOTORS[i], MotorType.kBrushless);
            leftMotors[i].setIdleMode(IdleMode.kBrake);
            rightMotors[i].setIdleMode(IdleMode.kBrake);
            leftMotors[i].setInverted(false);
            rightMotors[i].setInverted(true);
        }

        directionOrigin = sensors.getAngle();
    }

    //Robot functions

    /**
     * Updates drive components periodically
     * 
     * <p>Sets motor controllers to PID speed when auto-drive is active
     */
    public void run () {
        if(isAutodriveActive) {
            setAutoSpeed();
        }
    }

    public void idle () {
        //TODO: implement
    }

    public void auto () {
        //TODO: implement
    }

    //Autonomous

    /**
     * Sets the auto-drive distance
     * 
     * @param distance the distance the robot should drive from its current
     * location
     */
    public void autodrive (double distance) {
        autodriveTarget += distance;
        drivePID.setSetpoint(autodriveTarget);
        drivePID.setTolerance(Utilities.DRIVETOLERANCE);
        isAutodriveActive = true;
    }
    
    /**
     * Sets the auto-turn angle
     * 
     * @param angle the size of the angle the robot should travel from its
     * current position. Left is positive.
     */
    public void autoturn (double angle) {
        autoturnTarget += angle;
        turnPID.setSetpoint(angle);
        turnPID.setTolerance(Utilities.TURNTOLERANCE);
        isAutodriveActive = true;
    }

    /**
     * Defines motor speed based on PIDs for driving and turning to target
     */
    private void setAutoSpeed () {
        double speed = drivePID.calculate(odometer);
        double turn = turnPID.calculate(sensors.getAngle() - directionOrigin, autoturnTarget);
        setLeftSpeed(speed + turn);
        setRightSpeed(speed - turn);
    }

    /**
     * Whether or not the robot has reached its drive target
     * 
     * @return True if robot is at its target, otherwise false
     */
    public boolean atTarget() {
        return (drivePID.atSetpoint() && turnPID.atSetpoint());
    }

    //Teleop

    /**
     * Defines motor speeds given arcade drive inputs.
     * 
     * <p>Intended use is for one control stick axis to define speed, while
     * another defines turn.
     * 
     * @param speed The motor power the robot should use [-1, 1]
     * @param turn The motor power to turn with [-1, 1]
     */
    public void arcade (double speed, double turn) {
        speed = acceleration.calculate(speed);
        setLeftSpeed(speed + turn);
        setRightSpeed(speed - turn);
        if (!(speed == 0 && turn == 0)) {
            isAutodriveActive = false;
        }
    }

    /**
     * Defines motor speeds given tank drive inputs.
     * 
     * <p>Intended use is for one axis to define left-side motor speed, while
     * the other axis defines right-side motor speed.
     * @param leftSpeed Motor speed for left motors [-1, 1]
     * @param rightSpeed Motor speed for right motors [-1, 1]
     */
    public void tank (double leftSpeed, double rightSpeed) {
        setLeftSpeed(leftSpeed);
        setRightSpeed(rightSpeed);
        if (!(leftSpeed == 0 && rightSpeed == 0)) {
            isAutodriveActive = false;
        }
    }

    //Control code

    /**
     * Defines left-motor speeds
     * 
     * @param speed motor power [-1, 1]
     */
    private void setLeftSpeed (double speed) {
        for(CANSparkMax motor: leftMotors) {
            motor.set(speed);
        }
    }

    /**
     * Defines right-motor speeds.
     * 
     * @param speed motor power [-1, 1]
     */
    private void setRightSpeed (double speed) {
        for(CANSparkMax motor: rightMotors) {
            motor.set(speed);
        }
    }

    /**
     * Resets robot's default angle to current facing.
     */
    @SuppressWarnings("unused")
    private void resetDirectionOrigin() {
        autoturnTarget = 0;
        directionOrigin = sensors.getAngle();
    }
    
    /**
     * Resets robot's default position to current position.
     */
    @SuppressWarnings("unused")
    private void resetOdometer () {
        odometerOrigin = odometer;
    }
}
