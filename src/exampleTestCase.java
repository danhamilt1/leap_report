package com.leapcv;

import com.leapcv.utils.LeapCVStereoUtils;
import junit.framework.TestCase;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import java.util.List;

/**
 * Test the performance of the LeapCV functionality
 */
public class LeapCVPerformanceTest extends TestCase {
    LeapCVController controller;
    Mat leftImage = null;
    Mat rightImage = null;
    List<LeapCVCamera> cameras = null;
    final int NUM_FRAMES = 100;
    final int MIN_FRAMERATE = 15;

    public void setUp() throws Exception {
        super.setUp();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        this.controller = new LeapCVController();
    }

    public void testFrameRateGetPointCloud(){
        long start = 0;
        long stop = 0;
        Mat dispMat = null;
        Mat pointMat = null;
        LeapCVStereoUtils utils = new LeapCVStereoUtils();

        //  Start timer
        start = System.nanoTime();

        for(int i = 0; i < NUM_FRAMES; ++i){
            leftImage = this.controller.getLeftImageUndistorted();
            rightImage = this.controller.getRightImageUndistorted();
            dispMat = utils.getDisparityMap(leftImage,rightImage);
            pointMat = utils.getPointCloud(dispMat);
            this.controller.nextValidFrame();
        }

        //  Stop timer
        stop = System.nanoTime();

        // Calculate Framerate
        long elapsed = stop - start;
        double seconds = (double)elapsed/1000000000.0;
        double fps = NUM_FRAMES/seconds;

        //  Print Framerate
        System.out.println("FPS GET PC: " + fps);

        Highgui.imwrite("/Volumes/macintosh_hdd/Users/daniel/Desktop/dispTest.png", dispMat);

        assertTrue(fps > MIN_FRAMERATE);
    }
}