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
import pe.edu.sgcm.DTOs.Requests.CrearNotificacionRequest;
import pe.edu.sgcm.DTOs.Responses.NotificacionResponse;
import pe.edu.sgcm.Repositories.Interface.INotificacionRepository;
import pe.edu.sgcm.Utils.ConexionBD;

/**
 *
 * @author alexis
 */
public class NotificacionRepository implements INotificacionRepository {

    @Override
    public boolean crear(CrearNotificacionRequest request, String usuario, String ip) throws Exception {

        String sql = "INSERT INTO sgcm.notificacion (id_usuario, titulo, mensaje, usuario_creacion, ip_creacion) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, request.getIdUsuario());
            ps.setString(2, request.getTitulo());
            ps.setString(3, request.getMensaje());
            ps.setString(4, usuario);
            ps.setString(5, ip);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<NotificacionResponse> listarPorUsuario(Integer idUsuario) throws Exception {

        String sql = "SELECT id_notificacion, titulo, mensaje, leido " +
                     "FROM sgcm.notificacion WHERE id_usuario = ? AND sql_deleted = 'F'";

        List<NotificacionResponse> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                NotificacionResponse n = new NotificacionResponse();

                n.setIdNotificacion(rs.getInt("id_notificacion"));
                n.setTitulo(rs.getString("titulo"));
                n.setMensaje(rs.getString("mensaje"));
                n.setLeido(rs.getBoolean("leido"));

                lista.add(n);
            }
        }

        return lista;
    }

    @Override
    public boolean marcarLeido(Integer idNotificacion, String usuario, String ip) throws Exception {

        String sql = "UPDATE sgcm.notificacion SET leido = true, fecha_lectura = NOW(), usuario_modificacion = ?, ip_modificacion = ? WHERE id_notificacion = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, ip);
            ps.setInt(3, idNotificacion);

            return ps.executeUpdate() > 0;
        }
    }

    
}
