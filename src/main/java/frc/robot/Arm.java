package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxAnalogSensor;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

public class Arm {
    //Motors
    CANSparkMax elevationMotor = new CANSparkMax(Utilities.kElevation, MotorType.kBrushless);
    CANSparkMax extensionMotor = new CANSparkMax(Utilities.kExtension, MotorType.kBrushless);
    Spark slideMotor = new Spark(Utilities.kSlide);

    //PIDs
    PIDController slideController = Utilities.pidInitializer(Utilities.kSlidePID);
    PIDController elevationController = Utilities.pidInitializer(Utilities.kElevationPID);
    PIDController extensionController = Utilities.pidInitializer(Utilities.kExtensionPID);

    //Control
    SlewRateLimiter elevationAccel = new SlewRateLimiter(Utilities.kElevationAccel);
    SlewRateLimiter extensionAccel = new SlewRateLimiter(Utilities.kExtensionAccel);

    boolean autoSlide;
    boolean autoElevate;
    boolean autoExtend;

    //Sense
    SparkMaxLimitSwitch elevationForwardLimit;
    SparkMaxLimitSwitch elevationReverseLimit;
    SparkMaxAnalogSensor elevationPosition;
    SparkMaxLimitSwitch extensionNearLimit;
    SparkMaxLimitSwitch extensionFarLimit;
    SparkMaxAnalogSensor extensionPosition;
    DigitalInput slideLeftLimit;
    DigitalInput slideRightLimit;
    AnalogPotentiometer slidePosition;

    //Measurement
    double angleOrigin = 0;
    double slideOrigin = 0;
    double elevationOrigin = 0;
}
