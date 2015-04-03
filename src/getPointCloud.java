    public Mat getPointCloud(Mat disparityMap) {
    	//  Initialise distortion coefficients in a Multi Dimensional array
    	//  Values from Ma (2014)
        double[][] tQ = {{1.0, 0.0, 0.0,           -5.0638e+02},
                         {0.0, 1.0, 0.0,           -2.3762e+02},
                         {0.0, 0.0, 0.0,           1.3476e+03},
                         {0.0, 0.0, 6.9349981e-01, 3.503271}};

        Mat pointCloud = new MatOfPoint3();
        Mat Q = new Mat(4, 4, CvType.CV_32F);

        //  Put values of array tQ into Mat Q
        for (int i = 0; i < tQ.length; ++i)
            Q.put(i, 0, tQ[i]);

        //  Generate point cloud
        Calib3d.reprojectImageTo3D(disparityMap, pointCloud, Q);

        return pointCloud;
    }