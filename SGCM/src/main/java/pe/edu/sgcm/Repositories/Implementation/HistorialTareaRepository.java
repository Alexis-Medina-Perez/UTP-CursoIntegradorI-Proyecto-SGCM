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
import pe.edu.sgcm.DTOs.Requests.CrearHistorialRequest;
import pe.edu.sgcm.DTOs.Responses.HistorialTareaResponse;
import pe.edu.sgcm.Repositories.Interface.IHistorialTareaRepository;
import pe.edu.sgcm.Utils.ConexionBD;
import pe.edu.sgcm.Utils.UserContext;

/**
 *
 * @author alexis
 */
public class HistorialTareaRepository implements IHistorialTareaRepository {

    @Override
    public boolean registrar(CrearHistorialRequest request, String usuario, String ip) throws Exception {

        String sql = "INSERT INTO sgcm.historial_tarea " +
                     "(id_tarea, id_usuario, id_estado_tarea, observacion, fecha_cambio, usuario_creacion, ip_creacion) " +
                     "VALUES (?, ?, ?, ?, NOW(), ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, request.getIdTarea());
            ps.setInt(2, UserContext.getUserId());
            ps.setInt(3, request.getIdEstadoTarea());
            ps.setString(4, request.getObservacion());
            ps.setString(5, usuario);
            ps.setString(6, ip);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<HistorialTareaResponse> listarPorTarea(Integer idTarea) throws Exception {

        String sql = "SELECT e.nombre AS estado, u.username, h.observacion, h.fecha_cambio " +
                     "FROM sgcm.historial_tarea h " +
                     "JOIN sgcm.estado_tarea e ON e.id_estado_tarea = h.id_estado_tarea " +
                     "JOIN sgcm.usuario u ON u.id_usuario = h.id_usuario " +
                     "WHERE h.id_tarea = ? AND h.sql_deleted = 'F' " +
                     "ORDER BY h.fecha_cambio DESC";

        List<HistorialTareaResponse> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTarea);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                HistorialTareaResponse r = new HistorialTareaResponse();

                r.setEstado(rs.getString("estado"));
                r.setUsuario(rs.getString("username"));
                r.setObservacion(rs.getString("observacion"));
                r.setFechaCambio(rs.getTimestamp("fecha_cambio").toString());

                lista.add(r);
            }
        }

        return lista;
    }

    
}
