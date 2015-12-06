//Lead Programmer: Garret Sampel

package org.usfirst.frc.team3104.robot;

import java.util.HashMap;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Print {
	//PowerDistributionPanel panel = new PowerDistributionPanel();
	//HashMap<String,Double> table = new HashMap<String,Double>();  
    //DriverStationLCD.Line l = null;
    
    String clear = "                                   ";
    //35 character limit on clear.

/*public void p(int line, String txt){
        switch(line){
            case 1: l = DriverStationLCD.Line.kUser1; break;
            case 2: l = DriverStationLCD.Line.kUser2; break;
            case 3: l = DriverStationLCD.Line.kUser3; break;
            case 4: l = DriverStationLCD.Line.kUser4; break;
            case 5: l = DriverStationLCD.Line.kUser5; break;
            case 6: l = DriverStationLCD.Line.kUser6; break;
        }
      DriverStationLCD.getInstance().println(l, 1, clear);
      DriverStationLCD.getInstance().println(l, 1, txt);
      DriverStationLCD.getInstance().updateLCD();
    }
    public void clear(){
        p(1,clear);
        p(2,clear);
        p(3,clear);
        p(4,clear);
        p(5,clear);
        p(6,clear);
        }*/
   /* public void update(){
    	pdb("Temperature", panel.getTemperature());
    	pdb("Voltage", panel.getVoltage());
    	pdb("Total Current", panel.getTotalCurrent());
    	pdb("Total Energy", panel.getTotalEnergy());
    	pdb("Total Power", panel.getTotalPower());   	
    }*/
    
    public void n(String key, double n){
        SmartDashboard.putNumber(key, n);
    }
    public void s(String key, String txt){
    	SmartDashboard.putString(key, txt);
    }
   // public void pdb(String key, double value){
    	//table.put(key, value);
    //	SmartDashboard.putString(key, value+"");
   // }
}
