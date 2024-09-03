package com.ssafy.moabang.common.util;

import static com.ssafy.moabang.common.util.ApiUtils.ApiStatus.ERROR;
import static com.ssafy.moabang.common.util.ApiUtils.ApiStatus.SUCCESS;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

public class ApiUtils {

    public static <T> ApiResult<T> success(T response) {
        return new ApiResult<>(SUCCESS, response, null);
    }

    public static ApiResult<?> error(Throwable throwable, HttpStatus status) {
        return new ApiResult<>(ERROR, null, new ApiError(throwable, status));
    }

    public static ApiResult<?> error(String message, HttpStatus status) {
        return new ApiResult<>(ERROR, null, new ApiError(message, status));
    }

    @Getter
    public static class ApiError {

        private final String message;
        private final int status;

        ApiError(Throwable throwable, HttpStatus status) {
            this(throwable.getMessage(), status);
        }

        ApiError(String message, HttpStatus status) {
            this.message = message;
            this.status = status.value();
        }
    }

    @Getter
    public static class ApiResult<T> {

        private final ApiStatus status;
        private final T data;
        private final ApiError error;

        private ApiResult(ApiStatus status, T data, ApiError error) {
            this.status = status;
            this.data = data;
            this.error = error;
        }
    }

    @RequiredArgsConstructor
    enum ApiStatus {

        SUCCESS("성공"),
        ERROR("실패");

        private final String description;
    }
}