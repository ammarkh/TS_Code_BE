package com.marketplace.CodeChallenge.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ammar kh
 * since 2022-03
 * this tool use for excute map between dto and entity
 */
public class MapperUtil {
    @Bean
    private static ModelMapper modelMapper() {
        return new ModelMapper();
    }

    static {
        modelMapper();
        modelMapper().getConfiguration() .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

    }

    private MapperUtil() {
    }

    public static <D, T> D map(final T entity, Class<D> outClass) {
        return modelMapper().map(entity, outClass);
    }

    public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outClass) {
        return entityList.stream()
                .map(entity -> map(entity, outClass))
                .collect(Collectors.toList());
    }

    public static <S, D> D map(final S source, D destination) {
        modelMapper().map(source, destination);
        return destination;
    }

}
