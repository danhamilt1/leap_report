    /**
     * Turn a {@link Mat} into a {@link javafx.scene.image.Image}, useful for displaying in JavaFX
     */
    public static javafx.scene.image.Image matToWritableImage(Mat image) {
        MatOfByte byteMat = new MatOfByte();
        Highgui.imencode(".bmp", image, byteMat);
        
        return new javafx.scene.image.Image(new ByteArrayInputStream(byteMat.toArray()));
    }