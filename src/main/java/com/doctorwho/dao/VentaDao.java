package com.doctorwho.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.doctorwho.exception.VentaNotFoundException;
import com.doctorwho.model.Venta;

import java.math.BigDecimal;

public class VentaDao {

    private Connection connection;

    public VentaDao(Connection connection) {
        this.connection = connection;
    }

     // AÑADIR
    public boolean addVenta(Venta venta) {
        String sql = "INSERT INTO ventas (usuario_id, articulo_id, cantidad, total, fecha_venta, estado_venta, pagado) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, venta.getUsuarioId());
            stmt.setInt(2, venta.getArticuloId());
            stmt.setInt(3, venta.getCantidad());
            stmt.setBigDecimal(4, venta.getTotal());
            stmt.setDate(5, venta.getFechaVenta());
            stmt.setString(6, venta.getEstadoVenta());
            stmt.setBoolean(7, venta.isPagado());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // MODIFICAR
    public boolean updateVenta(Venta venta) {
        String sql = "UPDATE ventas SET usuario_id = ?, articulo_id = ?, cantidad = ?, total = ?, fecha_venta = ?, estado_venta = ?, pagado = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, venta.getUsuarioId());
            stmt.setInt(2, venta.getArticuloId());
            stmt.setInt(3, venta.getCantidad());
            stmt.setBigDecimal(4, venta.getTotal());
            stmt.setDate(5, venta.getFechaVenta());
            stmt.setString(6, venta.getEstadoVenta());
            stmt.setBoolean(7, venta.isPagado());
            stmt.setInt(8, venta.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ELIMINAR
    public boolean deleteVenta(int id) {
        String sql = "DELETE FROM ventas WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DETALLE
    public Venta getVentaById(int id) {
        String sql = "SELECT * FROM ventas WHERE id = ?";
        Venta venta = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    venta = new Venta();
                    venta.setId(rs.getInt("id"));
                    venta.setUsuarioId(rs.getInt("usuario_id"));
                    venta.setArticuloId(rs.getInt("articulo_id"));
                    venta.setCantidad(rs.getInt("cantidad"));
                    venta.setTotal(rs.getBigDecimal("total"));
                    venta.setFechaVenta(rs.getDate("fecha_venta"));
                    venta.setEstadoVenta(rs.getString("estado_venta"));
                    venta.setPagado(rs.getBoolean("pagado"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return venta;
    }

    // LISTAR
    public List<Venta> getAllVentas() {
        String sql = "SELECT * FROM ventas ORDER BY fecha_venta DESC";
        List<Venta> ventas = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Venta venta = new Venta();
                venta.setId(rs.getInt("id"));
                venta.setUsuarioId(rs.getInt("usuario_id"));
                venta.setArticuloId(rs.getInt("articulo_id"));
                venta.setCantidad(rs.getInt("cantidad"));
                venta.setTotal(rs.getBigDecimal("total"));
                venta.setFechaVenta(rs.getDate("fecha_venta"));
                venta.setEstadoVenta(rs.getString("estado_venta"));
                venta.setPagado(rs.getBoolean("pagado"));
                ventas.add(venta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventas;
    }
}