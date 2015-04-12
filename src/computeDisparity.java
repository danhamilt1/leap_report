public Mat getDisparityMapStereoSGBM(Mat left, Mat right) {
	//  Initialise new stereo matcher
    StereoSGBM stereo = new StereoSGBM();
    Mat disparityMap = Mat.zeros(0, 0, type);

    //  Computer disparity
    stereo.compute(left, right, disparityMap);
    //  Normalise output as recommended, creates a larger visual difference
    //  in diaparities.
    Core.normalize(disparityMap, disparityMap, 0, 255, Core.NORM_MINMAX);

    return disparityMap;
}