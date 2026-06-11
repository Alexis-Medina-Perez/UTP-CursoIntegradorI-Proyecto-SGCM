/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Utils;

/**
 *
 * @author alexis
 */

public class UserContext {

    private static ThreadLocal<Integer> currentUserId = new ThreadLocal<>();
    private static ThreadLocal<String> currentUsername = new ThreadLocal<>();

    public static void setUser(Integer userId, String username) {
        currentUserId.set(userId);
        currentUsername.set(username);
    }

    public static Integer getUserId() {
        return currentUserId.get();
    }

    public static String getUsername() {
        return currentUsername.get();
    }

    public static void clear() {
        currentUserId.remove();
        currentUsername.remove();
    }
}

