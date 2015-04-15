/**
*  Code to remove distortion from image using bilinear interpolation, converted from C++, (Lorenzo 2014).
*/
public BufferedImage undistortImage(Image image){
	double destinationWidth = 640;
	double destinationHeight = 240;
	byte[][] destination = new byte[(int)destinationWidth][(int)destinationHeight];

	//define needed variables outside the inner loop
	double calX, calY, weightX, weightY, dX1, dX2, dX3, dX4, dY1, dY2, dY3, dY4, dX, dY;
	int x1, x2, y1, y2, denormalizedX, denormalizedY;
	int x, y;

	final byte[] raw = image.data();
	final float[] distortion_buffer = image.distortion();

	//Local variables for values needed in loop
	final int distortionWidth = image.distortionWidth();
	final int width = image.width();
	final int height = image.height();

	for (x = 0; x < destinationWidth; ++x) {
	    for (y = 0; y < destinationHeight; ++y) {
	        //Calculate the position in the calibration map (still with a fractional part)
	        calX = 63 * x/destinationWidth;
	        calY = 63 * y/destinationHeight;
	        //Save the fractional part to use as the weight for interpolation
	        
	        weightX = calX - Math.floor(calX);
	        weightY = calY - Math.floor(calY);

	        //Get the x,y coordinates of the closest calibration map points to the target pixel
	        x1 = (int)calX; //Note truncation to int
	        y1 = (int)calY;
	        x2 = x1 + 1;
	        y2 = y1 + 1;

	        //Look up the x and y values for the 4 calibration map points around the target
	        // (x1, y1)  ..  .. .. (x2, y1)
	        //    ..                 ..
	        //    ..    (x, y)       ..
	        //    ..                 ..
	        // (x1, y2)  ..  .. .. (x2, y2)
	        dX1 = distortion_buffer[x1 * 2 + y1 * distortionWidth];
	        dX2 = distortion_buffer[x2 * 2 + y1 * distortionWidth];
	        dX3 = distortion_buffer[x1 * 2 + y2 * distortionWidth];
	        dX4 = distortion_buffer[x2 * 2 + y2 * distortionWidth];
	        dY1 = distortion_buffer[x1 * 2 + y1 * distortionWidth + 1];
	        dY2 = distortion_buffer[x2 * 2 + y1 * distortionWidth + 1];
	        dY3 = distortion_buffer[x1 * 2 + y2 * distortionWidth + 1];
	        dY4 = distortion_buffer[x2 * 2 + y2 * distortionWidth + 1];

	        //Bilinear interpolation of the looked-up values:
	        // X value
	        dX = dX1 * (1 - weightX) * (1- weightY) + dX2 * weightX * (1 - weightY) + dX3 * (1 - weightX) * weightY + dX4 * weightX * weightY;

	        // Y value
	        dY = dY1 * (1 - weightX) * (1- weightY) + dY2 * weightX * (1 - weightY) + dY3 * (1 - weightX) * weightY + dY4 * weightX * weightY;

	        // Reject points outside the range [0..1]
	        if((dX >= 0) && (dX <= 1) && (dY >= 0) && (dY <= 1)) {
	            //Denormalize from [0..1] to [0..width] or [0..height]
	            denormalizedX = (int) (dX * width);
	            denormalizedY = (int) (dY * height);

	            //look up the brightness value for the target pixel
	            destination[x][y] = raw[denormalizedX + denormalizedY * width];
	        } else {
	            destination[x][y] = -1;
	        }
	        
	        
	    }
	}
	byte[] b = new byte[640*240];
	int i = 0;
	for(y = 0; y < destinationHeight; ++y){
		for(x = 0; x < destinationWidth; ++x){
			b[i++] = destination[x][y];
		}
	}
    BufferedImage bufferedImage = new BufferedImage(image.width(),image.height(), BufferedImage.TYPE_BYTE_GRAY);
    final byte[] targetPixels = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
    System.arraycopy(b, 0, targetPixels, 0, b.length);  
    return bufferedImage;
}