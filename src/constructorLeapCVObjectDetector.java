    public LeapCVObjectDetector() {
        this.extractor = DescriptorExtractor.create(DescriptorExtractor.SIFT);
        this.matcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
        this.featureDetector = FeatureDetector.create(FeatureDetector.SIFT);

        this.matchedImage = new Mat();

    }