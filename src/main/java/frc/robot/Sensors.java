package frc.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

public class Sensors {
    AHRS navx = new AHRS(SPI.Port.kMXP);
}
