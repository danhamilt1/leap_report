/**
*  Save a generated point cloud to a file, for opening in CCViewer
*/
public void savePointCloud(Mat pointCloud, File destination) {
        try {
            // open the file for writing to (.obj file)
            File output = destination;
            if (!output.exists())
                output.createNewFile();

            FileOutputStream fileOutputStream = new FileOutputStream(output);
            OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream);
            System.out.println(pointCloud.size().width);
            System.out.println(pointCloud.size().height);

            //  For each point in the point cloud
            for (int w = 0; w < pointCloud.size().width; w++) {
                for (int h = 0; h < pointCloud.size().height; h++) {

                    //  Get XYZ from pointCloud
                    double[] values = pointCloud.get(h, w);
                    if (values != null)

                        //  Append XYZ to file
                        if (values.length >= 3)
                            writer.append("v " + String.valueOf(values[0]) + " " + String.valueOf(values[1]) + " " + String.valueOf(values[2]) + " " + "\n");
                }
            }
            writer.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }