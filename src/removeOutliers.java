    /**
     * Remove outliers from matched features
     *
     * @param matches
     * @return
     */
    private MatOfDMatch removeOutliers(MatOfDMatch matches) {
        MatOfDMatch goodMatches = new MatOfDMatch();
        double max_dist = 0;
        double min_dist = 100;

        for (int i = 0; i < matches.rows(); i++) {
            double dist = matches.toList().get(i).distance;
            System.out.println(dist);
            if (dist < min_dist)
                min_dist = dist;
            if (dist > max_dist)
                max_dist = dist;
        }

        for (int i = 0; i < matches.rows(); i++) {
            if (matches.toList().get(i).distance < 3.5 * min_dist) {
                MatOfDMatch goodMatch = new MatOfDMatch(matches.toList().get(i));
                goodMatches.push_back(goodMatch);
            }
        }

        return goodMatches;
    }