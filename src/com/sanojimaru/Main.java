package com.sanojimaru;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.io.FilenameUtils;
import sun.org.mozilla.javascript.internal.regexp.RegExpImpl;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: sanojimaru
 * Date: 2013/11/07
 * Time: 17:16
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    public static void main(String[] args) {

        File dir = new File("/Users/sanojimaru/tmp/doclabo");
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                String ext = FilenameUtils.getExtension(file.getName());
                Pattern p = Pattern.compile("jpg|jpeg|png|gif");
                Matcher m = p.matcher(ext);

                return m.find();
            }
        });

        if (files == null) return;

        for (File file : files) {
            System.out.println(file);
            String ext = FilenameUtils.getExtension(file.getName().toLowerCase());
            File mainImage = new File(file.getAbsolutePath());
            File thumbImage = new File(toThumbName(mainImage.getPath()));

            try {
                final int imageWidth = 1024;
                final int imageHeight = 768;
                Thumbnails.of(file)
                        .size(imageWidth, imageHeight)
                        .outputQuality(1f)
                        .keepAspectRatio(true)
                        .toFile(mainImage);

                final int thumbWidth = 320;
                final int thumbHeight = 240;
                Thumbnails.of(file)
                        .size(thumbWidth, thumbHeight)
                        .crop(Positions.CENTER)
                        .outputQuality(0.8f)
                        .keepAspectRatio(true)
                        .toFile(thumbImage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String toThumbName(String filename) {
        String ext = FilenameUtils.getExtension(filename.toLowerCase());
        return filename.replace("." + ext, ".thumb." + ext);
    }
}

