/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pe.edu.sgcm.Utils;

import jakarta.ws.rs.container.*;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Response;

import pe.edu.sgcm.Security.TokenCache;


/**
 *
 * @author alexis
 */

@Provider
@PreMatching
public class JwtFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) {

        String path = requestContext.getUriInfo().getPath();

        if (path.contains("login") || path.contains("register")) {
            return;
        }

        String authHeader = requestContext.getHeaderString("Authorization");

        System.out.println("HEADER: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring("Bearer ".length());

            try {
                var claims = JwtUtil.getClaims(token);

                Integer userId = claims.get("id_usuario", Integer.class);
                String username = claims.get("username", String.class);

                Integer userCache = TokenCache.obtenerUsuario(token);

                if (userCache == null) {
                    requestContext.abortWith(
                        Response.status(Response.Status.UNAUTHORIZED)
                                .entity("Token expirado o no válido (cache)")
                                .build()
                    );
                    return;
                }

                UserContext.setUser(userId, username);

            } catch (Exception e) {
                requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Token inválido")
                            .build()
                );
            }

        } else {
            requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Token requerido")
                        .build()
            );
        }
    }

}
