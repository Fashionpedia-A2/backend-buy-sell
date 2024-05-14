package id.ac.ui.cs.advprog.backendbuysell.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import id.ac.ui.cs.advprog.backendbuysell.enums.ApiResponseStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"status", "message", "data", "errors"})
public class ApiResponse<T> {
    private ApiResponseStatus status;
    private T data;
    private String message;
    private List<String> errors;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder().status(ApiResponseStatus.SUCCESS).data(data).build();
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder().status(ApiResponseStatus.SUCCESS).data(data).message(message).build();
    }

    public static <T> ApiResponse<T> failed(String message) {
        return ApiResponse.<T>builder().status(ApiResponseStatus.FAILED).message(message).build();
    }

    public static <T> ApiResponse<T> failed(List<String> errors, String message) {
        return ApiResponse.<T>builder().status(ApiResponseStatus.FAILED).errors(errors).message(message).build();
    }
}
