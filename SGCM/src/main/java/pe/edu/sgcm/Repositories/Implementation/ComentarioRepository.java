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
import pe.edu.sgcm.DTOs.Requests.ActualizarComentarioRequest;
import pe.edu.sgcm.DTOs.Requests.CrearComentarioRequest;
import pe.edu.sgcm.DTOs.Responses.ComentarioResponse;
import pe.edu.sgcm.Repositories.Interface.IComentarioRepository;
import pe.edu.sgcm.Utils.ConexionBD;
import pe.edu.sgcm.Utils.UserContext;
/**
 *
 * @author alexis
 */
public class ComentarioRepository implements IComentarioRepository {

    @Override
    public boolean crear(CrearComentarioRequest request, String usuario, String ip) throws Exception {

        String sql = "INSERT INTO sgcm.comentario_tarea (id_tarea, id_usuario, comentario, usuario_creacion, ip_creacion) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, request.getIdTarea());
            ps.setInt(2, UserContext.getUserId());
            ps.setString(3, request.getComentario());
            ps.setString(4, usuario);
            ps.setString(5, ip);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean actualizar(ActualizarComentarioRequest request, String usuario, String ip) throws Exception {

        String sql = "UPDATE sgcm.comentario_tarea SET comentario = ?, usuario_modificacion = ?, fecha_modificacion = NOW(), ip_modificacion = ? WHERE id_comentario_tarea = ? AND sql_deleted = 'F'";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, request.getComentario());
            ps.setString(2, usuario);
            ps.setString(3, ip);
            ps.setInt(4, request.getIdComentarioTarea());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(Integer idComentario, String usuario, String ip) throws Exception {

        String sql = "UPDATE sgcm.comentario_tarea SET sql_deleted = 'T', usuario_modificacion = ?, fecha_modificacion = NOW(), ip_modificacion = ? WHERE id_comentario_tarea = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, ip);
            ps.setInt(3, idComentario);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<ComentarioResponse> listarPorTarea(Integer idTarea) throws Exception {

        String sql = "SELECT c.id_comentario_tarea, c.comentario, u.username " +
                     "FROM sgcm.comentario_tarea c " +
                     "JOIN sgcm.usuario u ON u.id_usuario = c.id_usuario " +
                     "WHERE c.id_tarea = ? AND c.sql_deleted = 'F'";

        List<ComentarioResponse> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTarea);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                ComentarioResponse r = new ComentarioResponse();

                r.setIdComentarioTarea(rs.getInt("id_comentario_tarea"));
                r.setComentario(rs.getString("comentario"));
                r.setUsuario(rs.getString("username"));

                lista.add(r);
            }
        }

        return lista;
    }


}
