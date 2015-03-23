// Initialise the Controller
Controller controller = new Controller();

// Set the policy to retrieve images from the camera
controller.setPolicyFlag(PolicyFlag.POLICY_IMAGES);

// Get ImageList from the controller
ImageList images = controller.images();

// Convert both images and write to a file
int imageCount = 0;
for(Image image : images){
	BufferedImage bufferedImage = toBufferedImage(image);
	File outputImage = new File("image" + imageCount + ".png");
	ImageIO.write(image, "png", outputImage);
}