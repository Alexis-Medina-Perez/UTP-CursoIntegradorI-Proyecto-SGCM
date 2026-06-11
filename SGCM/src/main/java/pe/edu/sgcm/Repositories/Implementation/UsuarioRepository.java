/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Repositories.Implementation;

import pe.edu.sgcm.Repositories.Interface.IUsuarioRepository;
import pe.edu.sgcm.DTOs.Requests.UsuarioRegisterRequest;
import pe.edu.sgcm.Utils.ConexionBD;
import pe.edu.sgcm.Models.Usuario;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import pe.edu.sgcm.DTOs.Requests.ActualizarUsuarioRequest;
import pe.edu.sgcm.DTOs.Requests.UsuarioRolRequest;
import pe.edu.sgcm.DTOs.Responses.RolPorUsuarioResponse;


/**
 *
 * @author alexis
 */

public class UsuarioRepository implements IUsuarioRepository {
    
    @Override
    public Usuario obtenerUsuarioPorUsername(String username) throws Exception {

        String sql = "SELECT id_usuario, username, password_hash FROM sgcm.usuario WHERE username = ? AND activo = true AND sql_deleted = 'F'";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setUsername(rs.getString("username"));
                u.setPasswordHash(rs.getString("password_hash"));
                return u;
            }
        }
        return null;
    }

    @Override
    public String obtenerPassword(String username) throws Exception {

        String sql = "SELECT password_hash FROM sgcm.usuario WHERE username = ? AND activo = true AND sql_deleted = 'F'";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("password_hash");
            }
        }

        return null;
    }
       
    @Override
    public boolean registrarUsuario(UsuarioRegisterRequest request, String passwordEncrypt) throws Exception {

        String sql = "INSERT INTO sgcm.usuario (" +
                "nombres, apellidos, correo, username, password_hash, activo, " +
                "usuario_creacion, fecha_creacion, ip_creacion, " +
                "usuario_modificacion, fecha_modificacion, ip_modificacion, sql_deleted" +
                ") VALUES (?, ?, ?, ?, ?, true, ?, CURRENT_TIMESTAMP, ?, " +
                "NULL, NULL, NULL, 'F')";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, request.getNombres());
            ps.setString(2, request.getApellidos());
            ps.setString(3, request.getCorreo());
            ps.setString(4, request.getUsername());
            ps.setString(5, passwordEncrypt);
            ps.setString(6, request.getUsuarioCreacion());
            ps.setString(7, request.getIpCreacion());

            return ps.executeUpdate() > 0;
        }
    }
    
    @Override
    public List<Usuario> listarUsuarios() throws Exception {

        String sql = "SELECT id_usuario, nombres, apellidos, correo, username, sql_deleted " +
                     "FROM sgcm.usuario WHERE sql_deleted = 'F'";

        List<Usuario> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNombres(rs.getString("nombres"));
                u.setApellidos(rs.getString("apellidos"));
                u.setCorreo(rs.getString("correo"));
                u.setUsername(rs.getString("username"));
                u.setSqlDeleted(rs.getString("sql_deleted"));

                lista.add(u);
            }
        }

        return lista;
    }

    @Override
    public boolean actualizarUsuario(ActualizarUsuarioRequest request, String usuarioMod, String ip) throws Exception {

        String sql = "UPDATE sgcm.usuario " +
                     "SET nombres = ?, apellidos = ?, correo = ?, " +
                     "usuario_modificacion = ?, fecha_modificacion = NOW(), ip_modificacion = ? " +
                     "WHERE id_usuario = ? AND sql_deleted = 'F'";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, request.getNombres());
            ps.setString(2, request.getApellidos());
            ps.setString(3, request.getCorreo());
            ps.setString(4, usuarioMod);
            ps.setString(5, ip);
            ps.setInt(6, request.getIdUsuario());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminarUsuario(Integer idUsuario, String usuarioMod, String ip) throws Exception {

        String sql = "UPDATE sgcm.usuario " +
                     "SET sql_deleted = 'T', " +
                     "usuario_modificacion = ?, fecha_modificacion = NOW(), ip_modificacion = ? " +
                     "WHERE id_usuario = ? AND sql_deleted = 'F'";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuarioMod);
            ps.setString(2, ip);
            ps.setInt(3, idUsuario);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean actualizarEstadoUsuario(Integer idUsuario, Boolean activo, String usuarioMod, String ip) throws Exception {

        String sql = "UPDATE sgcm.usuario " +
                     "SET sql_deleted = ?, " +
                     "usuario_modificacion = ?, fecha_modificacion = NOW(), ip_modificacion = ? " +
                     "WHERE id_usuario = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, activo ? "F" : "T");

            ps.setString(2, usuarioMod);
            ps.setString(3, ip);
            ps.setInt(4, idUsuario);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean asignarRol(UsuarioRolRequest request, String usuario, String ip) throws Exception {

        String sql = "INSERT INTO sgcm.usuario_rol (id_usuario, id_rol, usuario_creacion, ip_creacion) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, request.getIdUsuario());
            ps.setInt(2, request.getIdRol());
            ps.setString(3, usuario);
            ps.setString(4, ip);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean existeAsignacion(Integer idUsuario, Integer idRol) throws Exception {

        String sql = "SELECT 1 FROM sgcm.usuario_rol WHERE id_usuario = ? AND id_rol = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idRol);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    @Override
    public boolean quitarRol(Integer idUsuario, Integer idRol) throws Exception {

        String sql = "DELETE FROM sgcm.usuario_rol WHERE id_usuario = ? AND id_rol = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idRol);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<RolPorUsuarioResponse> listarRolesUsuario(Integer idUsuario) throws Exception {

        String sql = "SELECT r.id_rol, r.nombre " +
                     "FROM sgcm.usuario_rol ur " +
                     "JOIN sgcm.rol r ON r.id_rol = ur.id_rol " +
                     "WHERE ur.id_usuario = ?";

        List<RolPorUsuarioResponse> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                RolPorUsuarioResponse r = new RolPorUsuarioResponse();

                r.setIdRol(rs.getInt("id_rol"));
                r.setNombre(rs.getString("nombre"));

                lista.add(r);
            }
        }

        return lista;
    }


}
