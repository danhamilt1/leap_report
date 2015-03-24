    /**
     * Turn a {@link BufferedImage} into a {@link WritableImage}, useful for displaying in JavaFX
     *
     * @param image - {@link BufferedImage}
     * @return {@link WritableImage}
     */
    public static WritableImage toWritableImage(BufferedImage image) {
        //BufferedImage image = toBufferedImage(toProcess);
        WritableImage wImage = new WritableImage(image.getWidth(), image.getHeight());
        PixelWriter pw = wImage.getPixelWriter();
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                pw.setArgb(x, y, image.getRGB(x, y));
            }
        }
        return wImage;
    }