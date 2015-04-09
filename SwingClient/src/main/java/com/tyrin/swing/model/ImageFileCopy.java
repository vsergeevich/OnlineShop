/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.swing.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tyrin V. S.
 * Класс, инкапсулирующий механиз копирования выбраного файла изображения товаров
 */
public class ImageFileCopy {

    /**
     * Метод. который копирует выбранный файл в папку по умолчанию и возвращает путь к конечному файлу
     * @param sourceFile
     * @return 
     */
    public static String copyFileInTarget(String sourceFile) {
        Path sourcePath = Paths.get(sourceFile);
        String name = sourcePath.getFileName().toString();
        Path targetPath = Paths.get("img\\" + name);
        try {
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(ImageFileCopy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return targetPath.toString();
    }
}
