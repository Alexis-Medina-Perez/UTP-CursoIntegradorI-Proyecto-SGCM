/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Repositories.Implementation;

import pe.edu.sgcm.Repositories.Interface.ITareaRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import pe.edu.sgcm.DTOs.Requests.ActualizarTareaRequest;
import pe.edu.sgcm.DTOs.Requests.CrearTareaRequest;
import pe.edu.sgcm.DTOs.Responses.TareaResponse;
import pe.edu.sgcm.Utils.ConexionBD;

/**
 *
 * @author alexis
 */
public class TareaRepository implements ITareaRepository {

    @Override
    public boolean crearTarea(CrearTareaRequest request, String usuario, String ip) throws Exception {

        String sql = "INSERT INTO sgcm.tarea (id_campania, id_estado_tarea, id_prioridad_tarea, id_usuario_responsable, nombre, descripcion, fecha_inicio, fecha_limite, usuario_creacion, ip_creacion) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, request.getIdCampania());
            ps.setInt(2, request.getIdEstadoTarea());
            ps.setInt(3, request.getIdPrioridadTarea());
            ps.setInt(4, request.getIdUsuarioResponsable());
            ps.setString(5, request.getNombre());
            ps.setString(6, request.getDescripcion());
            ps.setDate(7, java.sql.Date.valueOf(request.getFechaInicio()));
            ps.setDate(8, java.sql.Date.valueOf(request.getFechaLimite()));
            ps.setString(9, usuario);
            ps.setString(10, ip);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<TareaResponse> listarTareas() throws Exception {

        String sql = "SELECT t.id_tarea, t.nombre, t.descripcion, t.porcentaje_avance, " +
                     "t.fecha_inicio, t.fecha_limite, " +
                     "e.nombre AS estado, " +
                     "p.nombre AS prioridad, " +
                     "u.username AS responsable, " +
                     "c.nombre AS campania " +
                     "FROM sgcm.tarea t " +
                     "JOIN sgcm.estado_tarea e ON e.id_estado_tarea = t.id_estado_tarea " +
                     "JOIN sgcm.prioridad_tarea p ON p.id_prioridad_tarea = t.id_prioridad_tarea " +
                     "JOIN sgcm.usuario u ON u.id_usuario = t.id_usuario_responsable " +
                     "JOIN sgcm.campania c ON c.id_campania = t.id_campania " +
                     "WHERE t.sql_deleted = 'F'";

        List<TareaResponse> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                TareaResponse t = new TareaResponse();

                t.setIdTarea(rs.getInt("id_tarea"));
                t.setNombre(rs.getString("nombre"));
                t.setPorcentajeAvance(rs.getInt("porcentaje_avance"));
                t.setEstado(rs.getString("estado"));
                t.setPrioridad(rs.getString("prioridad"));
                t.setResponsable(rs.getString("responsable"));
                t.setCampania(rs.getString("campania"));
                t.setDescripcion(rs.getString("descripcion"));
                t.setFechaInicio(rs.getDate("fecha_inicio") != null ? rs.getDate("fecha_inicio").toString() : null);
                t.setFechaLimite(rs.getDate("fecha_limite") != null ? rs.getDate("fecha_limite").toString() : null);

                lista.add(t);
            }
        }

        return lista;
    }

    @Override
    public boolean actualizarEstado(Integer idTarea, Integer idEstado, String usuario, String ip) throws Exception {

        String sql = "UPDATE sgcm.tarea SET id_estado_tarea = ?, usuario_modificacion = ?, fecha_modificacion = NOW(), ip_modificacion = ? WHERE id_tarea = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEstado);
            ps.setString(2, usuario);
            ps.setString(3, ip);
            ps.setInt(4, idTarea);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean actualizarTarea(ActualizarTareaRequest request, String usuario, String ip) throws Exception {

        String sql = "UPDATE sgcm.tarea SET " +
                     "id_campania = ?, " +
                     "id_estado_tarea = ?, " +
                     "id_prioridad_tarea = ?, " +
                     "id_usuario_responsable = ?, " +
                     "nombre = ?, " +
                     "descripcion = ?, " +
                     "fecha_inicio = ?, " +
                     "fecha_limite = ?, " +
                     "porcentaje_avance = ?, " +
                     "usuario_modificacion = ?, " +
                     "fecha_modificacion = NOW(), " +
                     "ip_modificacion = ? " +
                     "WHERE id_tarea = ? AND sql_deleted = 'F'";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, request.getIdCampania());
            ps.setInt(2, request.getIdEstadoTarea());
            ps.setInt(3, request.getIdPrioridadTarea());
            ps.setInt(4, request.getIdUsuarioResponsable());

            ps.setString(5, request.getNombre());
            ps.setString(6, request.getDescripcion());

            ps.setDate(7, java.sql.Date.valueOf(request.getFechaInicio()));
            ps.setDate(8, java.sql.Date.valueOf(request.getFechaLimite()));

            ps.setInt(9, request.getPorcentajeAvance());

            ps.setString(10, usuario);
            ps.setString(11, ip);

            ps.setInt(12, request.getIdTarea());

            return ps.executeUpdate() > 0;
        }
    }

}
