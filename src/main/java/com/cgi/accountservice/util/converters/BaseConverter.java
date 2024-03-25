package com.cgi.accountservice.util.converters;


public abstract class BaseConverter<E, D> {
    public abstract E convertToEntity(D dto, Object... args);

    public abstract D convertToDto( E entity, Object... args);
}
