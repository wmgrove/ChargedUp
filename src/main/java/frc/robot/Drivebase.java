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
    double odomoterOrigin = 0;
    double directionOrigin = 0;
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
    SlewRateLimiter acceleration;

    //Singleton accessor
    public static Drivebase getDrivebase() {
        if (self == null) {
            self = new Drivebase();
        }
        return self;
    }

    //Constructor
    private Drivebase () {
        for (int i = 0; i < Utilities.DRIVEMOTORCOUNT; i++) {
            leftMotors[i] = new CANSparkMax(Utilities.LEFTMOTORS[i], MotorType.kBrushless);
            rightMotors[i] = new CANSparkMax(Utilities.RIGHTMOTORS[i], MotorType.kBrushless);
            leftMotors[i].setIdleMode(IdleMode.kBrake);
            rightMotors[i].setIdleMode(IdleMode.kBrake);
            leftMotors[i].setInverted(false);
            rightMotors[i].setInverted(true);
        }
        
        acceleration = new SlewRateLimiter(Utilities.ACCEL);
    }

    //Robot functions
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
    public void autodrive (double distance) {
        autodriveTarget += distance;
        isAutodriveActive = true;
    }

    private void setAutoSpeed () {
        double speed = drivePID.calculate(odomoterOrigin, autodriveTarget);
        double turn = turnPID.calculate(sensors.getYaw(), autoturnTarget);
        setLeftSpeed(speed + turn);
        setRightSpeed(speed - turn);
    }

    //Teleop
    public void arcade (double speed, double turn) {
        speed = acceleration.calculate(speed);
        setLeftSpeed(speed + turn);
        setRightSpeed(speed - turn);
        if (!(speed == 0 && turn == 0)) {
            isAutodriveActive = false;
        }
    }

    public void tank (double leftSpeed, double rightSpeed) {
        setLeftSpeed(leftSpeed);
        setRightSpeed(rightSpeed);
        if (!(leftSpeed == 0 && rightSpeed == 0)) {
            isAutodriveActive = false;
        }
    }

    //Control code
    private void setLeftSpeed (double speed) {
        for(CANSparkMax motor: leftMotors) {
            motor.set(speed);
        }
    }

    private void setRightSpeed (double speed) {
        for(CANSparkMax motor: rightMotors) {
            motor.set(speed);
        }
    }
    
}
