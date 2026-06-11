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
import pe.edu.sgcm.DTOs.Requests.ActualizarEmpresaRequest;
import pe.edu.sgcm.DTOs.Requests.CrearEmpresaRequest;
import pe.edu.sgcm.DTOs.Responses.EmpresaResponse;
import pe.edu.sgcm.Repositories.Interface.IEmpresaRepository;
import pe.edu.sgcm.Utils.ConexionBD;

/**
 *
 * @author alexis
 */
public class EmpresaRepository implements IEmpresaRepository {

    @Override
    public boolean crear(CrearEmpresaRequest request, String usuario, String ip) throws Exception {

        String sql = "INSERT INTO sgcm.empresa " +
                "(id_campania, id_estado_empresa, razon_social, nombre_comercial, ruc, contacto_principal, correo_contacto, telefono_contacto, direccion, observaciones, es_cliente, fecha_captacion, usuario_creacion, ip_creacion) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, request.getIdCampania());
            ps.setInt(2, request.getIdEstadoEmpresa());
            ps.setString(3, request.getRazonSocial());
            ps.setString(4, request.getNombreComercial());
            ps.setString(5, request.getRuc());
            ps.setString(6, request.getContactoPrincipal());
            ps.setString(7, request.getCorreoContacto());
            ps.setString(8, request.getTelefonoContacto());
            ps.setString(9, request.getDireccion());
            ps.setString(10, request.getObservaciones());
            ps.setBoolean(11, request.getEsCliente());
            ps.setString(12, usuario);
            ps.setString(13, ip);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<EmpresaResponse> listar() throws Exception {

        String sql = "SELECT e.id_empresa, e.razon_social, e.ruc, e.es_cliente, " +
                     "c.nombre AS campania, est.nombre AS estado " +
                     "FROM sgcm.empresa e " +
                     "JOIN sgcm.campania c ON c.id_campania = e.id_campania " +
                     "JOIN sgcm.estado_empresa est ON est.id_estado_empresa = e.id_estado_empresa ";

        List<EmpresaResponse> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                EmpresaResponse r = new EmpresaResponse();

                r.setIdEmpresa(rs.getInt("id_empresa"));
                r.setRazonSocial(rs.getString("razon_social"));
                r.setRuc(rs.getString("ruc"));
                r.setCampania(rs.getString("campania"));
                r.setEstado(rs.getString("estado"));
                r.setEsCliente(rs.getBoolean("es_cliente"));

                lista.add(r);
            }
        }

        return lista;
    }

    @Override
    public boolean actualizar(ActualizarEmpresaRequest request, String usuario, String ip) throws Exception {

        String sql = "UPDATE sgcm.empresa SET " +
                     "id_campania = ?, id_estado_empresa = ?, razon_social = ?, nombre_comercial = ?, ruc = ?, " +
                     "contacto_principal = ?, correo_contacto = ?, telefono_contacto = ?, direccion = ?, observaciones = ?, es_cliente = ?, " +
                     "usuario_modificacion = ?, fecha_modificacion = NOW(), ip_modificacion = ? " +
                     "WHERE id_empresa = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, request.getIdCampania());
            ps.setInt(2, request.getIdEstadoEmpresa());
            ps.setString(3, request.getRazonSocial());
            ps.setString(4, request.getNombreComercial());
            ps.setString(5, request.getRuc());
            ps.setString(6, request.getContactoPrincipal());
            ps.setString(7, request.getCorreoContacto());
            ps.setString(8, request.getTelefonoContacto());
            ps.setString(9, request.getDireccion());
            ps.setString(10, request.getObservaciones());
            ps.setBoolean(11, request.getEsCliente());
            ps.setString(12, usuario);
            ps.setString(13, ip);
            ps.setInt(14, request.getIdEmpresa());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean cambiarEstado(Integer idEmpresa, Boolean activo, String usuario, String ip) throws Exception {

        String sql = "UPDATE sgcm.empresa SET sql_deleted = ?, usuario_modificacion = ?, fecha_modificacion = NOW(), ip_modificacion = ? WHERE id_empresa = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, activo ? "F" : "T");
            ps.setString(2, usuario);
            ps.setString(3, ip);
            ps.setInt(4, idEmpresa);

            return ps.executeUpdate() > 0;
        }
    }

}
