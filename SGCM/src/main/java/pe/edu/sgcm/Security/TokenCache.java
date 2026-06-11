/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Security;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author alexis
 */


public class TokenCache {

    private static final Cache<String, Integer> cache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(15, TimeUnit.MINUTES)
            .build();

    public static void guardarToken(String token, Integer userId) {
        cache.put(token, userId);
    }

    public static Integer obtenerUsuario(String token) {
        return cache.getIfPresent(token);
    }

    public static void invalidarToken(String token) {
        cache.invalidate(token);
    }

    public static void limpiarTodo() {
        cache.invalidateAll();
    }
}
