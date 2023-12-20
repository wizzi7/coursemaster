package com.coursemaster.authservice.auth.api;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    private String refreshToken;
}
