package aplicacion.dominio.producto

interface View {
    interface ProductoRecomendado
    interface ProductoLista : ProductoRecomendado
    interface ProductoIndividual : ProductoLista
    interface ProductoConLote : ProductoIndividual, Lote
    interface ProductoItem : ProductoConLote, Item
    interface Item
    interface Lote
    interface DetalleProducto : ProductoConLote, ProductoItem
}