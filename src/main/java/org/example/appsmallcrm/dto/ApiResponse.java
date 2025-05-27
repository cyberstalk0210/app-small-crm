package org.example.appsmallcrm.dto;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data
) {
    public static ApiResponse<UserDTO> success(UserDTO dto) {
        return new ApiResponse<>(true, "Operation successful", dto);
    }
}
