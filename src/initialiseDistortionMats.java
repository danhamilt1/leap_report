/**
     * Initialise the distortion matrices for use with OpenCV {@link Imgproc.remap} method.
     */
    public static Map<String, Mat> initDistortionMat(Image image) {

        Mat tempX = new Mat();
        Mat tempY = new Mat();

        tempX = Mat.ones(image.height(), image.width(), CvType.CV_32F);
        tempY = Mat.ones(image.height(), image.width(), CvType.CV_32F);

        // Get distortion value for each pixel in the image
        for (int y = 0; y < image.height(); ++y) {
            for (int x = 0; x < image.width(); ++x) {
                Vector input = new Vector((float) x / image.width(), (float) y / image.height(), 0);
                input.setX((input.getX() - image.rayOffsetX()) / image.rayScaleX());
                input.setY((input.getY() - image.rayOffsetY()) / image.rayScaleY());
                Vector pixel = image.warp(input);
                if (pixel.getX() >= 0 && pixel.getX() < image.width() && pixel.getY() >= 0 && pixel.getY() < image.height()) {
                    tempX.put(y, x, pixel.getX());
                    tempY.put(y, x, pixel.getY());
                } else {
                    tempX.put(y, x, -1);
                    tempY.put(y, x, -1);
                }

            }
        }

        Map<String, Mat> retVal = new HashMap<>();
        retVal.put(X_MAT_KEY, tempX);
        retVal.put(Y_MAT_KEY, tempY);

        return retVal;
    }