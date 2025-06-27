package com.weatherfit.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class ApiResponse<T> {

    private final boolean isSuccess;
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data;

    private ApiResponse(boolean isSuccess, String code, String message, T data) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 성공 응답
    public static <T> ResponseEntity<ApiResponse<T>> onSuccess(BaseCode code, T data) {
        ApiResponse<T> response = new ApiResponse<>(true, code.getResponse().getCode(), code.getResponse().getMessage(), data);
        return ResponseEntity.status(code.getResponse().getHttpStatus()).body(response);
    }

    // 실패 응답
    public static <T> ResponseEntity<ApiResponse<T>> onFail(BaseCode code) {
        ApiResponse<T> response = new ApiResponse<>(false, code.getResponse().getCode(), code.getResponse().getMessage(), null);
        return ResponseEntity.status(code.getResponse().getHttpStatus()).body(response);
    }
}
