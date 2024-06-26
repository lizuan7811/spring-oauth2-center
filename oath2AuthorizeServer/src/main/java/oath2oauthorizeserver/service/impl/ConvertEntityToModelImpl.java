package oath2oauthorizeserver.service.impl;

import oath2oauthorizeserver.service.ConvertEntityToModel;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;

@Component
public class ConvertEntityToModelImpl implements ConvertEntityToModel {

	@Override
	public  <F, D> D convertEntityToModel(F clazFrom, D clazDest) {

		try {
			System.out.println(">>> ConvertEntity");
			Field[] fields = clazFrom.getClass().getDeclaredFields();
			Arrays.asList(fields).stream().filter(field->{
				ReflectionUtils.makeAccessible(field);
				return !field.getName().equals("serialVersionUID") && !field.getName().equals("roles");
			}).forEach(field -> {
				try {
					ReflectionUtils.makeAccessible(field);
					Field fieldD = clazDest.getClass().getDeclaredField(field.getName());
					ReflectionUtils.makeAccessible(fieldD);
					fieldD.set(clazDest, field.get(clazFrom));
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			});

		} catch (IllegalArgumentException | SecurityException e) {
			e.printStackTrace();
		}
		return clazDest;
	}

}
