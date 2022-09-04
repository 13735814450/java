package com.lt.dm.gateway.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lt.platform.common.model.base.BaseResult;
import com.lt.platform.common.model.enums.BusinessExceptionEnum;
import com.lt.platform.common.model.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关异常处理器
 *
 * @author laiyulong
 * @since 2021/1/19
 */
@Slf4j
@Order(-1)
@Component
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        log.error("Gateway异常:", ex);
        if (ex instanceof ResponseStatusException) {
            response.setStatusCode(((ResponseStatusException) ex).getStatus());
        }

        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            try {
                if (ex instanceof BusinessException) {
                    BusinessException e = (BusinessException) ex;
                    return dataBufferFactory.wrap(
                            objectMapper.writeValueAsBytes(
                                    BaseResult.fail(e.getCode(), e.getMessage())));
                }else if(ex instanceof ResponseStatusException){
                    ResponseStatusException e = (ResponseStatusException) ex;
                    return dataBufferFactory.wrap(objectMapper.writeValueAsBytes(BaseResult.fail(e.getReason())));
                }
                return dataBufferFactory.wrap(objectMapper.writeValueAsBytes(BaseResult.fail(BusinessExceptionEnum.SYSTEM_ERROR.message())));

            } catch (JsonProcessingException e) {
                log.error("Error writing response", ex);
                return dataBufferFactory.wrap(new byte[0]);
            }
        }));
    }
}
