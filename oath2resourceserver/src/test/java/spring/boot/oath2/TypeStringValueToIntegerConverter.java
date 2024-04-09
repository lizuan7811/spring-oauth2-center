//package spring.boot.oath2;
//
//import java.util.Collections;
//import java.util.Set;
//
//import org.springframework.beans.factory.config.TypedStringValue;
//import org.springframework.core.convert.TypeDescriptor;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.core.convert.converter.GenericConverter;
//
//public class TypeStringValueToIntegerConverter implements GenericConverter {
//
//	@Override
//	public Set<ConvertiblePair> getConvertibleTypes() {
//		return Collections.singleton(new ConvertiblePair(TypedStringValue.class,Integer.class));
//	}
//
//	@Override
//	public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
//		TypedStringValue typedStringValue=(TypedStringValue)source;
//		return Integer.valueOf(typedStringValue.getValue());
//	}
//
//}
