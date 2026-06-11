/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Repositories.Implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import pe.edu.sgcm.DTOs.Requests.CrearRolRequest;
import pe.edu.sgcm.Models.Rol;
import pe.edu.sgcm.Repositories.Interface.IRolRepository;
import pe.edu.sgcm.Utils.ConexionBD;

/**
 *
 * @author alexis
 */
public class RolRepository implements IRolRepository {

    @Override
    public List<Rol> listarRoles() throws Exception {

        String sql = "SELECT id_rol, nombre, descripcion, sql_deleted FROM sgcm.rol WHERE sql_deleted = 'F'";

        List<Rol> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Rol r = new Rol();

                r.setIdRol(rs.getInt("id_rol"));
                r.setNombre(rs.getString("nombre"));
                r.setDescripcion(rs.getString("descripcion"));
                r.setSqlDeleted(rs.getString("sql_deleted"));

                lista.add(r);
            }
        }

        return lista;
    }

    @Override
    public boolean crearRol(CrearRolRequest request, String usuario, String ip) throws Exception {

        String sql = "INSERT INTO sgcm.rol (nombre, descripcion, usuario_creacion, ip_creacion) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, request.getNombre());
            ps.setString(2, request.getDescripcion());
            ps.setString(3, usuario);
            ps.setString(4, ip);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean actualizarEstadoRol(Integer idRol, Boolean activo, String usuario, String ip) throws Exception {

        String sql = "UPDATE sgcm.rol SET sql_deleted = ?, usuario_modificacion = ?, ip_modificacion = ?, fecha_modificacion = NOW() WHERE id_rol = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, activo ? "F" : "T");
            ps.setString(2, usuario);
            ps.setString(3, ip);
            ps.setInt(4, idRol);

            return ps.executeUpdate() > 0;
        }
    }


}
