/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Utils;

import java.util.Base64;

/**
 *
 * @author alexis
 */

public class PasswordUtil {

    public static String encrypt(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes());
    }

    public static String decrypt(String text) {
        return new String(Base64.getDecoder().decode(text));
    }
}

