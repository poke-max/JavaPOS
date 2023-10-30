package org.fcyt.model;

public class SQLStrings {
    public String selectSQL;
    public String insertSQL;
    public String updateSQL;
    public String deleteSQL;
    public SQLStrings(String table, String value) {
        switch (table) {
            case "caja":
                selectSQL ="select c.id as id, c.descripcion, c.nro_caja, e.nombre as empresa\n" +
                        "from caja c\n" +
                        "inner join empresa e on e.id = c.idempresa\n" +
                        "where descripcion ilike '%"+value+"%'\n" +
                        "order by id asc";
                insertSQL="insert into caja(descripcion,nro_caja,idempresa) values(?,?,?)";
                updateSQL="update caja set descripcion=?, nro_caja=?, idempresa=? where id=?";
                deleteSQL="delete from caja where id=?";
                break;
            case "cliente":
                selectSQL ="select c.id, c.ruc, c.nombre as nombre, c.telefono, c.direccion, e.nombre as empresa\n" +
                        "from cliente c\n" +
                        "inner join empresa e on e.id = c.idempresa\n" +
                        "where c.nombre ilike '%"+value+"%'\n" +
                        "order by id asc";
                insertSQL="insert into cliente(ruc,nombre,telefono,direccion,idempresa) values(?,?,?,?,?)";
                updateSQL="update cliente set ruc=?, nombre=?, telefono=?, direccion=?, idempresa=? where id=?";
                deleteSQL="delete from cliente where id=?";
                break;

            case "detalle_factura":
                selectSQL ="select p.descripcion as producto, f.nro_factura, d.descripcion as descripcion, d.cantidad, d.importe, i.descripcion as iva\n" +
                        "from detalle_factura d\n" +
                        "inner join producto p on p.id = d.idproducto\n" +
                        "inner join public.factura f on f.id = d.idfactura\n" +
                        "inner join public.iva i on i.id = d.idiva\n" +
                        "where f.nro_factura ilike '%"+value+"%'\n" +
                        "order by f.nro_factura asc";
                insertSQL="insert into detalle_factura(idfactura,descripcion,cantidad,importe,idiva) values(?,?,?,?,?)";
                updateSQL="update detalle_factura set idfactura=?, descripcion=?, cantidad=?, importe=?, idiva=? where id=?";
                deleteSQL="delete from detalle_factura where id=?";
                break;

            case "empresa":
                selectSQL="select * from empresa\n" +
                        "where nombre ilike '%"+value+"%'\n" +
                        "order by id asc";
                insertSQL="insert into empresa(nombre,direccion,telefono,ruc) values(?,?,?,?)";
                updateSQL="update empresa set nombre=?, direccion=?, telefono=?, ruc=? where id=?";
                deleteSQL="delete from empresa where id=?";
                break;

            case "factura":
                selectSQL="select f.id, e.nombre as empresa, f.fecha, f.cond_venta, c.nombre as cliente , f.nro_factura, f.total, ca.descripcion as caja, u.usuario\n" +
                        "from factura f\n" +
                        "inner join empresa e on e.id = f.idempresa\n" +
                        "inner join public.cliente c on c.id = f.idcliente\n" +
                        "inner join public.caja ca on ca.id = f.idcaja\n" +
                        "inner join public.usuario u on e.id = u.idempresa\n" +
                        "where nro_factura ilike '%"+value+"%'\n" +
                        "order by f.fecha asc";
                insertSQL="insert into factura(idempresa,fecha,cond_venta,idcliente,nro_factura,total,idcaja,idusuario) values(?,?,?,?,?,?,?,?)";
                updateSQL="update factura set idempresa=?, fecha=?, cond_venta=?, idcliente=?, nro_factura=?, total=?, idcaja=?, idusuario=? where id=?";
                deleteSQL="delete from factura where id=?";
                break;

            case "iva":
                selectSQL="select * from iva\n" +
                        "where descripcion ilike '%"+value+"%'\n" +
                        "order by id asc";
                updateSQL="update iva set descripcion where id=?";
                deleteSQL="delete from iva where id=?";
                insertSQL="insert into iva(descripcion) values(?)";
                break;

            case "marca":
                selectSQL="select * from marca\n" +
                        "where descripcion ilike '%"+value+"%'\n" +
                        "order by id asc";
                insertSQL="insert into marca(descripcion) values(?)";
                updateSQL="update marca set descripcion where id=?";
                deleteSQL="delete from marca where id=?";
                break;

            case "producto":
                selectSQL="select p.id, p.descripcion, p.precio_compra, p.precio_venta, m.descripcion as marca, i.descripcion as iva, e.nombre as empresa\n" +
                        "from producto p\n" +
                        "inner join marca m on m.id = p.idmarca\n" +
                        "inner join public.iva i on i.id = p.idiva\n" +
                        "inner join public.empresa e on e.id = p.idempresa\n" +
                        "where p.descripcion ilike '%"+value+"%'\n" +
                        "order by id asc";
                insertSQL="insert into producto(descripcion,precio_compra,precio_venta,idmarca,idiva,idempresa) values(?,?,?,?,?,?)";
                updateSQL="update producto set descripcion=?, precio_compra=?, precio_venta=?, idmarca=?, idiva=?, idempresa=? where id=?";
                deleteSQL="delete from producto where id=?";
                break;

            case "usuario":
                selectSQL="select u.id, u.usuario, u.clave, e.nombre\n" +
                        "from usuario u\n" +
                        "inner join empresa e on e.id = u.idempresa\n" +
                        "where usuario ilike '%"+value+"%'\n" +
                        "order by id asc";
                insertSQL="insert into usuario(usuario,clave,idempresa) values(?,?,?)";
                updateSQL="update usuario set usuario=?, clave=?, idempresa=? where id=?";
                deleteSQL="delete from usuario where id=?";
                break;
        }
    }
}
