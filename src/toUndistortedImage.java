public BufferedImage toUndistortedImage(Image image){
    int targetWidth = image.width();
    int targetHeight = image.heigh();
    int type = BufferedImage.TYPE_BYTE_GRAY;
    int bufferSize = targetWidth*targetHeight;
    byte[] pixels = new byte[bufferSize];
    int brightness = 0;

    // Loop through each pixel in the image
    for(double y = 0; y < targetHeight; y++){
    	for(double x = 0; x < targetWidth; x++){
            
            // Normalise the slope for the current pixel
    	    Vector input = new Vector((float)x/targetWidth, (float)y/targetHeight, 0);

            //Convert from normalized [0..1] to slope [-4..4]
            input.setX((input.getX() - image.rayOffsetX()) / image.rayScaleX());
            input.setY((input.getY() - image.rayOffsetY()) / image.rayScaleY());

            // Look up the pixel coordinates in the raw image 
            // corresponding to the slope values.
            Vector pixel = image.warp(input);
        		
            // Check that the pixel coordinates are actually valid...
            if((pixel.getX() >= 0) && 
               (pixel.getX() < targetWidth) && 
               (pixel.getY() >= 0) && 
               (pixel.getY() < targetHeight)) {

                // Convert the discrete x and y coordinates into a data buffer index
                int data_index = (int)(
                    Math.floor(pixel.getY()) 
                    * targetWidth 
                    + Math.floor(pixel.getX())
                );

                // Look up brightness value at current pixel
                brightness = image.data()[data_index] & 0xff; 

            } else {
                //Display invalid pixels as red
                brightness = 255; 

            }
            // Copy the brightness value into the new undistorted image
            pixels[(int) Math.floor(y * targetWidth + x)] =  (byte) brightness;
        }
    }
    
    // Turn the undistorted image into a buffered image
    BufferedImage bufferedImage = new BufferedImage(targetWidth, targetHeight, type);
    final byte[] targetPixels = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
    System.arraycopy(pixels, 0, targetPixels, 0, pixels.length);  

    return bufferedImage;
}
