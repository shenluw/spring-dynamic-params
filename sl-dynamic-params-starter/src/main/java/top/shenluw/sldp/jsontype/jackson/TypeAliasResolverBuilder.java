package top.shenluw.sldp.jsontype.jackson;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import top.shenluw.sldp.SldpProperties;
import top.shenluw.sldp.TypeNameAliasResolver;

import java.util.Collection;

/**
 * @author Shenluw
 * 创建日期：2019/6/14 16:54
 */
public class TypeAliasResolverBuilder extends StdTypeResolverBuilder {
    private TypeNameAliasResolver resolver;

    private String typeProperty;

    public TypeAliasResolverBuilder(TypeNameAliasResolver resolver, SldpProperties properties) {
        this.resolver = resolver;
        typeProperty = properties.getTypePropertyName();
    }

    @Override
    public StdTypeResolverBuilder init(JsonTypeInfo.Id idType, TypeIdResolver idRes) {
        super.init(idType, idRes);
        _typeProperty = typeProperty;
        return this;
    }

    @Override
    public StdTypeResolverBuilder typeProperty(String typeIdPropName) {
        _typeProperty = typeProperty;
        return this;
    }

    @Override
    protected TypeIdResolver idResolver(MapperConfig<?> config, JavaType baseType, Collection<NamedType> subtypes, boolean forSer, boolean forDeser) {
        return new TypeAliasIdResolver(baseType, config.getTypeFactory(), this.resolver);
    }
}
