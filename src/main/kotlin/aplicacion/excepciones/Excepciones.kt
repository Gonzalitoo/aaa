package aplicacion.excepciones


class NotFoundException(override var message: String) : RuntimeException(message)
class BusinessException(override var message: String) : RuntimeException(message)
class CarritoExcepcion(override var message: String) : RuntimeException(message)
