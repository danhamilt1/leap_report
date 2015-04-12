    /**
     * Crops even percentage from each side of an image
     *
     * @param image The {@link com.leapmotion.leap.Image} to be cropped.
     * @param percentageCrop The percentage to which the image passed in should be cropped. Between 0 and 1.
     * @return {@link org.opencv.core.Mat}
     */
    public static Mat crop(Mat image, double percentageCrop) {
        int width;
        int height;
        int x;
        int y;
        Mat newImage = image;

        //  Get the width and height of the image
        width = newImage.cols();
        height = newImage.rows();

        //  Work out top left coordinates of submatrix
        x = (int) (width * percentageCrop);
        y = (int) (height * percentageCrop);

        //  Work out the width and height of the submatrix
        width = width - x * 2;
        height = height - y * 2;

        //  Set the rectangle of interest
        Rect roi = new Rect(x, y, width, height);

        //  Return the submatrix
        return newImage.submat(roi);
    }