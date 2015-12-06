
package org.usfirst.frc.team3104.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.IllegalUseOfCommandException;
import edu.wpi.first.wpilibj.vision.AxisCamera;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    	String n = "[HAZARD]: ";
    	AxisCamera camera;
    	//Pixy pixy;
    	//PixyPacket packet;
    	//int center;
    	//int robotCenter = 320;
        static DigitalInput limitSwitchTop = new DigitalInput(0);   		//used to determine max height of lifting mechanism
        static DigitalInput limitSwitchBottom = new DigitalInput(1);		//used to determine minimum height of lifting mechanism
        boolean upAllow = true;												//booleans used for lifter
        boolean downAllow = true;
    	static Solenoid pneumaticTrigger = new Solenoid(0);					//pneumatics for grabbing system to grab crates
    	static Solenoid pneumaticRelease = new Solenoid(1);
    	static Solenoid brakeEngage = new Solenoid(2);						//pneumatic brakes for the lifting motor
    	static Solenoid brakeDisengage = new Solenoid(3);
    	Joystick leftStick = new Joystick(0);
    	Joystick rightStick = new Joystick(1);
    	static RobotDrive drive = new RobotDrive(0,1,2,3);					//4 wheel mecanum drive!
    	static Talon elevateTalon = new Talon(4);							//motor used for lifting mechanism
    	Print p = new Print();												//function I wrote because native print functions are annoying
    	PowerDistributionPanel panel = new PowerDistributionPanel();
    	CommandGroup group = new CommandGroup();							//MULTITHREADING!!! (sort of)
    	int loop=0;
    	//int signature = 1;
    	
	public void robotInit() {
		//pixy = new Pixy();												//image tracking never implemented, image tracking programmer was not successful.
		camera = new AxisCamera("10.31.4.20");								//set up camera
		camera.writeResolution(AxisCamera.Resolution.k320x240);
		camera.writeBrightness(0);
		p.s("Camera", "Enabled");
		pneumaticTrigger.set(false);										//disengage all acutuators on gripping mechanism
		pneumaticRelease.set(false);
		brakeDisengage.set(false);											//engage airbrake
		brakeEngage.set(true);
		p.s("Lift Brake", "Engaged");
		p.s("State", "Robot Init");
		update();
		/*try{													//tried to multi thread, did not work
    	group.addSequential(new Forward(), 5);
    	//group.addSequential(new Trigger());
    	//group.addSequential(new Elevate(), 2);
    	//group.addSequential(new Strafe(), 2);
    	//group.addSequential(new Lower(), 1);
    	//group.addSequential(new Release());
    	//group.addSequential(new Lower(), 1);
    	group.addSequential(new Trigger());
    	group.addSequential(new Elevate(), 2);
    	group.addSequential(new Forward(), 10);
		}catch(IllegalUseOfCommandException e){
			e.printStackTrace();
		}*/
    }

    /**
     * This function is called periodically during autonomous
     */
	public void autonomousInit(){
		p.s("State", "Autonomous");
    	loop=0;
	}
	
    public void autonomousPeriodic() {
		//update();											//image tracking not used, code commented out for future reference.
		//align();
		//if(getDistance(packet.Height)<13.5){
		//	drive.mecanumDrive_Cartesian(0, 0.5, 0, 0);
		//}
    	//drive.mecanumDrive_Cartesian(0, -1, 0, 0);
    	/*if(loop==0){
    		group.start();
    		loop++;
    		}*/
    	/*try{
    	group.start();
    	}catch(IllegalUseOfCommandException e){
    		e.printStackTrace();
    	}*/
    	if(loop==0){
    	for(int x=0;x<5500;x++){
    		drive.mecanumDrive_Cartesian(0, -1, 0, 0);		//drive forward with crate in front of you to et out of other players way
    		p.n("iteration", x);
    	}
    	drive.mecanumDrive_Cartesian(0, 0, 0, 0);			//stop after distance reached
    	loop++;
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopInit(){
    	p.s("State", "Teleop");
    }
    
    public void teleopPeriodic() {
		update();
        //drive.tankDrive(leftStick, rightStick);
    	//drive.arcadeDrive(leftStick);
    	//double stick mode
    	//drive.mecanumDrive_Cartesian(leftStick.getX(), leftStick.getY(), rightStick.getY(), 0);
    	//Single stick mode
		pneumatics();																					//check if pneumatics state change
		elevator();																						//check elevator state change
        drive.mecanumDrive_Cartesian(leftStick.getX(), leftStick.getY(), (leftStick.getTwist()/2), 0);	//mecanum drive!!!! I love mechanum drive!!!!!
    	Timer.delay(0.005);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void disabledInit() {
    	p.s("State", "Disabled");
    }
    
    public void update(){
    	p.n("Temperature", (panel.getTemperature()*9/5)+32);	//tell me everything about the PDB on the smart dashoard
    	p.n("Voltage", panel.getVoltage());
    	p.n("Total Current", panel.getTotalCurrent());
    	p.n("Total Energy", panel.getTotalEnergy());
    	p.n("Total Power", panel.getTotalPower()); 
    	for(int x=0;x<=15;x++){
    		p.n("Port "+x, panel.getCurrent(x));
    	}

    }
    
    public void elevator(){								//same code I use every year for motor control with limit switches. look at previous years for description 
    	if(limitSwitchTop.get() == true){
    		upAllow = false;
    		downAllow = true;
    	}else if(limitSwitchBottom.get() == true){
    		downAllow = false;
    		upAllow = true;
    	}else{
    		upAllow = true;
    		downAllow = true;
    	}
    	if(((leftStick.getRawButton(6)==true)||(leftStick.getRawButton(5)==true))&&(upAllow)){
    		brakeDisengage.set(true);  																		//this is the cool part, use code timing to disengage airbreak, move, and reingage the airbreak.
    		brakeEngage.set(false);
    		p.s("Lift Brake", "Disengaged");
    		elevateTalon.set(0.5);
    		p.s("Elevator State", "Forwards");
    	}else if(((leftStick.getRawButton(4)==true)||(leftStick.getRawButton(3)==true))&&(downAllow)){
    		brakeDisengage.set(true);
    		brakeEngage.set(false);
    		p.s("Lift Brake", "Disengaged");
    		elevateTalon.set(-0.5);
    		p.s("Elevator State", "Backwards");
    	}else if(upAllow == false){
    		brakeEngage.set(true);
    		brakeDisengage.set(false);
    		p.s("Lift Brake", "Engaged");
    		p.s("Elevator State", "Too High");
    		elevateTalon.set(0.0);
    	}else if(downAllow == false){
    		brakeEngage.set(true);
    		brakeDisengage.set(false);
    		p.s("Lift Brake", "Engaged");
    		p.s("Elevator State", "Too Low");
    		elevateTalon.set(0.0);
    	}else{
    		brakeEngage.set(true);							//reingage the airbreak
    		brakeDisengage.set(false);
    		p.s("Lift Brake", "Engaged");
    		elevateTalon.set(0.0);
    		p.s("Elevator State", "Stopped");
    	}
    }
    
    public void pneumatics(){
    	if(leftStick.getRawButton(1)==true){			//close the gripper
    		pneumaticRelease.set(false);
    		pneumaticTrigger.set(true);
    		p.s("Pneumatics", "Extended");
    	}else if(leftStick.getRawButton(2)==true){		//open the gripper
    		pneumaticTrigger.set(false);
    		pneumaticRelease.set(true);
    		p.s("Pneumatics", "Retracted");
    	}else{
    		pneumaticTrigger.set(false);				
    		pneumaticRelease.set(false);
    		p.s("Pneumatics", "Disabled");
    	}
    }
    
   /* public void align(){																							//vision tracking never used...
		try {packet = pixy.readPacket(signature++);} catch (PixyException e) {e.printStackTrace();}
		center = (packet.X+(packet.Width/2));
		p.n("Crate Center(pixels)need(320)", center);
		if(center<robotCenter){
			p.s("Autonomus State", "Shifting right");
			drive.mecanumDrive_Cartesian(0.25, 0, 0, 0);
		}else if(center>robotCenter){
			p.s("Autonomus State", "Shifting left");
			drive.mecanumDrive_Cartesian(-0.25, 0, 0, 0);
		}else{p.s("Autonomus State", "Aligned");}
    }
    
    public double getDistance(int height){
    	return ((.00002*Math.pow(height, 4))-(.0053*Math.pow(height, 3))+(0.5056*Math.pow(height, 2))-(22.107*height)+435.64);
    }*/
    
}
