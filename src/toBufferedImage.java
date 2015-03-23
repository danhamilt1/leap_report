public static BufferedImage toBufferedImage(Image image) {
    int type = BufferedImage.TYPE_BYTE_GRAY;
    byte[] imagePixels;

    // Get all the pixels
    imagePixels = image.data();

    // Write all of the pixels to the buffer in a new BufferedImage
    BufferedImage bufferedImage = new BufferedImage(image.width(), image.height(), type);
    final byte[] bufferedImagePixels = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
    System.arraycopy(imagePixels, 0, bufferedImagePixels, 0, imagePixels.length);
    return bufferedImage;
}