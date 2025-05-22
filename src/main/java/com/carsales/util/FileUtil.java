package com.carsales.util;

import org.apache.commons.fileupload.FileItem;

import java.io.File;

public class FileUtil {
    public static String[] saveUploadedFiles(FileItem[] items, String savePath) throws Exception {
        // First count valid files
        int fileCount = 0;
        for (FileItem item : items) {
            if (!item.isFormField() && !item.getName().isEmpty()) {
                fileCount++;
            }
        }

        // Create array of exact size
        String[] fileNames = new String[fileCount];
        int index = 0;

        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }

        for (FileItem item : items) {
            if (!item.isFormField()) {
                String fileName = new File(item.getName()).getName();
                if (!fileName.isEmpty()) {
                    File saveFile = new File(savePath, fileName);
                    item.write(saveFile);
                    fileNames[index++] = fileName;
                }
            }
        }
        return fileNames;
    }
}