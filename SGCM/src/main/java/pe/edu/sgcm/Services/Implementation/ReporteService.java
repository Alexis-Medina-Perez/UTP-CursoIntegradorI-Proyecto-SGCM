/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Services.Implementation;

import java.util.List;
import pe.edu.sgcm.DTOs.Responses.ReporteTareasPorCampaniaResponse;
import pe.edu.sgcm.DTOs.Responses.ReporteTareasPorEstadoResponse;
import pe.edu.sgcm.DTOs.Responses.ReporteTareasPorUsuarioResponse;
import pe.edu.sgcm.Repositories.Implementation.ReporteRepository;
import pe.edu.sgcm.Repositories.Interface.IReporteRepository;
import pe.edu.sgcm.Services.Interface.IReporteService;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import pe.edu.sgcm.DTOs.Responses.ReporteCampaniaEmpresaResponse;
import pe.edu.sgcm.DTOs.Responses.ReporteDetalleEmpresaResponse;

/**
 *
 * @author alexis
 */

public class ReporteService implements IReporteService {

    private IReporteRepository reporteRepository = new ReporteRepository();

    @Override
    public List<ReporteTareasPorUsuarioResponse> tareasPorUsuario() throws Exception {
        return reporteRepository.tareasPorUsuario();
    }

    @Override
    public List<ReporteTareasPorCampaniaResponse> tareasPorCampania() throws Exception {
        return reporteRepository.tareasPorCampania();
    }

    @Override
    public List<ReporteTareasPorEstadoResponse> tareasPorEstado() throws Exception {
        return reporteRepository.tareasPorEstado();
    }
    
    @Override
    public byte[] generarExcelTareasPorUsuario() throws Exception {

        List<ReporteTareasPorUsuarioResponse> lista = reporteRepository.tareasPorUsuario();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reporte");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Usuario");
        header.createCell(1).setCellValue("Total Tareas");

        int rowNum = 1;
        for (ReporteTareasPorUsuarioResponse r : lista) {

            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(r.getUsuario());
            row.createCell(1).setCellValue(r.getTotalTareas());
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();

        return bos.toByteArray();
    }

    @Override
    public byte[] generarExcelCampaniaEmpresas() throws Exception {

        List<ReporteCampaniaEmpresaResponse> lista = reporteRepository.empresasPorCampania();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Campañas");

        CellStyle styleHeader = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        styleHeader.setFont(font);

        Row header = sheet.createRow(0);

        String[] columnas = {
            "Campaña",
            "Total Empresas",
            "Clientes",
            "Prospectos",
            "% Conversión"
        };

        for (int i = 0; i < columnas.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(styleHeader);
        }

        int rowNum = 1;

        for (ReporteCampaniaEmpresaResponse r : lista) {

            Row row = sheet.createRow(rowNum++);

            double conversion = r.getTotalEmpresas() > 0
                    ? (double) r.getClientes() / r.getTotalEmpresas()
                    : 0;

            row.createCell(0).setCellValue(r.getCampania());
            row.createCell(1).setCellValue(r.getTotalEmpresas());
            row.createCell(2).setCellValue(r.getClientes());
            row.createCell(3).setCellValue(r.getProspectos());
            row.createCell(4).setCellValue(conversion);
        }

        CellStyle percentStyle = workbook.createCellStyle();
        percentStyle.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));

        for (int i = 1; i < rowNum; i++) {
            sheet.getRow(i).getCell(4).setCellStyle(percentStyle);
        }

        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();

        return bos.toByteArray();
    }

    @Override
    public byte[] generarExcelDetalleEmpresas() throws Exception {

        List<ReporteDetalleEmpresaResponse> lista = reporteRepository.detalleEmpresas();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Empresas");

        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        String[] columnas = {
            "ID",
            "Razón Social",
            "Nombre Comercial",
            "RUC",
            "Campaña",
            "Estado",
            "Cliente",
            "Contacto",
            "Correo",
            "Teléfono",
            "Dirección",
            "Fecha Captación"
        };

        Row header = sheet.createRow(0);

        for (int i = 0; i < columnas.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowNum = 1;

        for (ReporteDetalleEmpresaResponse r : lista) {

            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(r.getIdEmpresa());
            row.createCell(1).setCellValue(r.getRazonSocial());
            row.createCell(2).setCellValue(r.getNombreComercial());
            row.createCell(3).setCellValue(r.getRuc());
            row.createCell(4).setCellValue(r.getCampania());
            row.createCell(5).setCellValue(r.getEstado());
            row.createCell(6).setCellValue(r.getEsCliente() ? "CLIENTE" : "PROSPECTO");
            row.createCell(7).setCellValue(r.getContacto());
            row.createCell(8).setCellValue(r.getCorreo());
            row.createCell(9).setCellValue(r.getTelefono());
            row.createCell(10).setCellValue(r.getDireccion());
            row.createCell(11).setCellValue(r.getFechaCaptacion());
        }

        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();

        return bos.toByteArray();
    }

}
