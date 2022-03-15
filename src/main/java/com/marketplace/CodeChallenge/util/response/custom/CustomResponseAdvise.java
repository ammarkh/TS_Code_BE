package com.marketplace.CodeChallenge.util.response.custom;

import com.marketplace.CodeChallenge.util.response.ErrorResponse;
import com.marketplace.CodeChallenge.util.response.IgnoreResponseBinding;
import com.marketplace.CodeChallenge.util.response.SuccessResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class CustomResponseAdvise implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter methodParameter,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        if (methodParameter.getContainingClass()
                .isAnnotationPresent(RestController.class)) {
            if (methodParameter.getMethod()
                    .isAnnotationPresent(IgnoreResponseBinding.class) == false) {
                if ((!(body instanceof ErrorResponse))
                        && (!(body instanceof SuccessResponse))) {
                    SuccessResponse<Object> responseBody
                            = new SuccessResponse<>(body);
                    return responseBody;
                }
            }
        }
        return body;
    }

}