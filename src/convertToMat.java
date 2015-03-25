public static Mat convertToMat(Image image){
    // Initialise the Mat with the height and width of the image
    // CvType.CV_8UC1 is single channel unsigned 8 bit.
    Mat convertedImage = new Mat(image.height(), image.width(), CvType.CV_8UC1);

    // Put the image data into the Mat starting at pixel x = 0, y = 0.
    convertedImage.put(0, 0, image.data());

    return convertedImage;
}
