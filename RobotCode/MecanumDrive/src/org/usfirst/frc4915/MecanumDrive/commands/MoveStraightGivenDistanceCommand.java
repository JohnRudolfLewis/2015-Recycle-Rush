// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc4915.MecanumDrive.commands;

import java.util.Arrays;
import java.util.List;

import org.usfirst.frc4915.MecanumDrive.Robot;
import org.usfirst.frc4915.MecanumDrive.RobotMap;
import org.usfirst.frc4915.MecanumDrive.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class  MoveStraightGivenDistanceCommand extends Command {
	public static List <CANTalon> motors = Robot.driveTrain.motors;
	public double inputDistance;
	private DriveTrain driveTrain = Robot.driveTrain;
	
    public MoveStraightGivenDistanceCommand(double inputDistance) {
    	requires(driveTrain); 
    	this.inputDistance = inputDistance;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    long elapsed;
    long startTime;
    long endTime;
    double distance;
    double time;
    double wait;
    double distanceSinceElapsed;
    
    // Called just before this Command runs the first time
    /**
     * This initializes the variables for the distance calculator.  
     */
    protected void initialize() {
    	
    	// This loop allows for a negative input that will have the robot run backwards. 
    	if (inputDistance < 0)
    		driveTrain.driveStraight(-0.2);
    	else 
    		driveTrain.driveStraight(0.2);
    	// Creates variables for use in determining the time so we can compute the distance traveled 
    	elapsed = 0;
    	startTime = System.currentTimeMillis();
    	endTime = 0;
    	// creates a variable to keep track of the time. 
    	distance = 0;
    	// creates two variables for use in determining the wait. 
    	wait = System.currentTimeMillis();
    	time = 0;
    }
    /**
     * This uses the wheel circumference and the number of rotations to compute the distance traveled. 
     */
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// creates a loop to track the distance traveled
    		if (time < 100){
    			time = System.currentTimeMillis() - wait;
			}
    		else {
    			// uses the time to determine the distance traveled since the last time the robot was sampled.
    			endTime = System.currentTimeMillis();
    			elapsed = endTime - startTime;
    			distanceSinceElapsed = 0;
    			//maybe a method???
    			for(CANTalon motor : motors) {
    				double distanceForMotor = driveTrain.getDistanceForMotor(motor, elapsed);
    				distanceSinceElapsed = Math.max(distanceForMotor, distanceSinceElapsed);
    			}
    		}
    		distance += distanceSinceElapsed;
    		startTime = endTime; 	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Math.abs(distance) > Math.abs(inputDistance));
    }

    // Called once after isFinished returns true
    protected void end() {
    	driveTrain.getRobotDrive().stopMotor(); 
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
