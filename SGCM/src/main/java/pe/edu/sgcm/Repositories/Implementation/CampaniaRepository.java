/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Repositories.Implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import pe.edu.sgcm.DTOs.Requests.ActualizarCampaniaRequest;
import pe.edu.sgcm.DTOs.Requests.CrearCampaniaRequest;
import pe.edu.sgcm.Models.Campania;
import pe.edu.sgcm.Repositories.Interface.ICampaniaRepository;
import pe.edu.sgcm.Utils.ConexionBD;

/**
 *
 * @author alexis
 */
public class CampaniaRepository implements ICampaniaRepository {

    @Override
    public boolean crearCampania(CrearCampaniaRequest request, String usuario, String ip) throws Exception {

        String sql = "INSERT INTO sgcm.campania (id_estado_campania, id_tipo_campania, nombre, descripcion, objetivo, fecha_inicio, fecha_fin, presupuesto, usuario_creacion, ip_creacion) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, request.getIdEstadoCampania());
            ps.setInt(2, request.getIdTipoCampania());
            ps.setString(3, request.getNombre());
            ps.setString(4, request.getDescripcion());
            ps.setString(5, request.getObjetivo());
            setOptionalDate(ps, 6, request.getFechaInicio());
            setOptionalDate(ps, 7, request.getFechaFin());
            ps.setDouble(8, request.getPresupuesto() != null ? request.getPresupuesto() : 0d);
            ps.setString(9, usuario);
            ps.setString(10, ip);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Campania> listarCampanias() throws Exception {

        String sql = """
            SELECT 
                c.id_campania,
                c.nombre,
                c.descripcion,
                c.sql_deleted,
                c.fecha_inicio,
                c.fecha_fin,
                c.presupuesto,
                ec.nombre AS estado,
                tc.nombre AS tipo
            FROM sgcm.campania c
            INNER JOIN sgcm.estado_campania ec 
                ON c.id_estado_campania = ec.id_estado_campania
            INNER JOIN sgcm.tipo_campania tc 
                ON c.id_tipo_campania = tc.id_tipo_campania
        """;

        List<Campania> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Campania c = new Campania();

                c.setIdCampania(rs.getInt("id_campania"));
                c.setNombre(rs.getString("nombre"));
                c.setDescripcion(rs.getString("descripcion"));
                c.setSqlDeleted(rs.getString("sql_deleted"));

                c.setFechaInicioText(rs.getString("fecha_inicio"));
                c.setFechaFinText(rs.getString("fecha_fin"));
                c.setPresupuesto(rs.getDouble("presupuesto"));

                c.setEstado(rs.getString("estado"));
                c.setTipo(rs.getString("tipo"));

                lista.add(c);
            }
        }

        return lista;
    }

    private void setOptionalDate(PreparedStatement ps, int index, String value) throws Exception {
        if (value == null || value.isBlank()) {
            ps.setNull(index, Types.DATE);
        } else {
            ps.setDate(index, java.sql.Date.valueOf(value));
        }
    }

    @Override
    public boolean actualizarEstado(Integer idCampania, Boolean activo, String usuario, String ip) throws Exception {

        String sql = "UPDATE sgcm.campania SET sql_deleted = ?, usuario_modificacion = ?, ip_modificacion = ?, fecha_modificacion = NOW() WHERE id_campania = ?";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, activo ? "F" : "T");
            ps.setString(2, usuario);
            ps.setString(3, ip);
            ps.setInt(4, idCampania);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean actualizarCampania(ActualizarCampaniaRequest request, String usuario, String ip) throws Exception {

        String sql = "UPDATE sgcm.campania SET "
                + "id_estado_campania = ?, "
                + "id_tipo_campania = ?, "
                + "nombre = ?, "
                + "descripcion = ?, "
                + "objetivo = ?, "
                + "fecha_inicio = ?, "
                + "fecha_fin = ?, "
                + "presupuesto = ?, "
                + "usuario_modificacion = ?, "
                + "fecha_modificacion = NOW(), "
                + "ip_modificacion = ? "
                + "WHERE id_campania = ? AND sql_deleted = 'F'";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, request.getIdEstadoCampania());
            ps.setInt(2, request.getIdTipoCampania());
            ps.setString(3, request.getNombre());
            ps.setString(4, request.getDescripcion());
            ps.setString(5, request.getObjetivo());

            setOptionalDate(ps, 6, request.getFechaInicio());
            setOptionalDate(ps, 7, request.getFechaFin());

            ps.setDouble(8, request.getPresupuesto() != null ? request.getPresupuesto() : 0d);
            ps.setString(9, usuario);
            ps.setString(10, ip);
            ps.setInt(11, request.getIdCampania());

            return ps.executeUpdate() > 0;
        }
    }

}
