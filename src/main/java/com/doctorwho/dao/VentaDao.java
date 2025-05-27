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

    // BÚSQUEDA DE VENTAS
    public List<Venta> searchVentas(String estado, Boolean pagado, String fechaDesde, String fechaHasta) {
        List<Venta> ventas = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ventas WHERE 1=1 ");

        List<Object> parametros = new ArrayList<>();

        // Filtro por estado
        if (estado != null && !estado.trim().isEmpty()) {
            sql.append("AND estado_venta = ? ");
            parametros.add(estado);
        }

        // Filtro por estado de pago
        if (pagado != null) {
            sql.append("AND pagado = ? ");
            parametros.add(pagado);
        }

        // Filtro por rango de fechas
        if (fechaDesde != null && !fechaDesde.trim().isEmpty()) {
            sql.append("AND fecha_venta >= ? ");
            parametros.add(java.sql.Date.valueOf(fechaDesde));
        }

        if (fechaHasta != null && !fechaHasta.trim().isEmpty()) {
            sql.append("AND fecha_venta <= ? ");
            parametros.add(java.sql.Date.valueOf(fechaHasta));
        }

        sql.append("ORDER BY fecha_venta DESC");

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            // Establecer parámetros
            for (int i = 0; i < parametros.size(); i++) {
                Object param = parametros.get(i);
                if (param instanceof String) {
                    stmt.setString(i + 1, (String) param);
                } else if (param instanceof Boolean) {
                    stmt.setBoolean(i + 1, (Boolean) param);
                } else if (param instanceof java.sql.Date) {
                    stmt.setDate(i + 1, (java.sql.Date) param);
                }
            }

            try (ResultSet rs = stmt.executeQuery()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventas;
    }

    // CONTAR VENTAS FILTRADAS
    public int countVentasFiltradas(String estado, Boolean pagado, String fechaDesde, String fechaHasta) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM ventas WHERE 1=1 ");

        List<Object> parametros = new ArrayList<>();

        // Mismos filtros que en searchVentas
        if (estado != null && !estado.trim().isEmpty()) {
            sql.append("AND estado_venta = ? ");
            parametros.add(estado);
        }

        if (pagado != null) {
            sql.append("AND pagado = ? ");
            parametros.add(pagado);
        }

        if (fechaDesde != null && !fechaDesde.trim().isEmpty()) {
            sql.append("AND fecha_venta >= ? ");
            parametros.add(java.sql.Date.valueOf(fechaDesde));
        }

        if (fechaHasta != null && !fechaHasta.trim().isEmpty()) {
            sql.append("AND fecha_venta <= ? ");
            parametros.add(java.sql.Date.valueOf(fechaHasta));
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < parametros.size(); i++) {
                Object param = parametros.get(i);
                if (param instanceof String) {
                    stmt.setString(i + 1, (String) param);
                } else if (param instanceof Boolean) {
                    stmt.setBoolean(i + 1, (Boolean) param);
                } else if (param instanceof java.sql.Date) {
                    stmt.setDate(i + 1, (java.sql.Date) param);
                }
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    

    // BÚSQUEDA DE VENTAS CON PAGINACIÓN
    public List<Venta> searchVentasPaged(String estado, Boolean pagado, String fechaDesde, String fechaHasta, int offset, int limit) {
        List<Venta> ventas = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ventas WHERE 1=1 ");

        List<Object> parametros = new ArrayList<>();

        // Filtros
        if (estado != null && !estado.trim().isEmpty()) {
            sql.append("AND estado_venta = ? ");
            parametros.add(estado);
        }

        if (pagado != null) {
            sql.append("AND pagado = ? ");
            parametros.add(pagado);
        }

        if (fechaDesde != null && !fechaDesde.trim().isEmpty()) {
            sql.append("AND fecha_venta >= ? ");
            parametros.add(java.sql.Date.valueOf(fechaDesde));
        }

        if (fechaHasta != null && !fechaHasta.trim().isEmpty()) {
            sql.append("AND fecha_venta <= ? ");
            parametros.add(java.sql.Date.valueOf(fechaHasta));
        }

        sql.append("ORDER BY fecha_venta DESC LIMIT ? OFFSET ?");
        parametros.add(limit);
        parametros.add(offset);

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            // Establecer parámetros
            for (int i = 0; i < parametros.size(); i++) {
                Object param = parametros.get(i);
                if (param instanceof String) {
                    stmt.setString(i + 1, (String) param);
                } else if (param instanceof Boolean) {
                    stmt.setBoolean(i + 1, (Boolean) param);
                } else if (param instanceof java.sql.Date) {
                    stmt.setDate(i + 1, (java.sql.Date) param);
                } else if (param instanceof Integer) {
                    stmt.setInt(i + 1, (Integer) param);
                }
            }

            try (ResultSet rs = stmt.executeQuery()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventas;
    }

    // OBTENER TODAS LAS VENTAS CON PAGINACIÓN
    public List<Venta> getAllVentasPaged(int offset, int limit) {
        String sql = "SELECT * FROM ventas ORDER BY fecha_venta DESC LIMIT ? OFFSET ?";
        List<Venta> ventas = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            
            try (ResultSet rs = stmt.executeQuery()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventas;
    }

    // CONTAR TODAS LAS VENTAS
    public int countAllVentas() {
        String sql = "SELECT COUNT(*) FROM ventas";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}