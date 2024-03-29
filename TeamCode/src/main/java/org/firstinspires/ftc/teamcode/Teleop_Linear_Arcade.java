/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Basic: Linear OpMode", group="Linear Opmode")
//@Disabled
public class Teleop_Linear_Arcade extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFrontDrive;
    private DcMotor rightFrontDrive;
    private DcMotor leftBackDrive;
    private DcMotor rightBackDrive;

    private boolean thisTime = false;
    private boolean lastTime = false;
    private double backward = -1;
    private double forward = 1;
    private double turningFactor = 0.75;
    private double maxSpeed = 1;
    private double slowSpeed = 0.5;

    // Setup a variable for each drive wheel to save power level for telemetry
    // Choose to drive using either Tank Mode, or POV Mode
    // Comment out the method that's not used.  The default below is POV.
    // POV Mode uses left stick to go forward, and right stick to turn.
    // - This uses basic math to combine motions and is easier to drive straight.

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftFrontDrive  = hardwareMap.get(DcMotor.class, "lfdrive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rfdrive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "lbdrive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rbdrive");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {



            if (gamepad1.right_stick_x == 0){
                leftBackDrive.setPower(forward * -gamepad1.right_stick_y);
                leftFrontDrive.setPower(forward * -gamepad1.right_stick_y);
                rightBackDrive.setPower(forward * -gamepad1.right_stick_y);
                rightFrontDrive.setPower(forward * -gamepad1.right_stick_y);

            }
            else if (-gamepad1.right_stick_y == 0){
                leftBackDrive.setPower(forward * gamepad1.right_stick_x);
                leftFrontDrive.setPower(backward * gamepad1.right_stick_x);
                rightBackDrive.setPower(forward * gamepad1.right_stick_x);
                rightFrontDrive.setPower(backward * gamepad1.right_stick_x);

            }
            else if (gamepad1.right_stick_x/-gamepad1.right_stick_y==1){
                leftFrontDrive.setPower(forward*gamepad1.right_stick_x);
                rightBackDrive.setPower(forward*gamepad1.right_stick_x);
                leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
            else if (gamepad1.right_stick_x/-gamepad1.right_stick_y==-1){
                leftBackDrive.setPower(forward*gamepad1.right_stick_x);
                rightFrontDrive.setPower(forward*gamepad1.right_stick_x);
                leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }

//            else if((gamepad1.right_stick_x*-gamepad1.right_stick_y)>0){
//                if(gamepad1.right_stick_x > 0){
//                    leftFrontDrive.setPower(forward);
//                    rightBackDrive.setPower(forward);
//                }
//                else{
//                    leftFrontDrive.setPower(-forward);
//                    rightBackDrive.setPower(-forward);
//                }
//                leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//                rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//            }
//            else if((gamepad1.right_stick_x*-gamepad1.right_stick_y)<0){
//                if(gamepad1.right_stick_x < 0){
//                    rightFrontDrive.setPower(forward);
//                    leftBackDrive.setPower((forward));
//                }
//                else{
//                    rightFrontDrive.setPower(-forward);
//                    leftBackDrive.setPower(-forward);
//                }
//                leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//                rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//            }
            else if(gamepad1.right_trigger>0){
                leftFrontDrive.setPower(forward*turningFactor);
                leftBackDrive.setPower(forward*turningFactor);
                rightFrontDrive.setPower(backward*turningFactor);
                rightBackDrive.setPower(backward*turningFactor);
            }
            else if(gamepad1.left_trigger>0){
                leftFrontDrive.setPower(backward*turningFactor);
                leftBackDrive.setPower(backward*turningFactor);
                rightFrontDrive.setPower(forward*turningFactor);
                rightBackDrive.setPower(forward*turningFactor);
            }



//            Declare 1=being pressed, 0=not being pressed
            if(gamepad1.a){
                thisTime = true;
            }
            else{
                thisTime = false;
            }
//            check for the button is pressed...
            if(thisTime != lastTime){
//                check that change is to pushed
                if(thisTime == true) {
                    if (forward == maxSpeed){
                        forward = slowSpeed;
                    } else {
                        forward = maxSpeed;
                    }
                    if (backward == -maxSpeed){
                        backward = -slowSpeed;
                    } else {
                        backward = -maxSpeed;
                    }
                    lastTime = true;
                }
            }
            lastTime = thisTime;

            telemetry.addData("Motor Speed: ", forward);







            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", forward, backward);
            telemetry.update();
        }
    }
}
